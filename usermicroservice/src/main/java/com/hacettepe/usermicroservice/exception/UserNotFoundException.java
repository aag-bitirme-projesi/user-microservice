package com.hacettepe.usermicroservice.exception;

public class UserNotFoundException extends  Exception {
    public  UserNotFoundException(String message) {
        super(message);
    }
}
