package com.es.core.service.implementation;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.MiniCart;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.exceptions.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.service.CartService;
import com.es.core.service.PhoneStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class HttpSessionCartService implements CartService {

    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private CartPricingServiceImpl cartPricingService;

    @Resource
    private PhoneStockService phoneStockService;


    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Phone phoneToAdd = phoneDao.get(phoneId).orElseThrow(PhoneNotFoundException::new);

        Long currentQuantity = cart.getCartItems().stream()
                .filter(ci -> ci.getPhone().getId().equals(phoneId))
                .findFirst().map(CartItem::getQuantity).orElse(0L);

        if (!phoneStockService.hasEnoughStock(phoneId, currentQuantity + quantity)) {
            throw new OutOfStockException();
        }

        if (currentQuantity != 0L) {
            cart.getCartItems().stream().filter(cartItem -> cartItem.getPhone().getId().equals(phoneId))
                    .findFirst().ifPresent(cartItem -> cartItem.setQuantity(currentQuantity + quantity));
        } else {
            CartItem item = new CartItem(phoneToAdd, quantity);
            cart.getCartItems().add(item);
        }

        cartPricingService.recalculateCartPrice(cart);
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            cart.getCartItems().stream().filter(cartItem -> item.getKey().equals(cartItem.getPhone().getId())).findFirst()
                    .ifPresent(cartItem -> cartItem.setQuantity(item.getValue()));
        }
        cartPricingService.recalculateCartPrice(cart);
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> phoneId.equals(cartItem.getPhone().getId()));
        cartPricingService.recalculateCartPrice(cart);
    }

    @Override
    public Map<Long, Long> formMapForUpdate(Map<Long, String> cartItems) {
        Map<Long, Long> mapForUpdate = new HashMap<>();
        cartItems.forEach((key, value) -> mapForUpdate.put(key, Long.valueOf(value)));
        return mapForUpdate;
    }

    @Override
    public MiniCart getMiniCart() {
        return new MiniCart(getCartTotalProductsCount(), cart.getSubtotalPrice());
    }

    @Override
    public void clearCart() {
        cart = new Cart();
    }

    private Long getCartTotalProductsCount() {
        return cart.getCartItems().stream().mapToLong(CartItem::getQuantity).sum();
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    public void setCartPricingService(CartPricingServiceImpl cartPricingService) {
        this.cartPricingService = cartPricingService;
    }

    public void setPhoneStockService(PhoneStockService phoneStockService) {
        this.phoneStockService = phoneStockService;
    }
}
