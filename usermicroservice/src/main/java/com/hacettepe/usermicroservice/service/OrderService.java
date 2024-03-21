package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.PaymentInfoDTO;
import com.hacettepe.usermicroservice.dto.StripeChargeDto;
import com.hacettepe.usermicroservice.dto.StripeTestChargeDto;
import com.hacettepe.usermicroservice.dto.StripeTokenDto;
import com.hacettepe.usermicroservice.exception.UnableToPayException;
import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.ShoppingCart;
import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final IShoppingCartRepository shoppingCartRepository;
    private final IOrderRepository orderRepository;
    private final IModelRepository modelRepository;
    private final IUserRepository userRepository;
    private final IPaymentInfoRepository paymentInfoRepository;
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

    public ShoppingCart addToShoppingCart(long modelId) {
        User user = userRepository.findByUsername(getUsername()).get();
        var shoppingCartExists = shoppingCartRepository.findByUser(user).isPresent();
        ShoppingCart shoppingCart;

        if (!shoppingCartExists) {
            List<Model> emptyModelsList = new ArrayList<>();

            shoppingCart = ShoppingCart.builder()
                                        .user(user)
                                        .models(emptyModelsList)
                                        .build();

            shoppingCartRepository.save(shoppingCart);
        }

        var model = modelRepository.findById(Long.toString(modelId)).get();
        shoppingCart = shoppingCartRepository.findByUser(user).get();
        var modelsList = shoppingCart.getModels();
        modelsList.add(model);
        shoppingCartRepository.updateShoppingCart(user, modelsList);

        return shoppingCart;
    }

    public List<Model> getShoppingCart() { //Principal principal) {
        User user = userRepository.findByUsername(getUsername()).get();  //userRepository.findByUsername(principal.getName()).get();
        shoppingCartRepository.findByUser(user).get().getModels();
        return shoppingCartRepository.findByUser(user).get().getModels();
        // frontend bağlama şekline göre geliştirilebilir
    }

    public double getTotalAmount(List<Model> modelsList) {
        return modelsList.stream()
                .mapToDouble(Model::getPrice)
                .sum();
    }

    @ExceptionHandler({UnableToPayException.class})
    public void payForOrder(boolean addNewPayment, PaymentInfoDTO paymentInfo, boolean savePayment) throws UnableToPayException {
        //todo shopping cart boşalt, save payment to db
        double amount = getTotalAmount(getShoppingCart());

        RestTemplate restTemplate = new RestTemplate();
        StripeTestChargeDto testChargeDto = StripeTestChargeDto.builder()
                                                               .amount(amount)
                                                               .build();
        String token = "";

        if (!addNewPayment) {
            token = paymentInfoRepository.findPaymentInfoByCardNumber(paymentInfo.getCardNumber()).getStripeToken();
        }

        try {
            if (addNewPayment || token.isEmpty()) {
                StripeTokenDto tokenDto = StripeTokenDto.builder()
                        .cardNumber(paymentInfo.getCardNumber())
                        .expMonth(paymentInfo.getExpirationMonth())
                        .expYear(paymentInfo.getExpirationYear())
                        .cvc(paymentInfo.getCvc())
                        .build();

                tokenDto = restTemplate.postForObject(CREATE_CARD_TOKEN_URL, tokenDto, StripeTokenDto.class);

                if (savePayment) {
                    paymentInfoRepository.addStripeToken(paymentInfo.getCardNumber(), tokenDto.getToken());
                }

                // we're using test version for now
//            StripeChargeDto chargeDto = StripeChargeDto.builder()
//                    .token(tokenDto.getToken())
//                    .amount(amount)
//                    .chargeId()  // what should this be??
//                    .additionalInfo()
//                    .build();

//            chargeDto = restTemplate.postForObject(CHARGE_URL, chargeDto, StripeChargeDto.class);
            }

            testChargeDto = restTemplate.postForObject(CHARGE_URL, testChargeDto, StripeTestChargeDto.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            throw new UnableToPayException(errorMessage);
        }

    }

    public List<?> getPastOrders() {
        return orderRepository.findByUser(getUsername());
    }
}
