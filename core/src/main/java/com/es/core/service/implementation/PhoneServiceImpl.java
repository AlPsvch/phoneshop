package com.es.core.service.implementation;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.service.PhoneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<Phone> findPhones(String query, String order, String orderDirection, int page) {
        return phoneDao.findAll(query, order, orderDirection, (page - 1) * PRODUCTS_TO_DISPLAY_NUMBER, PRODUCTS_TO_DISPLAY_NUMBER);
    }

    @Override
    public int getTotalNumberOfPages(String query) {
        int numOfProducts = phoneDao.getTotalNumberOfProducts(query);
        return (numOfProducts % PRODUCTS_TO_DISPLAY_NUMBER == 0 ? 0 : 1) + numOfProducts / PRODUCTS_TO_DISPLAY_NUMBER;
    }
}
