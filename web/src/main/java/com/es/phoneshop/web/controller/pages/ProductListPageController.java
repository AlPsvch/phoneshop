package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.SortingDirection;
import com.es.core.service.CartService;
import com.es.core.service.PhonePagingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {

    private static final String PRODUCT_PAGE_ATTRIBUTE = "productPage";

    private static final String DEFAULT_ORDER_VALUE = "brand";

    private static final String DEFAULT_ORDER_DIRECTION = "ASC";

    private static final String DEFAULT_PAGE_NUMBER = "1";


    @Resource
    private PhonePagingService phonePagingService;

    @Resource
    private CartService cartService;


    @GetMapping
    public String showProductList(@RequestParam(defaultValue = DEFAULT_ORDER_VALUE) String sortBy,
                                  @RequestParam(defaultValue = DEFAULT_ORDER_DIRECTION) SortingDirection sortDirection,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(required = false) String query,
                                  Model model) {
        model.addAttribute(PRODUCT_PAGE_ATTRIBUTE, phonePagingService.formProductPage(query, sortBy, sortDirection, page));
        cartService.insertMiniCart(model);
        return "productList";
    }
}
