package com.es.core.model.stock;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Transactional
public class JdbcStockDao implements StockDao {

    private static final String GET_PHONE_STOCK_QUERY = "SELECT stock FROM stocks WHERE phoneId = ?";

    private static final String UPDATE_PHONE_STOCK_QUERY = "UPDATE stocks SET stock = ? WHERE phoneId = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;


    @Override
    public int getPhoneStock(Long id) {
        return jdbcTemplate.queryForObject(GET_PHONE_STOCK_QUERY, new Object[]{id}, Integer.class);
    }

    @Override
    public void reducePhoneStock(Long id, Long quantity) {
        jdbcTemplate.update(UPDATE_PHONE_STOCK_QUERY, getPhoneStock(id) - quantity, id);
    }
}
