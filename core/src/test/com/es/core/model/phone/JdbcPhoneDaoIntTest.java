package com.es.core.model.phone;

import com.es.core.IntegrationTest;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;


public class JdbcPhoneDaoIntTest extends IntegrationTest {

    private static final String PHONE_BRAND_1 = "Samsung";

    private static final String PHONE_MODEL_1 = "Galaxy S7";

    private static final String PHONE_BRAND_2 = "Apple";

    private static final String PHONE_MODEL_2 = "Iphone 6";

    private static final Long NON_EXISTENT_PHONE_ID = 1000L;

    private static final Integer NUMBER_OF_PHONES_IN_TEST_BASE = 2;


    @Resource
    private PhoneDao phoneDao;

    private Phone phone1;

    private Phone phone2;

    private Set<Color> colorSet = new HashSet<>();


    @Before
    public void setup() {
        phone1 = new Phone();
        phone1.setBrand(PHONE_BRAND_1);
        phone1.setModel(PHONE_MODEL_1);
        phone1.setPrice(BigDecimal.TEN);

        phone2 = new Phone();
        phone2.setBrand(PHONE_BRAND_2);
        phone2.setModel(PHONE_MODEL_2);
        phone2.setPrice(BigDecimal.TEN);

        Color black = new Color(1L, "Black");
        Color white = new Color(2L, "White");
        Color yellow = new Color(3L, "Yellow");
        Color red = new Color(4L, "Red");

        colorSet.add(yellow);
        colorSet.add(black);
    }


    @Test
    public void testSave() {
        phoneDao.save(phone2);

        long phoneId = phone2.getId();

        Optional<Phone> returnedPhoneOptional = phoneDao.get(phoneId);

        assertTrue(returnedPhoneOptional.isPresent());

        Phone returnedPhone = returnedPhoneOptional.get();

        assertEquals(phone2.getBrand(), returnedPhone.getBrand());
        assertEquals(phone2.getModel(), returnedPhone.getModel());
    }


    @Test
    public void testGet() {
        phoneDao.save(phone1);

        long phoneId = phone1.getId();

        Optional<Phone> returnedPhoneOptional = phoneDao.get(phoneId);

        assertTrue(returnedPhoneOptional.isPresent());

        Phone returnedPhone = returnedPhoneOptional.get();

        assertEquals(phone1.getBrand(), returnedPhone.getBrand());
        assertEquals(phone1.getModel(), returnedPhone.getModel());
    }


    @Test(expected = NullPointerException.class)
    public void testGetWithNullKey() {
        Long key = null;

        phoneDao.get(key);
    }


    @Test
    public void testFindAll() {
        List<Phone> phones = phoneDao.findAll(0, 10);
        assertEquals(2, phones.size());
    }


    @Test
    public void testGetNonExist() {
        Optional<Phone> phoneOptional = phoneDao.get(NON_EXISTENT_PHONE_ID);

        assertFalse(phoneOptional.isPresent());
    }


    @Test
    public void testSaveWithColors() {
        phone1.setColors(colorSet);

        phoneDao.save(phone1);

        long phoneId = phone1.getId();

        Optional<Phone> returnedPhoneOptional = phoneDao.get(phoneId);

        assertTrue(returnedPhoneOptional.isPresent());

        Phone returnedPhone = returnedPhoneOptional.get();

        Set<Color> returnedPhoneColors = returnedPhone.getColors();
        assertEquals(colorSet.size(), returnedPhoneColors.size());
    }


    @Test
    public void testGetWithColors() {
        phone1.setColors(colorSet);

        phoneDao.save(phone1);
        long phoneId = phone1.getId();

        Optional<Phone> returnedPhoneOptional = phoneDao.get(phoneId);
        assertTrue(returnedPhoneOptional.isPresent());
        Phone returnedPhone = returnedPhoneOptional.get();

        assertEquals(phone1.getId(), returnedPhone.getId());
        assertEquals(phone1.getColors().size(), returnedPhone.getColors().size());
    }

    @Test
    public void testUpdate() {
        phone1.setColors(colorSet);

        phoneDao.save(phone1);
        long phoneId = phone1.getId();

        Optional<Phone> returnedPhoneOptional = phoneDao.get(phoneId);
        assertTrue(returnedPhoneOptional.isPresent());
        Phone returnedPhone = returnedPhoneOptional.get();

        assertEquals(phone1.getId(), returnedPhone.getId());
        assertEquals(phone1.getColors().size(), returnedPhone.getColors().size());

        phone1.setColors(new HashSet<>());
        phone1.setBrand(PHONE_BRAND_2);

        phoneDao.save(phone1);

        Optional<Phone> updatedPhoneOptional = phoneDao.get(phoneId);
        assertTrue(updatedPhoneOptional.isPresent());
        Phone updatedPhone = updatedPhoneOptional.get();

        assertEquals(phone1.getId(), updatedPhone.getId());
        assertEquals(phone1.getBrand(), updatedPhone.getBrand());
        assertEquals(phone1.getColors().size(), updatedPhone.getColors().size());
    }
}
