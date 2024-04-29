package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.PaymentInfoDTO;
import com.hacettepe.usermicroservice.dto.StripeTestChargeDto;
import com.hacettepe.usermicroservice.dto.StripeTokenDto;
import com.hacettepe.usermicroservice.exception.UnableToPayException;
import com.hacettepe.usermicroservice.model.*;
import com.hacettepe.usermicroservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final IShoppingCartRepository shoppingCartRepository;
    private final IOrderRepository orderRepository;
    private final IModelRepository modelRepository;
    private final IUserRepository userRepository;
    private final IPaymentInfoRepository paymentInfoRepository;
    private final IShoppingCartModelsRepository shoppingCartModelsRepository;
    private final String STRIPE_URL = "http://localhost:8081/stripe";
    private final String CREATE_CARD_TOKEN_URL = STRIPE_URL + "/card/token";
    private final String CHARGE_URL = STRIPE_URL + "/charge";

    private String username;

    public String getUsername() {
        if (username == null) {
            // Retrieve the currently authenticated principal
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                username = authentication.getName();
            }
        }
        return username;
    }

    public void addToShoppingCart(long modelId) {
        User user = userRepository.findByEmail(getUsername()).get();
        var shoppingCartExists = shoppingCartRepository.findByUser(user) ;
        ShoppingCart shoppingCart;

        if (shoppingCartExists == null) {
            List<ShoppingCartModels> emptyModelsList = new ArrayList<>();

            shoppingCart = ShoppingCart.builder()
                                        .user(user)
                                        .build();

            shoppingCartRepository.save(shoppingCart);
        }

        var model = modelRepository.findById(modelId);
        shoppingCart = shoppingCartRepository.findByUser(user);

        var addToCart = ShoppingCartModels.builder()
                                        .model(model)
                                        .shoppingCart(shoppingCart)
                                        .build();

        shoppingCartModelsRepository.save(addToCart);
    }

    public List<Model> getShoppingCart() {
        User user = userRepository.findByEmail(getUsername()).get();
        var shoppingCart = shoppingCartRepository.findByUser(user);
        return modelRepository.findModelsByCartId(shoppingCart.getId());
    }

    public double getTotalAmount(List<Model> modelsList) {
        return modelsList.stream()
                .mapToDouble(Model::getPrice)
                .sum();
    }

    @Transactional
    @ExceptionHandler({UnableToPayException.class})
    public void payForOrder() throws UnableToPayException { //boolean addNewPayment, PaymentInfoDTO paymentInfo, boolean savePayment) throws UnableToPayException {
        double amount =  getTotalAmount(getShoppingCart());

        RestTemplate restTemplate = new RestTemplate();
        StripeTestChargeDto testChargeDto = StripeTestChargeDto.builder()
                                                               .amount(amount)
                                                               .additionalInfo(Map.of("ID_TAG", "01234567890"))
                                                               .build();

        // FOR NOW TEST VERSION OF STRIPE API IS USED
//        String token = "";
//
//        if (!addNewPayment) {
//            token = paymentInfoRepository.findPaymentInfoByCardNumber(paymentInfo.getCardNumber()).getStripeToken();
//        }
//
//        try {
//            if (addNewPayment || token.isEmpty()) {
//                StripeTokenDto tokenDto = StripeTokenDto.builder()
//                        .cardNumber(paymentInfo.getCardNumber())
//                        .expMonth(paymentInfo.getExpirationMonth())
//                        .expYear(paymentInfo.getExpirationYear())
//                        .cvc(paymentInfo.getCvc())
//                        .build();
//
//                tokenDto = restTemplate.postForObject(CREATE_CARD_TOKEN_URL, tokenDto, StripeTokenDto.class);
//
//                if (savePayment) {
//                    paymentInfoRepository.addStripeToken(paymentInfo.getCardNumber(), tokenDto.getToken());
//                }
//
//            StripeChargeDto chargeDto = StripeChargeDto.builder()
//                    .token(tokenDto.getToken())
//                    .amount(amount)
//                    .chargeId()  // what should this be??
//                    .additionalInfo()
//                    .build();
//
//            chargeDto = restTemplate.postForObject(CHARGE_URL, chargeDto, StripeChargeDto.class);
//            }

            testChargeDto = restTemplate.postForObject(CHARGE_URL, testChargeDto, StripeTestChargeDto.class);

            if(!testChargeDto.isSuccess()) {
                throw new UnableToPayException("cant pay");
            }

            var bought_models = getShoppingCart(); //todo
            var user = userRepository.findByEmail(getUsername()).get();

            for(Model model: bought_models) {
                Order newOrder = Order.builder()
                        .orderDate(LocalDate.now())
                        .user(user)
                        .model(model)
                        .build();

                orderRepository.save(newOrder);
            }

            var shoppingCart = shoppingCartRepository.findByUser(user);
            shoppingCartModelsRepository.deleteByShoppingCart(shoppingCart);
//        } catch (HttpClientErrorException | HttpServerErrorException e) {
//            String errorMessage = e.getResponseBodyAsString();
//            throw new UnableToPayException(errorMessage);
//        }

    }

    public List<Order> getPastOrders() {
        var user = userRepository.findByEmail(getUsername()).get();
        return orderRepository.findAllByUser(user);
    }
}
