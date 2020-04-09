package com.es.core.cart;

import java.math.BigDecimal;

public class MiniCart {
    private Long totalCount;
    private BigDecimal totalPrice;

    public MiniCart() {
    }

    public MiniCart(Long totalCount, BigDecimal totalPrice) {
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
