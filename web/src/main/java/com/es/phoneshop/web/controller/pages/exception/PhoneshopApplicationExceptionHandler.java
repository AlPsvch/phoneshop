package com.es.phoneshop.web.controller.pages.exception;

import com.es.core.exceptions.PhoneNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;

@ControllerAdvice
public class PhoneshopApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PhoneNotFoundException.class})
    protected ResponseEntity<String> handlePhoneNotFoundException(PhoneNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<String> handleValidationException(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
