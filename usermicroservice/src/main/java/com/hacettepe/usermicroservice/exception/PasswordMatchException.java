package com.hacettepe.usermicroservice.exception;

public class PasswordMatchException extends Exception{
    public PasswordMatchException(String message) {
        super(message);
    }
}
