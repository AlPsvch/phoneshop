package com.es.core.service;

import com.es.core.IntegrationTest;
import com.es.core.cart.Cart;
import com.es.core.model.phone.JdbcPhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.service.implementation.CartPricingServiceImpl;
import com.es.core.service.implementation.HttpSessionCartService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpSessionCartServiceTest extends IntegrationTest {

    private static final Long PHONE_1_ID = 1L;
    private static final Long PHONE_2_ID = 2L;
    private static final Long PHONE_1_QUANTITY = 10L;
    private static final Long PHONE_2_QUANTITY = 5L;
    private static final BigDecimal EXPECTED_TOTAL_PRICE = new BigDecimal(150);

    private Cart cart;

    @Before
    public void init() {
        cart = new Cart();
        HttpSessionCartService cartService = new HttpSessionCartService();
        CartPricingServiceImpl cartPricingService = new CartPricingServiceImpl();
        PhoneDao phoneDao = mock(JdbcPhoneDao.class);

        cartService.setCart(cart);
        cartService.setCartPricingService(cartPricingService);
        cartService.setPhoneDao(phoneDao);

        Phone phone1 = new Phone();
        Phone phone2 = new Phone();

        phone1.setId(1L);
        phone2.setId(2L);

        phone1.setPrice(BigDecimal.TEN);
        phone2.setPrice(BigDecimal.TEN);

        when(phoneDao.get(1L)).thenReturn(Optional.of(phone1));
        when(phoneDao.get(2L)).thenReturn(Optional.of(phone2));
        when(phoneDao.hasEnoughStock(any(), any())).thenReturn(Boolean.TRUE);

        cartService.addPhone(PHONE_1_ID, PHONE_1_QUANTITY);
        cartService.addPhone(PHONE_2_ID, PHONE_2_QUANTITY);
    }

    @Test
    public void testCalculatingTotalPrice() {
        assertEquals(EXPECTED_TOTAL_PRICE, cart.getTotalPrice());
    }
}
