package com.es.phoneshop.web.controller.pages.cart;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddToCartValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return AddProductToCartForm.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        AddProductToCartForm addToCartForm = (AddProductToCartForm) object;

        long quantity;
        try {
            quantity = Long.parseLong(addToCartForm.getQuantity());
        } catch (NumberFormatException e) {
            errors.reject("quantity.invalid", "The value must must be a number");
            return;
        }

        if(quantity < 1) {
            errors.reject("quantity.negative", "The value must greater than 0");
        }
    }
}
