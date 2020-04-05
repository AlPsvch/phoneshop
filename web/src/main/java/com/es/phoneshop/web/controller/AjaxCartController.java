package com.es.phoneshop.web.controller;

import com.es.core.exceptions.DataValidationException;
import com.es.core.service.CartService;
import com.es.phoneshop.web.controller.pages.cart.AddProductToCartForm;
import com.es.phoneshop.web.controller.pages.cart.AddToCartValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    @Resource
    private CartService cartService;

    @Resource
    private AddToCartValidator addToCartValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addToCartValidator);
    }


    @PostMapping
    public @ResponseBody
    ResponseEntity<String> addPhone(@RequestBody @Validated AddProductToCartForm addToCartForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataValidationException(bindingResult);
        }
        cartService.addPhone(addToCartForm.getPhoneId(), Long.valueOf(addToCartForm.getQuantity()));
        return ResponseEntity.ok().build();
    }
}
