package com.es.phoneshop.web.controller.pages;

import com.es.core.service.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {

    private static final String PHONES_ATTRIBUTE = "phones";

    private static final String ORDER_ATTRIBUTE = "order";

    private static final String ORDER_DIRECTION_ATTRIBUTE = "orderDir";

    private static final String QUERY_ATTRIBUTE = "query";

    private static final String TOTAL_PAGES_NUMBER_ATTRIBUTE = "totalNumOfPages";

    private static final String CURRENT_PAGE_ATTRIBUTE = "currentPage";

    private static final String DEFAULT_ORDER_VALUE = "brand";

    private static final String DEFAULT_ORDER_DIRECTION = "ASC";

    private static final String DEFAULT_PAGE_NUMBER = "1";


    @Resource
    private PhoneService phoneService;


    @GetMapping
    public String showProductList(@RequestParam(defaultValue = DEFAULT_ORDER_VALUE) String order,
                                  @RequestParam(defaultValue = DEFAULT_ORDER_DIRECTION) String orderDir,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(required = false) String query,
                                  Model model) {
        model.addAttribute(PHONES_ATTRIBUTE, phoneService.findPhones(query, order, orderDir, page));
        model.addAttribute(ORDER_ATTRIBUTE, order);
        model.addAttribute(ORDER_DIRECTION_ATTRIBUTE, orderDir);
        model.addAttribute(QUERY_ATTRIBUTE, query);
        model.addAttribute(TOTAL_PAGES_NUMBER_ATTRIBUTE, phoneService.getTotalNumberOfPages(query));
        model.addAttribute(CURRENT_PAGE_ATTRIBUTE, page);
        return "productList";
    }
}
