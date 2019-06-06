package com.es.core.model.phone;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PhoneResultSetExtractor implements ResultSetExtractor<List<Phone>> {

    @Override
    public List<Phone> extractData(ResultSet resultSet) throws SQLException {

        Map<Long, Phone> phoneMap = new HashMap<>();
        List<Phone> phones = new ArrayList<>();

        while (resultSet.next()) {

            Long phoneId = resultSet.getLong("id");
            Phone phone = phoneMap.get(phoneId);

            if (phone == null) {
                phone = processPhoneData(resultSet);
                phoneMap.put(phoneId, phone);
                phones.add(phone);
            }

            addColor(phone, resultSet);
        }

        return phones;
    }

    private Phone processPhoneData(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone();

        phone.setId(resultSet.getLong("id"));
        phone.setBrand(resultSet.getString("brand"));
        phone.setModel(resultSet.getString("model"));
        phone.setPrice(resultSet.getBigDecimal("price"));
        phone.setDisplaySizeInches(resultSet.getBigDecimal("displaySizeInches"));
        phone.setWeightGr(resultSet.getInt("weightGr"));
        phone.setLengthMm(resultSet.getBigDecimal("lengthMm"));
        phone.setWidthMm(resultSet.getBigDecimal("widthMm"));
        phone.setHeightMm(resultSet.getBigDecimal("heightMm"));
        phone.setAnnounced(resultSet.getDate("announced"));
        phone.setDeviceType(resultSet.getString("deviceType"));
        phone.setOs(resultSet.getString("os"));
        phone.setDisplayResolution(resultSet.getString("displayResolution"));
        phone.setPixelDensity(resultSet.getInt("pixelDensity"));
        phone.setDisplayTechnology(resultSet.getString("displayTechnology"));
        phone.setBackCameraMegapixels(resultSet.getBigDecimal("backCameraMegapixels"));
        phone.setFrontCameraMegapixels(resultSet.getBigDecimal("frontCameraMegapixels"));
        phone.setRamGb(resultSet.getBigDecimal("ramGb"));
        phone.setInternalStorageGb(resultSet.getBigDecimal("internalStorageGb"));
        phone.setBatteryCapacityMah(resultSet.getInt("batteryCapacityMah"));
        phone.setTalkTimeHours(resultSet.getBigDecimal("talkTimeHours"));
        phone.setStandByTimeHours(resultSet.getBigDecimal("standByTimeHours"));
        phone.setBluetooth(resultSet.getString("bluetooth"));
        phone.setPositioning(resultSet.getString("positioning"));
        phone.setImageUrl(resultSet.getString("imageUrl"));
        phone.setDescription(resultSet.getString("description"));
        phone.setColors(new HashSet<>());

        return phone;
    }

    private void addColor(Phone phone, ResultSet resultSet) throws SQLException {
        Long colorId = resultSet.getLong("colorId");

        if (colorId > 0) {

            Color color = new Color();
            color.setId(colorId);
            color.setCode(resultSet.getString("colorCode"));

            phone.getColors().add(color);

        }
    }
}
