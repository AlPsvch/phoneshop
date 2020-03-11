package com.es.phoneshop.web.controller;

import com.es.core.service.CartService;
import com.es.phoneshop.web.controller.pages.cart.AddProductToCartForm;
import com.es.phoneshop.web.controller.pages.cart.AddToCartResponseForm;
import com.es.phoneshop.web.controller.pages.cart.AddToCartValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.bind.ValidationException;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    private static final String OK_STATUS = "ok";
    private static final String ERROR_STATUS = "error";
    private static final String SUCCESSFULLY_ADDED = "Successfully added to cart";
    private static final String ERROR_ADDING_TO_CART = "Error while adding to cart, try again later";

    @Resource
    private CartService cartService;

    @Resource
    private AddToCartValidator addToCartValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(addToCartValidator);
    }


    @PostMapping
    public @ResponseBody
    AddToCartResponseForm addPhone(@RequestBody @Validated AddProductToCartForm addToCartForm, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
            }

            cartService.addPhone(addToCartForm.getPhoneId(), Long.valueOf(addToCartForm.getQuantity()));

            return new AddToCartResponseForm(OK_STATUS, SUCCESSFULLY_ADDED);
        } catch (ValidationException e) {
            return new AddToCartResponseForm(ERROR_STATUS, e.getMessage());
        } catch (Exception e) {
            return new AddToCartResponseForm(ERROR_STATUS, ERROR_ADDING_TO_CART);
        }
    }
}
