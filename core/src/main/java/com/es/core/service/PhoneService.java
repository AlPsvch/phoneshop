package com.es.core.service;

import com.es.core.model.phone.Phone;

import java.util.List;

public interface PhoneService {

    public static final Integer PRODUCTS_TO_DISPLAY_NUMBER = 20;

    List<Phone> findPhones(String query, String order, String orderDirection, int page);

    int getTotalNumberOfPages(String query);

}
