package com.hacettepe.usermicroservice.exception;

public class UnableToPayException extends Exception{
    public  UnableToPayException(String message) {
        super(message);
    }
}
