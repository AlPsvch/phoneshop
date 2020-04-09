package com.es.core.service.implementation;

import com.es.core.model.stock.StockDao;
import com.es.core.service.PhoneStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PhoneStockServiceImpl implements PhoneStockService {

    @Resource
    private StockDao stockDao;

    @Override
    public boolean hasEnoughStock(Long id, Long quantity) {
        return quantity <= stockDao.getPhoneStock(id);
    }
}
