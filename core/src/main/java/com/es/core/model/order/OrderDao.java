package com.es.core.model.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Optional<Order> get(Long key);

    Long save(Order order);

    List<Order> findAll();

    void updateOrderStatus(Long id, OrderStatus orderStatus);
}
