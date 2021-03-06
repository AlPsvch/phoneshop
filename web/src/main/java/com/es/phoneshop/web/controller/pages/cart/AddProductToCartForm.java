package com.es.phoneshop.web.controller.pages.cart;

public class AddProductToCartForm {

    private Long phoneId;
    private String quantity;


    public AddProductToCartForm() {
    }

    public AddProductToCartForm(Long phoneId, String quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
