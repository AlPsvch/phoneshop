package com.es.core.service;

import com.es.core.cart.Cart;

public interface CartPricingService {

    void recalculateTotalPrice(Cart cart);

}
