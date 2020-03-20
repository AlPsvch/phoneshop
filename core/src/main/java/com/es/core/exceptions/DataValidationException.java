package com.es.core.exceptions;

import org.springframework.validation.BindingResult;

public class DataValidationException extends RuntimeException {

    private final BindingResult bindingResult;

    public DataValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public DataValidationException(final String message, final BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
