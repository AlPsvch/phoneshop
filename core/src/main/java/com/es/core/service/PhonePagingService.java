package com.es.core.service;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.ProductPage;
import com.es.core.model.phone.SortingDirection;

import java.util.Optional;

public interface PhonePagingService {

    ProductPage formProductPage(String query, String orderBy, SortingDirection sortDirection, Integer page);

    Optional<Phone> getPhone(Long id);

    boolean phoneExists(Long id);
}
