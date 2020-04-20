package com.es.core.model.order;

import java.util.Optional;

public interface OrderDao {

    Optional<Order> get(Long key);

    Long save(Order order);
}
