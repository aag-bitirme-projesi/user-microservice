package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.auth.PasswordResetToken;
import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.repository.IPasswordResetTokenRepository;
import com.hacettepe.usermicroservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IPasswordResetTokenRepository passwordResetTokenRepository;

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).get();
    }

//    public void createPasswordResetToken(User user, String token) {
//        PasswordResetToken myToken = PasswordResetToken.builder()
//                                                        .token(token)
//                                                        .user(user)
//                                                        .build();
//        passwordResetTokenRepository.save(myToken);
//    }
}
