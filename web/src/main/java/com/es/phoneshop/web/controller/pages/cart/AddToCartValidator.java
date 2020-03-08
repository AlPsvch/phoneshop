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

        Long quantity = extractQuantityFromForm(addToCartForm);
        if (quantity == null) {
            errors.reject("quantity.invalid", "The value must must be a number");
            return;
        }

        if (quantity < 1) {
            errors.reject("quantity.negative", "The value must greater than 0");
        }
    }

    private Long extractQuantityFromForm(AddProductToCartForm toCartForm) {
        try {
            return Long.parseLong(toCartForm.getQuantity());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
