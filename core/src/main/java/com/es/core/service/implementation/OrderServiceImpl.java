package com.es.core.service.implementation;

import com.es.core.cart.Cart;
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
import java.math.BigDecimal;
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
        Order order = new Order();

        BigDecimal subtotalPrice = cart.getTotalPrice();
        BigDecimal delPrice = new BigDecimal(deliveryPrice);

        order.setStatus(OrderStatus.NEW);
        order.setSubtotal(subtotalPrice);
        order.setDeliveryPrice(delPrice);
        order.setTotalPrice(delPrice.add(subtotalPrice));
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
    public Optional<Order> getOrder(Long orderId) {
        return orderDao.get(orderId);
    }
}
