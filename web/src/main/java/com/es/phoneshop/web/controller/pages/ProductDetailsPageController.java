package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.service.PhonePagingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    private static final String PHONE = "phone";

    @Resource
    private PhonePagingService phonePagingService;


    @GetMapping(value = "/{id}")
    public String showPhone(@PathVariable Long id, Model model) {
        Optional<Phone> phoneOptional = phonePagingService.getPhone(id);
        phoneOptional.ifPresent(phone -> model.addAttribute(PHONE, phone));
        return "productDetails";
    }
}
