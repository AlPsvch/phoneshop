package com.es.core.service.implementation;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(Cart cart) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        throw new UnsupportedOperationException("TODO");
    }
}
