package com.es.core.model.order;

import com.es.core.exceptions.PhoneNotFoundException;
import com.es.core.model.phone.PhoneDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {

    @Resource
    private PhoneDao phoneDao;

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();

        orderItem.setId(resultSet.getLong("id"));
        orderItem.setPhone(phoneDao.get(resultSet.getLong("phoneId")).orElseThrow(PhoneNotFoundException::new));
        orderItem.setQuantity(resultSet.getLong("quantity"));

        return orderItem;
    }
}
