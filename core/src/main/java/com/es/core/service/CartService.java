package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.cart.MiniCart;
import com.es.core.model.phone.Phone;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    /**
     * @param items key: {@link Phone#id}
     *              value: quantity
     */
    void update(Map<Long, Long> items);

    void remove(Long phoneId);

    Map<Long, Long> formMapForUpdate(Map<Long, String> cartItems);

    MiniCart getMiniCart();

    void clearCart();

    void addPhones(Map<Long, Long> phones);
}
