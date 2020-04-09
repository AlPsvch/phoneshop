package com.es.core.model.stock;

public interface StockDao {

    int getPhoneStock(Long id);

    void reducePhoneStock(Long id, Long quantity);

}
