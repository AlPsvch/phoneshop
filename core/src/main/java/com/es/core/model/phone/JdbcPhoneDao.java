package com.es.core.model.phone;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Component
@Transactional
public class JdbcPhoneDao implements PhoneDao {

    private static final String GET_PHONE_BY_KEY_QUERY = "SELECT * FROM phones WHERE id = ?";

    private static final String GET_COLORS_BY_PHONE_KEY_QUERY = "SELECT * FROM colors " +
            "INNER JOIN phone2color ON (colors.id = phone2color.colorId AND phone2color.phoneId = ?)";

    private static final String UPDATE_PHONE_WITH_ID_QUERY = "UPDATE phones SET brand = ?, model = ?, price = ?, displaySizeInches = ?, " +
            "weightGr = ?, lengthMm = ?, widthMm = ?, heightMm = ?, announced = ?, deviceType = ?, os = ?, displayResolution = ?, " +
            "pixelDensity = ?, displayTechnology = ?, backCameraMegapixels = ?, frontCameraMegapixels = ?, ramGb = ?, " +
            "internalStorageGb = ?, batteryCapacityMah = ?, talkTimeHours = ?, standByTimeHours = ?, bluetooth = ?, " +
            "positioning = ?, imageUrl = ?, description = ? WHERE id = ?";

    private static final String DELETE_MATCHED_COLORS_QUERY = "DELETE FROM phone2color WHERE phoneId = ?";

    private static final String MATCH_COLORS_TO_PHONE_QUERY = "INSERT INTO phone2color (phoneId, colorId) VALUES (?, ?)";

    private static final String GET_ALL_PHONES_WITH_OFFSET_AND_LIMIT_QUERY = "SELECT * FROM phones OFFSET ? LIMIT ?";


    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SimpleJdbcInsert phoneJdbcInsert;

    @Resource
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper;

    @Resource
    private BeanPropertyRowMapper<Color> colorBeanPropertyRowMapper;


    public Optional<Phone> get(final Long key) {
        Objects.requireNonNull(key, "Key must not be null");

        Phone phone;

        try {
            phone = jdbcTemplate.queryForObject(GET_PHONE_BY_KEY_QUERY, phoneBeanPropertyRowMapper, key);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        phone.setColors(extractColors(key));

        return Optional.of(phone);
    }


    private Set<Color> extractColors(final Long key) {
        List<Color> colorList = jdbcTemplate.query(GET_COLORS_BY_PHONE_KEY_QUERY, colorBeanPropertyRowMapper, key);

        return new HashSet<>(colorList);
    }


    public void save(final Phone phone) {
        if (phone.getId() == null) {
            addPhone(phone);
        } else {
            updatePhone(phone);
        }

        matchColors(phone);
    }


    private void addPhone(final Phone phone) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(phone);

        Number id = phoneJdbcInsert.executeAndReturnKey(parameterSource);
        phone.setId(id.longValue());
    }


    private void updatePhone(final Phone phone) {
        jdbcTemplate.update(UPDATE_PHONE_WITH_ID_QUERY, phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(),
                phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(),
                phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(),
                phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(),
                phone.getPositioning(), phone.getImageUrl(), phone.getDescription(), phone.getId());

        jdbcTemplate.update(DELETE_MATCHED_COLORS_QUERY, phone.getId());
    }


    private void matchColors(Phone phone) {
        Long phoneId = phone.getId();

        List<Color> colors = new ArrayList<>(phone.getColors());

        jdbcTemplate.batchUpdate(MATCH_COLORS_TO_PHONE_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Color color = colors.get(i);

                preparedStatement.setLong(1, phoneId);
                preparedStatement.setLong(2, color.getId());
            }

            @Override
            public int getBatchSize() {
                return colors.size();
            }
        });
    }


    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = jdbcTemplate.query(GET_ALL_PHONES_WITH_OFFSET_AND_LIMIT_QUERY,
                phoneBeanPropertyRowMapper, offset, limit);

        phones.forEach(phone -> phone.setColors(extractColors(phone.getId())));

        return phones;
    }
}
