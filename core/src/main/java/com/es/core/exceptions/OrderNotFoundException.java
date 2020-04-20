package com.es.core.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("Oops, the order was not found");
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
