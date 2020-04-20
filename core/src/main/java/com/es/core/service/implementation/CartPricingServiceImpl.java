package com.es.core.service.implementation;

import com.es.core.cart.Cart;
import com.es.core.service.CartPricingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartPricingServiceImpl implements CartPricingService {

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Override
    public void recalculateCartPrice(Cart cart) {
        BigDecimal newSubtotalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        cart.setSubtotalPrice(newSubtotalPrice);

        BigDecimal totalPrice = deliveryPrice.add(newSubtotalPrice);

        cart.setDeliveryPrice(deliveryPrice);
        cart.setTotalPrice(totalPrice);
    }
}
