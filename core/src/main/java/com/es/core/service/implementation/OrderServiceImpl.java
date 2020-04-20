package com.es.core.service.implementation;

import com.es.core.cart.Cart;
import com.es.core.exceptions.OrderNotFoundException;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.stock.StockDao;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import com.es.core.service.PhoneStockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${delivery.price}")
    private Integer deliveryPrice;

    @Resource
    private OrderDao orderDao;

    @Resource
    private StockDao stockDao;

    @Resource
    private PhoneStockService phoneStockService;

    @Resource
    private CartService cartService;

    @Override
    public Order createOrder(Cart cart) {
        if(!cartService.quantitiesAreValid()) {
            throw new OutOfStockException("Some products from your cart are out of stock, they were removed from cart");
        }

        Order order = new Order();

        order.setStatus(OrderStatus.NEW);
        order.setSubtotal(cart.getSubtotalPrice());
        order.setDeliveryPrice(cart.getDeliveryPrice());
        order.setTotalPrice(cart.getTotalPrice());
        fillOrderItems(order, cart);

        return order;
    }

    private void fillOrderItems(Order order, Cart cart) {
        order.setOrderItems(cart.getCartItems().stream()
                .map(cartItem -> new OrderItem(cartItem.getPhone(), order, cartItem.getQuantity()))
                .collect(Collectors.toList()));
    }

    @Override
    public Long placeOrder(Order order) {
        Long orderId = orderDao.save(order);
        cartService.clearCart();

        order.getOrderItems()
                .forEach(orderItem -> stockDao.reducePhoneStock(orderItem.getPhone().getId(), orderItem.getQuantity()));

        return orderId;
    }

    @Override
    public Boolean quantitiesAreValid(Order order) {
        boolean removed = order.getOrderItems()
                .removeIf(orderItem -> !phoneStockService.hasEnoughStock(orderItem.getPhone().getId(), orderItem.getQuantity()));
        return !removed;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderDao.get(orderId).orElseThrow(OrderNotFoundException::new);
    }
}
