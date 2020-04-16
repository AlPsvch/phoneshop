package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import com.es.phoneshop.web.controller.pages.order.OrderForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {

    private static final String ORDER_ATTRIBUTE = "order";

    private static final String ORDER_FORM_ATTRIBUTE = "orderForm";

    private static final String OUT_OF_STOCK = "outOfStock";

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;


    @GetMapping
    public String getOrder(Model model) {
        Order order = orderService.createOrder(cartService.getCart());
        model.addAttribute(ORDER_ATTRIBUTE, order);
        model.addAttribute(ORDER_FORM_ATTRIBUTE, new OrderForm());
        return "orderPage";
    }

    @PostMapping
    public String placeOrder(@ModelAttribute @Valid OrderForm orderForm, BindingResult bindingResult, Model model) {
        Order order = orderService.createOrder(cartService.getCart());

        if (bindingResult.hasErrors()) {
            model.addAttribute(ORDER_ATTRIBUTE, order);
            return "orderPage";
        }

        if (!orderService.quantitiesAreValid(order)) {
            model.addAttribute(OUT_OF_STOCK, Boolean.TRUE);
            model.addAttribute(ORDER_ATTRIBUTE, order);
            return "orderPage";
        }

        fillOrderInformation(order, orderForm);
        Long id = orderService.placeOrder(order);

        return "redirect:/orderOverview/" + id;
    }

    private void fillOrderInformation(Order order, OrderForm orderForm) {
        order.setFirstName(orderForm.getFirstName());
        order.setLastName(orderForm.getLastName());
        order.setDeliveryAddress(orderForm.getAddress());
        order.setContactPhoneNo(orderForm.getPhone());
        order.setAdditionalInformation(orderForm.getAdditionalInfo());
    }
}
