package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.phoneshop.web.controller.pages.cart.QuickOrderForm;
import com.es.phoneshop.web.controller.pages.cart.QuickOrderItem;
import com.es.phoneshop.web.controller.pages.cart.QuickOrderValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/quickOrder")
public class QuickOrderController {

    private static final String QUICK_ORDER_FORM = "quickOrderForm";
    private static final String CONTAIN_ERRORS = "containErrors";

    @Resource
    private CartService cartService;

    @Resource
    private QuickOrderValidator quickOrderValidator;

    @InitBinder("quickOrderForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(quickOrderValidator);
    }


    @GetMapping
    public String getQuickOrderPage(Model model) {
        model.addAttribute(QUICK_ORDER_FORM, new QuickOrderForm());
        return "quickOrderPage";
    }

    @PostMapping
    public String quickOrder(@ModelAttribute @Validated QuickOrderForm quickOrderForm, BindingResult bindingResult, Model model) {
        boolean containErrors = bindingResult.hasErrors();
        model.addAttribute(CONTAIN_ERRORS, containErrors);

        Map<Long, Long> phonesToAdd = extractPhones(quickOrderForm.getQuickOrderItems(), bindingResult);
        cartService.addPhones(phonesToAdd);

        return "quickOrderPage";
    }

    private Map<Long, Long> extractPhones(List<QuickOrderItem> items, BindingResult bindingResult) {
        Map<Long, Long> phones = new HashMap<>();
        int i = 0;
        for (QuickOrderItem orderItem : items) {
            if (!(bindingResult.hasFieldErrors("quickOrderItems[" + i + "].code")
                    || bindingResult.hasFieldErrors("quickOrderItems[" + i + "].quantity"))) {

                phones.put(Long.valueOf(orderItem.getCode()), Long.valueOf(orderItem.getQuantity()));
                orderItem.setCode("");
                orderItem.setQuantity("");
            }
            i++;
        }
        return phones;
    }
}
