package com.es.core.exceptions;

public class PhoneNotFoundException extends RuntimeException {

    public PhoneNotFoundException() {
        super("Oops, the phone was not found, try again later");
    }

    public PhoneNotFoundException(String message) {
        super(message);
    }
}
