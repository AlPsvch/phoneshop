package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.phoneshop.web.controller.pages.cart.CartItemsUpdateForm;
import com.es.phoneshop.web.controller.pages.cart.CartUpdateValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    private static final String CART = "cart";
    private static final String MINI_CART_ATTRIBUTE = "miniCart";
    private static final String CART_ITEMS_UPDATE_ATTRIBUTE = "cartItemsUpdateForm";
    private static final String CONTAIN_ERRORS = "containErrors";

    @Resource
    private CartService cartService;

    @Resource
    private CartUpdateValidator cartUpdateValidator;

    @InitBinder("cartItemsUpdateForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(cartUpdateValidator);
    }


    @GetMapping
    public String getCart(Model model) {
        model.addAttribute(CART_ITEMS_UPDATE_ATTRIBUTE, new CartItemsUpdateForm());
        model.addAttribute(CART, cartService.getCart());
        model.addAttribute(MINI_CART_ATTRIBUTE, cartService.getMiniCart());
        return "cartPage";
    }

    @PostMapping("/update")
    public String updateCart(@ModelAttribute @Validated CartItemsUpdateForm cartItemsUpdateForm,
                             BindingResult bindingResult, Model model) {

        boolean containErrors = bindingResult.hasErrors();
        model.addAttribute(CONTAIN_ERRORS, containErrors);

        if (!containErrors) {
            Map<Long, Long> cartItemsMap = cartService.formMapForUpdate(cartItemsUpdateForm.getCartItems());
            cartService.update(cartItemsMap);
        }

        model.addAttribute(CART, cartService.getCart());
        model.addAttribute(MINI_CART_ATTRIBUTE, cartService.getMiniCart());
        return "cartPage";
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id, Model model) {
        cartService.remove(id);
        return "redirect:/cart";
    }
}
