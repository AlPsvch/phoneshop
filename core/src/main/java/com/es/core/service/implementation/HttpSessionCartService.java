package com.es.core.service.implementation;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.MiniCart;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.exceptions.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class HttpSessionCartService implements CartService {

    private static final String MINI_CART = "miniCart";

    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private CartPricingServiceImpl cartPricingService;


    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Phone phoneToAdd = phoneDao.get(phoneId).orElseThrow(PhoneNotFoundException::new);

        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getPhone().getId().equals(phoneId))
                .findFirst();

        boolean cartItemPresents = optionalCartItem.isPresent();

        long currentQuantity = cartItemPresents ? optionalCartItem.get().getQuantity() : 0;
        if (!phoneDao.hasEnoughStock(phoneId, currentQuantity + quantity)) {
            throw new OutOfStockException();
        }

        if (cartItemPresents) {
            CartItem item = optionalCartItem.get();
            item.setQuantity(currentQuantity + quantity);
        } else {
            CartItem item = new CartItem(phoneToAdd, quantity);
            cart.getCartItems().add(item);
        }

        cartPricingService.recalculateTotalPrice(cart);
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            cart.getCartItems().stream().filter(cartItem -> item.getKey().equals(cartItem.getPhone().getId())).findFirst()
                    .ifPresent(cartItem -> cartItem.setQuantity(item.getValue()));
        }
        cartPricingService.recalculateTotalPrice(cart);
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> phoneId.equals(cartItem.getPhone().getId()));
        cartPricingService.recalculateTotalPrice(cart);
    }

    @Override
    public Map<Long, Long> formMapForUpdate(Map<Long, String> cartItems) {
        Map<Long, Long> mapForUpdate = new HashMap<>();
        cartItems.forEach((key, value) -> mapForUpdate.put(key, Long.valueOf(value)));
        return mapForUpdate;
    }

    @Override
    public void insertMiniCart(Model model) {
        MiniCart miniCart = new MiniCart(getCartTotalProductsCount(), cart.getTotalPrice());
        model.addAttribute(MINI_CART, miniCart);
    }

    private Long getCartTotalProductsCount() {
        Long totalCount = 0L;
        for (CartItem cartItem : cart.getCartItems()) {
            totalCount += cartItem.getQuantity();
        }
        return totalCount;
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
}
