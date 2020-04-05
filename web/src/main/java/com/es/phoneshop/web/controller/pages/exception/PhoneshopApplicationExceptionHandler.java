package com.es.phoneshop.web.controller.pages.exception;

import com.es.core.exceptions.DataValidationException;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.exceptions.PhoneNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PhoneshopApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PhoneNotFoundException.class})
    protected ResponseEntity<String> handlePhoneNotFoundException(PhoneNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DataValidationException.class)
    protected ResponseEntity<String> handleDataValidationException(DataValidationException e) {
        return ResponseEntity.badRequest().body(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(OutOfStockException.class)
    protected ResponseEntity<String> handleOutOfStockException(OutOfStockException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
