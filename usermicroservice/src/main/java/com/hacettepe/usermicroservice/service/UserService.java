package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.model.*;
import com.hacettepe.usermicroservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.hacettepe.usermicroservice.dto.PaymentInfoDTO;
import com.hacettepe.usermicroservice.dto.UserUpdateDTO;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IPaymentInfoRepository paymentInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final IShoppingCartModelsRepository shoppingCartModelsRepository;
    private final IShoppingCartRepository shoppingCartRepository;
    private final IPaymentRepository paymentRepository;
    private final IModelRepository modelRepository;
    private final IDevelopersModelRepository developersModelRepository;

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

    @Override
    @ExceptionHandler({UserNotFoundException.class})
    public User updateUser(UserUpdateDTO new_user) throws UserNotFoundException, IOException {
//        User user = userRepository.findById(new_user.getUsername()).orElse(null);
        User user = userRepository.findByEmail(getUsername()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User with username " + new_user.getUsername() + " not found");
        }

        if (new_user.getName() != null) {
            user.setName(new_user.getName());
        }

        if (new_user.getEmail() != null) {
            user.setEmail(new_user.getEmail());
        }

        if (new_user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(new_user.getPassword()));
        }

        if (new_user.getCv() != null) {
            String cv_url = s3Service.uploadCV(user.getUsername(), new_user.getCv());
            user.setCv(cv_url);
        }

        if (new_user.getGithub() != null) {
            user.setGithub(new_user.getGithub());
        }

        if(new_user.getProfilePhoto() != null) {
            String pp_url = s3Service.uploadProfilePhoto(user.getUsername(), new_user.getProfilePhoto());
            user.setProfilePhoto(pp_url);
        }

        return userRepository.save(user);
    }

//    private PaymentInfo getPaymentInfo(UserUpdateDTO new_user) {
//        PaymentInfo newPaymentInfo = new PaymentInfo();
//        PaymentInfoDTO newPaymentInfoDTO = new_user.getPaymentInfo();
//
//        newPaymentInfo.setCardNumber(newPaymentInfoDTO.getCardNumber());
//        newPaymentInfo.setCvc(newPaymentInfoDTO.getCvc());
//        newPaymentInfo.setExpirationMonth(newPaymentInfoDTO.getExpirationMonth());
//        newPaymentInfo.setExpirationYear(newPaymentInfoDTO.getExpirationYear());
//        newPaymentInfo.setOwner(newPaymentInfoDTO.getOwner());
//        newPaymentInfo.setCardName(newPaymentInfoDTO.getCardName());
//
//        return paymentInfoRepository.save(newPaymentInfo);
//    }

    public User getProfile() {
        String email = getUsername();
        return userRepository.findByEmail(getUsername()).get();
    }

    public void deleteAccount() {  //TODO bu error verebilir
        String email = getUsername();
        User user = userRepository.findByEmail(email).get();

        ShoppingCart cart = shoppingCartRepository.findByUser(user);
        List<Model> models = developersModelRepository.findByUser(user.getUsername());

        for (Model model: models) {
            modelRepository.deleteById(model.getId());
        }

        developersModelRepository.deleteByUser(user);
        shoppingCartModelsRepository.deleteByShoppingCart(cart);
        shoppingCartRepository.deleteByUser(user);
        paymentRepository.deleteByUser(user);
        userRepository.delete(user);
    }

    public void changePassword(String newPassword) {
        User user = userRepository.findByEmail(getUsername()).get();
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }
}
