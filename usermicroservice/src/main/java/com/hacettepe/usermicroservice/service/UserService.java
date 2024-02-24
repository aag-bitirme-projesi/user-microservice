package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.PaymentInfoDTO;
import com.hacettepe.usermicroservice.dto.UserUpdateDTO;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.model.PaymentInfo;
import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.repository.PaymentInfoRepository;
import com.hacettepe.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private StorageService storageService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findById(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(UserUpdateDTO new_user) throws UserNotFoundException {
        User user = userRepository.findById(new_user.getUsername()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User with username " + new_user.getUsername() + " not found");
        }

        if (new_user.getEmail() != null) {
            user.setEmail(new_user.getEmail());
        }

        // TODO UPDATE PASSWORD
        if (new_user.getPassword() != null) {
            user.setPassword(new_user.getPassword());
        }

        if (new_user.getCv() != null) {
            String pdfUrl = storageService.uploadCv(new_user.getCv());
            user.setCv(pdfUrl);
        }

        if (new_user.getGithub() != null) {
            user.setGithub(new_user.getGithub());
        }

        if (new_user.getPaymentInfo() != null) {
            PaymentInfo newPaymentInfo = getPaymentInfo(new_user);
            user.setPaymentInfo(newPaymentInfo);
        }

        return userRepository.save(user);
    }

    private PaymentInfo getPaymentInfo(UserUpdateDTO new_user) {
        PaymentInfo newPaymentInfo = new PaymentInfo();
        PaymentInfoDTO newPaymentInfoDTO = new_user.getPaymentInfo();

        newPaymentInfo.setCardNumber(newPaymentInfoDTO.getCardNumber());
        newPaymentInfo.setCvc(newPaymentInfoDTO.getCvc());
        newPaymentInfo.setExpirationMonth(newPaymentInfoDTO.getExpirationMonth());
        newPaymentInfo.setExpirationYear(newPaymentInfoDTO.getExpirationYear());
        newPaymentInfo.setOwner(newPaymentInfoDTO.getOwner());
        newPaymentInfo.setCardName(newPaymentInfoDTO.getCardName());

        return paymentInfoRepository.save(newPaymentInfo);
    }

}
