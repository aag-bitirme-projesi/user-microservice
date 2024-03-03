package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.UserUpdateDTO;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.model.User;

import java.util.Optional;

public interface IUserService {

    User updateUser(UserUpdateDTO new_user) throws UserNotFoundException;

}
