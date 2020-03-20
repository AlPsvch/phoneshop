package com.es.core.service;

import com.es.core.model.phone.ProductPage;
import com.es.core.model.phone.SortingDirection;

public interface PhonePagingService {

    ProductPage formProductPage(String query, String orderBy, SortingDirection sortDirection, Integer page);

}
