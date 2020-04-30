package com.es.phoneshop.web.controller.pages.cart;

import com.es.core.service.PhonePagingService;
import com.es.core.service.PhoneStockService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.List;

@Component
public class QuickOrderValidator implements Validator {

    private static final String QUICK_ITEM_CODE_FORM = "quickOrderItems[%s].code";
    private static final String QUICK_ITEM_QUANTITY_FORM = "quickOrderItems[%s].quantity";

    @Resource
    private PhonePagingService pagingService;

    @Resource
    private PhoneStockService stockService;


    @Override
    public boolean supports(Class<?> aClass) {
        return QuickOrderForm.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        QuickOrderForm quickOrderForm = (QuickOrderForm) object;

        validateInput(quickOrderForm, errors);
    }

    private void validateInput(QuickOrderForm quickOrderForm, Errors errors) {
        List<QuickOrderItem> quickOrderItems = quickOrderForm.getQuickOrderItems();
        int i = 0;
        for (QuickOrderItem item : quickOrderItems) {
            validateInput(item, i++, errors);
        }
    }

    private void validateInput(QuickOrderItem item, int index, Errors errors) {
        validateCode(item, index, errors);
        validateQuantity(item, index, errors);
    }

    private void validateCode(QuickOrderItem item, int index, Errors errors) {
        Long code = item.getCode();
        if (code == null) {
            errors.rejectValue(String.format(QUICK_ITEM_CODE_FORM, index), "code.invalid", "The value must must be a number");
            return;
        }
        if (code <= 0) {
            errors.rejectValue(String.format(QUICK_ITEM_CODE_FORM, index), "code.negative", "The value must be greater than 0");
            return;
        }
        if (!pagingService.phoneExists(code)) {
            errors.rejectValue(String.format(QUICK_ITEM_CODE_FORM, index), "code.not.found", "Phone not found");
        }
    }

    private void validateQuantity(QuickOrderItem item, int index, Errors errors) {
        Long quantity = item.getQuantity();
        if (quantity == null) {
            errors.rejectValue(String.format(QUICK_ITEM_QUANTITY_FORM, index), "quantity.invalid", "The value must must be a number");
            return;
        }
        if (quantity <= 0) {
            errors.rejectValue(String.format(QUICK_ITEM_QUANTITY_FORM, index), "quantity.negative", "The value must be greater than 0");
        }
    }
}
