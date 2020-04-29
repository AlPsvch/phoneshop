package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {

    Optional<Phone> get(Long key);

    Optional<Phone> getWithPrice(Long key);

    void save(Phone phone);

    List<Phone> findAll(int offset, int limit);

    List<Phone> findAll(String query, String order, SortingDirection sortingDirection, int offset, int limit);

    int getTotalNumberOfProducts(String query);

}
