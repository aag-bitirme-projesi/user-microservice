package com.hacettepe.usermicroservice.exception;

public class EmailSendingException extends  Exception{

    public EmailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
