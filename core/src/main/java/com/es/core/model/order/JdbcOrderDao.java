package com.es.core.model.order;

import com.es.core.model.phone.PhoneDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Component
@Transactional
public class JdbcOrderDao implements OrderDao {

    private static final String GET_ORDER_BY_KEY_QUERY = "SELECT * FROM orders WHERE orderId = ?";

    private static final String GET_ORDER_ITEMS_BY_ORDER_ID = "SELECT * FROM item2order WHERE orderId = ?";

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    PhoneDao phoneDao;

    @Resource
    SimpleJdbcInsert orderSimpleJdbcInsert;

    @Resource
    SimpleJdbcInsert orderItemSimpleJdbcInsert;

    @Resource
    BeanPropertyRowMapper<Order> orderBeanPropertyRowMapper;


    @Override
    public Optional<Order> get(Long key) {
        Objects.requireNonNull(key, "Key must not be null");

        Order order;
        try {
            order = jdbcTemplate.queryForObject(GET_ORDER_BY_KEY_QUERY, orderBeanPropertyRowMapper, key);
            order.setId(key);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        order.setOrderItems(extractOrderItems(order));
        return Optional.of(order);
    }

    private List<OrderItem> extractOrderItems(Order order) {
        List<OrderItem> orderItems = jdbcTemplate.query(GET_ORDER_ITEMS_BY_ORDER_ID, new OrderItemRowMapper(phoneDao), order.getId());
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        return orderItems;
    }

    @Override
    public Long save(Order order) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(order);
        Number orderId = orderSimpleJdbcInsert.executeAndReturnKey(parameterSource);
        order.setId(orderId.longValue());

        saveOrderItems(order.getOrderItems());

        return (Long) orderId;
    }

    private void saveOrderItems(List<OrderItem> orderItems) {
        orderItems.forEach(this::save);
    }

    private void save(OrderItem orderItem) {
        Map<String, Object> params = new HashMap<>();

        params.put("orderId", orderItem.getOrder().getId());
        params.put("phoneId", orderItem.getPhone().getId());
        params.put("quantity", orderItem.getQuantity());

        orderItemSimpleJdbcInsert.execute(params);
    }
}
