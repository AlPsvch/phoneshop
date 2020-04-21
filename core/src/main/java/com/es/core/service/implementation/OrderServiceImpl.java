package com.es.core.service.implementation;

import com.es.core.cart.Cart;
import com.es.core.exceptions.OrderNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.stock.StockDao;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StockDao stockDao;

    @Resource
    private CartService cartService;


    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();

        order.setStatus(OrderStatus.NEW);
        order.setSubtotal(cart.getSubtotalPrice());
        order.setDeliveryPrice(cart.getDeliveryPrice());
        order.setTotalPrice(cart.getTotalPrice());
        order.setCreationDate(getCurrentDateTime());
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
    public Order getOrder(Long orderId) {
        return orderDao.get(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public List<Order> getOrders() {
        return orderDao.findAll();
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        orderDao.updateOrderStatus(orderId, orderStatus);
    }

    private Date getCurrentDateTime() {
        java.util.Date currentTime = new java.util.Date();
        return new Date(currentTime.getTime());
    }
}
