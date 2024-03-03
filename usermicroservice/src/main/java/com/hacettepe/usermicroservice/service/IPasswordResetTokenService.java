package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.exception.EmailSendingException;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;

public interface IPasswordResetTokenService {

    public String createToken(String email) throws UserNotFoundException, EmailSendingException;

    public void resetPassword(String token, String newPassword);

    public boolean validatePasswordResetToken(String token);
}
