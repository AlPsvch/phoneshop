package com.es.core.model.phone;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {

    private static final String GET_PHONE_BY_KEY_QUERY = "SELECT phones.*, colors.id AS colorId, colors.code AS colorCode FROM phones " +
            "LEFT JOIN phone2color ON phones.id = phone2color.phoneId " +
            "LEFT JOIN colors ON colors.id = phone2color.colorId " +
            "WHERE phones.id = ?";

    private static final String UPDATE_PHONE_WITH_ID_QUERY = "UPDATE phones SET brand = ?, model = ?, price = ?, displaySizeInches = ?, " +
            "weightGr = ?, lengthMm = ?, widthMm = ?, heightMm = ?, announced = ?, deviceType = ?, os = ?, displayResolution = ?, " +
            "pixelDensity = ?, displayTechnology = ?, backCameraMegapixels = ?, frontCameraMegapixels = ?, ramGb = ?, " +
            "internalStorageGb = ?, batteryCapacityMah = ?, talkTimeHours = ?, standByTimeHours = ?, bluetooth = ?, " +
            "positioning = ?, imageUrl = ?, description = ? WHERE id = ?";

    private static final String DELETE_MATCHED_COLORS_QUERY = "DELETE FROM phone2color WHERE phoneId = ?";

    private static final String MATCH_COLORS_TO_PHONE_QUERY = "INSERT INTO phone2color (phoneId, colorId) VALUES (?, ?)";

    private static final String GET_ALL_PHONES_WITH_OFFSET_AND_LIMIT_QUERY = "SELECT phones.*, colors.id AS colorId, colors.code AS colorCode FROM phones " +
            "LEFT JOIN phone2color ON phones.id = phone2color.phoneId " +
            "LEFT JOIN colors ON colors.id = phone2color.colorId " +
            "OFFSET ? LIMIT ?";


    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SimpleJdbcInsert phoneJdbcInsert;


    public Optional<Phone> get(final Long key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        List<Phone> phones = jdbcTemplate.query(GET_PHONE_BY_KEY_QUERY, new PhoneResultSetExtractor(), key);

        if (phones.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(phones.get(0));
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
        List<Phone> phones = jdbcTemplate.query(GET_ALL_PHONES_WITH_OFFSET_AND_LIMIT_QUERY, new PhoneResultSetExtractor(), offset, limit);

        return phones;
    }
}
