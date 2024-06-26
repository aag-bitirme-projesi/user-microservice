package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.UserInfoDto;
import com.hacettepe.usermicroservice.exception.PasswordMatchException;
import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.repository.IPaymentInfoRepository;
import com.hacettepe.usermicroservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.hacettepe.usermicroservice.dto.PaymentInfoDTO;
import com.hacettepe.usermicroservice.dto.UserUpdateDTO;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.model.PaymentInfo;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IPaymentInfoRepository paymentInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    @Override
    @ExceptionHandler({UserNotFoundException.class})
    public UserInfoDto getUser(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }

        String username = user.getUsername();
        return UserInfoDto.builder()
                .username(username)
                .email(user.getEmail())
                .profilePicture(s3Service.getProfilePicture(username))
                .cv(s3Service.getCV(username))
                .github(user.getGithub())
                .paymentInfo(getPaymentInfoDto(user))
                .build();
    }

    @Override
    @ExceptionHandler({UserNotFoundException.class})
    public User updateUser(UserUpdateDTO new_user) throws UserNotFoundException, PasswordMatchException, IOException {
        User user = userRepository.findById(new_user.getUsername()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User with username " + new_user.getUsername() + " not found");
        }

        if (new_user.getEmail() != null) {
            user.setEmail(new_user.getEmail());
        }

        if (new_user.getPassword() != null) {
            if (!passwordEncoder.matches(new_user.getOldPassword(), user.getPassword()))
                throw new PasswordMatchException("Old password doesn't match");
            user.setPassword(passwordEncoder.encode(new_user.getPassword()));
        }

        if (new_user.getProfilePicture() != null) {
            s3Service.uploadProfilePicture(user.getUsername(), new_user.getProfilePicture());
        }

        if (new_user.getCv() != null) {
            s3Service.uploadCV(user.getUsername(), new_user.getCv());
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

    private PaymentInfoDTO getPaymentInfoDto(User user) {
        PaymentInfo paymentInfo = user.getPaymentInfo();

        return PaymentInfoDTO.builder()
                .cardNumber(paymentInfo.getCardNumber())
                .cvc(paymentInfo.getCvc())
                .expirationMonth(paymentInfo.getExpirationMonth())
                .expirationYear(paymentInfo.getExpirationYear())
                .owner(paymentInfo.getOwner())
                .cardName(paymentInfo.getCardName())
                .build();
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
