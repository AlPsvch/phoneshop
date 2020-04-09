package com.es.phoneshop.web.controller.pages.cart;

import com.es.core.service.PhoneStockService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Component
public class AddToCartValidator implements Validator {

    @Resource
    private PhoneStockService stockService;


    @Override
    public boolean supports(Class<?> aClass) {
        return AddProductToCartForm.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        AddProductToCartForm addToCartForm = (AddProductToCartForm) object;

        validateQuantity(addToCartForm, errors);
    }

    private void validateQuantity(AddProductToCartForm addToCartForm, Errors errors) {
        Long quantity = extractQuantityFromForm(addToCartForm);
        if (quantity == null) {
            errors.reject("quantity.invalid", "The value must must be a number");
            return;
        }
        if (quantity < 1) {
            errors.reject("quantity.negative", "The value must greater than 0");
        }
        if (!stockService.hasEnoughStock(addToCartForm.getPhoneId(), quantity)) {
            errors.reject("quantity.not.available", "Not enough stock for this product");
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
