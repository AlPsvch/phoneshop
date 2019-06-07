package com.es.core.model.phone;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:resources/context/applicationContext-core-test.xml")
public class JdbcPhoneDaoIntTest {

    private static final String PHONE_BRAND_1 = "Samsung";

    private static final String PHONE_MODEL_1 = "Galaxy S7";

    private static final String PHONE_BRAND_2 = "Apple";

    private static final String PHONE_MODEL_2 = "Iphone 6";

    private static final Long NON_EXISTENT_PHONE_ID = 1000L;


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

        phone2 = new Phone();
        phone2.setBrand(PHONE_BRAND_2);
        phone2.setModel(PHONE_MODEL_2);

        Color black = new Color(1L, "Black");
        Color white = new Color(2L, "White");
        Color yellow = new Color(3L, "Yellow");
        Color red = new Color(4L, "Red");

        colorSet.add(yellow);
        colorSet.add(black);
    }


    @Test
    @Transactional
    public void testSave() {
        phoneDao.save(phone1);

        List<Phone> phones = phoneDao.findAll(0, 10);

        assertEquals(phone1.getBrand(), phones.get(0).getBrand());
        assertEquals(phone1.getModel(), phones.get(0).getModel());
    }


    @Test
    @Transactional
    public void testGet() {
        phoneDao.save(phone1);

        long phoneId = phone1.getId();

        Optional<Phone> returnedPhoneOptional = phoneDao.get(phoneId);

        assertTrue(returnedPhoneOptional.isPresent());

        Phone returnedPhone = returnedPhoneOptional.get();

        assertEquals(phone1.getBrand(), returnedPhone.getBrand());
        assertEquals(phone1.getModel(), returnedPhone.getModel());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetWithNullKey() {
        Long key = null;

        phoneDao.get(key);
    }


    @Test
    @Transactional
    public void testFindAll() {
        phoneDao.save(phone1);

        List<Phone> phones = phoneDao.findAll(0, 10);
        assertEquals(1, phones.size());

        phoneDao.save(phone2);

        phones = phoneDao.findAll(0, 10);
        assertEquals(2, phones.size());

        phones = phoneDao.findAll(0, 1);
        assertEquals(1, phones.size());
    }


    @Test
    public void testGetNonExist() {
        Optional<Phone> phoneOptional = phoneDao.get(NON_EXISTENT_PHONE_ID);

        assertFalse(phoneOptional.isPresent());
    }


    @Test
    @Transactional
    public void testSaveWithColors() {
        phone1.setColors(colorSet);

        phoneDao.save(phone1);

        List<Phone> phones = phoneDao.findAll(0, 10);

        assertEquals(1, phones.size());

        Set<Color> returnedPhoneColors = phones.get(0).getColors();
        assertEquals(colorSet.size(), returnedPhoneColors.size());
    }


    @Test
    @Transactional
    public void testGetWithColors() {
        phone1.setColors(colorSet);

        phoneDao.save(phone1);
        long phoneId = phone1.getId();

        Optional<Phone> returnedPhoneOptional = phoneDao.get(phoneId);
        assertTrue(returnedPhoneOptional.isPresent());
        Phone returnedPhone = returnedPhoneOptional.get();

        assertEquals(colorSet.size(), returnedPhone.getColors().size());
    }
}