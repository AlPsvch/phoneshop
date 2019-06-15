package com.es.core.model.phone;

import com.es.core.IntegrationTest;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
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
    public void testSave() {
        phoneDao.save(phone1);

        List<Phone> phones = phoneDao.findAll(0, 10);

        assertEquals(phone1.getBrand(), phones.get(0).getBrand());
        assertEquals(phone1.getModel(), phones.get(0).getModel());
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
    public void testSaveWithColors() {
        phone1.setColors(colorSet);

        phoneDao.save(phone1);

        List<Phone> phones = phoneDao.findAll(0, 10);

        assertEquals(1, phones.size());

        Set<Color> returnedPhoneColors = phones.get(0).getColors();
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
