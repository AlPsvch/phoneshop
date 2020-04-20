package com.es.phoneshop.web.controller.pages.order;

import com.es.core.cart.Cart;
import com.es.core.service.PhoneStockService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Service
public class StockAvailabilityValidator implements Validator {

    @Resource
    private PhoneStockService phoneStockService;


    @Override
    public boolean supports(Class<?> aClass) {
        return Cart.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        if (!itemsStockAvailable((Cart) object)) {
            errors.reject("stock.unavailable", "Stock of some products from cart not available");
        }
    }

    private Boolean itemsStockAvailable(Cart cart) {
        boolean removed = cart.getCartItems()
                .removeIf(cartItem -> !phoneStockService.hasEnoughStock(cartItem.getPhone().getId(), cartItem.getQuantity()));
        return !removed;
    }
}
