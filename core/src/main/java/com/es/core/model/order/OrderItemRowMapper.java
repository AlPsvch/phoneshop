package com.es.core.model.order;

import com.es.core.model.phone.PhoneDao;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

    private PhoneDao phoneDao;

    public OrderItemRowMapper(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();

        orderItem.setId(resultSet.getLong("id"));
        orderItem.setPhone(phoneDao.get(resultSet.getLong("phoneId")).orElse(null));
        orderItem.setQuantity(resultSet.getLong("quantity"));

        return orderItem;
    }
}
