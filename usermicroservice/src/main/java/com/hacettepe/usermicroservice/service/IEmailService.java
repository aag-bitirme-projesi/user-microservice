package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.exception.EmailSendingException;

public interface IEmailService {

    public void sendPasswordResetEmail(String to, String subject, String text) throws EmailSendingException;

}
