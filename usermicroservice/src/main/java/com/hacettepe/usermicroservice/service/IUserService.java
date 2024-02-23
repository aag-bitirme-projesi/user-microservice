package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.auth.PasswordResetToken;
import com.hacettepe.usermicroservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface IUserService {
    User findUserByEmail(String email);

//    void createPasswordResetToken(User user, String token);
}
