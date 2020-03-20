package com.es.core.service.implementation;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.exceptions.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class HttpSessionCartService implements CartService {

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

        if (optionalCartItem.isPresent()) {
            CartItem item = optionalCartItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem(phoneToAdd, quantity);
            cart.getCartItems().add(item);
        }

        cartPricingService.recalculateTotalPrice(cart);
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }
}
