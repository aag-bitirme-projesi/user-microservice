package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.UserInfoDto;
import com.hacettepe.usermicroservice.dto.UserUpdateDTO;
import com.hacettepe.usermicroservice.exception.PasswordMatchException;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.model.PaymentInfo;
import com.hacettepe.usermicroservice.model.User;

import java.io.IOException;

public interface IUserService {

    UserInfoDto getUser(String email) throws UserNotFoundException;

    User updateUser(UserUpdateDTO new_user) throws UserNotFoundException, PasswordMatchException, IOException;

    User getProfile();

    void deleteAccount();

    void changePassword(String newPassword);
}
