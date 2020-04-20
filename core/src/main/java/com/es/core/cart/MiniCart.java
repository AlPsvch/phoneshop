package com.es.core.cart;

import java.math.BigDecimal;

public class MiniCart {
    private Long totalCount;
    private BigDecimal subtotalPrice;

    public MiniCart() {
    }

    public MiniCart(Long totalCount, BigDecimal totalPrice) {
        this.totalCount = totalCount;
        this.subtotalPrice = totalPrice;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(BigDecimal subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }
}
