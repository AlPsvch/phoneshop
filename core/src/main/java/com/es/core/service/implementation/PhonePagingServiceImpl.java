package com.es.core.service.implementation;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.ProductPage;
import com.es.core.service.PhonePagingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhonePagingServiceImpl implements PhonePagingService {

    private static final Integer PRODUCTS_TO_DISPLAY_NUMBER = 20;

    @Resource
    private PhoneDao phoneDao;


    @Override
    public ProductPage formProductPage(String query, String orderBy, String orderDir, Integer page) {
        ProductPage productPage = new ProductPage(orderBy, orderDir, query, page);
        productPage.setPhones(findPhones(query, orderBy, orderDir, page));
        productPage.setTotalNumOfPages(getTotalNumberOfPages(query));
        return productPage;
    }


    private List<Phone> findPhones(String query, String order, String orderDirection, int page) {
        return phoneDao.findAll(query, order, orderDirection, (page - 1) * PRODUCTS_TO_DISPLAY_NUMBER, PRODUCTS_TO_DISPLAY_NUMBER);
    }

    private int getTotalNumberOfPages(String query) {
        int numOfProducts = phoneDao.getTotalNumberOfProducts(query);
        return (numOfProducts % PRODUCTS_TO_DISPLAY_NUMBER == 0 ? 0 : 1) + numOfProducts / PRODUCTS_TO_DISPLAY_NUMBER;
    }
}
