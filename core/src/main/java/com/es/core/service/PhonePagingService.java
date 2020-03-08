package com.es.core.service;

import com.es.core.model.phone.ProductPage;

public interface PhonePagingService {

    ProductPage formProductPage(String query, String orderBy, String orderDir, Integer page);

}
