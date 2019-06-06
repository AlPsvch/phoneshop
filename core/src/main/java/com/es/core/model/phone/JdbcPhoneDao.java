package com.es.core.model.phone;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
            "INNER JOIN phone2color ON phones.id = phone2color.phoneId " +
            "INNER JOIN colors ON colors.id = phone2color.colorId " +
            "WHERE phones.id = ?";

    private static final String MATCH_COLORS_TO_PHONE_QUERY = "INSERT INTO phone2color (phoneId, colorId) VALUES (?, ?)";


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
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(phone);

        Number id = phoneJdbcInsert.executeAndReturnKey(parameterSource);
        phone.setId(id.longValue());

        matchColors(phone);
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
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper<>(Phone.class));
    }
}
