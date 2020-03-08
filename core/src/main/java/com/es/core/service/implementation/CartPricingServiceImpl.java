package com.es.core.service.implementation;

import com.es.core.cart.Cart;
import com.es.core.service.CartPricingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartPricingServiceImpl implements CartPricingService {

    @Override
    public void recalculateTotalPrice(Cart cart) {
        BigDecimal newTotalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0));

        cart.setTotalPrice(newTotalPrice);
    }
}
