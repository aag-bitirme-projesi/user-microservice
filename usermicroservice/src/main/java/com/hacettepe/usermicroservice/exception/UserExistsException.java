package com.hacettepe.usermicroservice.exception;

public class UserExistsException extends Exception {
    public  UserExistsException(String message) {
        super(message);
    }
}
