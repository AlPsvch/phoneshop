package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exceptions.OutOfStockException;

import java.util.Optional;

public interface OrderService {
    Order createOrder(Cart cart);
    Long placeOrder(Order order) throws OutOfStockException;
    Boolean quantitiesAreValid(Order order);
    Optional<Order> getOrder(Long orderId);
}
