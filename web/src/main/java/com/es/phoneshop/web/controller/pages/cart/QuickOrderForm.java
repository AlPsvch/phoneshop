package com.es.phoneshop.web.controller.pages.cart;

import java.util.ArrayList;
import java.util.List;

public class QuickOrderForm {

    private static final Integer INITIAL_CAPACITY = 10;

    private List<QuickOrderItem> quickOrderItems;

    public QuickOrderForm() {
        quickOrderItems = new ArrayList<>(INITIAL_CAPACITY);
        for(int i = 0; i < INITIAL_CAPACITY ; i++) {
            quickOrderItems.add(new QuickOrderItem());
        }
    }

    public QuickOrderForm(List<QuickOrderItem> quickOrderItems) {
        this.quickOrderItems = quickOrderItems;
    }

    public List<QuickOrderItem> getQuickOrderItems() {
        return quickOrderItems;
    }

    public void setQuickOrderItems(List<QuickOrderItem> quickOrderItems) {
        this.quickOrderItems = quickOrderItems;
    }
}
