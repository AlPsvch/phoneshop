package com.es.phoneshop.web.controller.pages.cart;

public class AddToCartResponseForm {

    private String status;
    private String message;


    public AddToCartResponseForm() {
    }

    public AddToCartResponseForm(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
