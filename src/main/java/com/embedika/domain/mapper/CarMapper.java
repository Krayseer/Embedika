package com.embedika.domain.mapper;

import com.embedika.domain.dto.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet row, int rowNum) throws SQLException {
        return Car
                .builder()
                .id(row.getLong("id"))
                .number(row.getString("number"))
                .person_id(row.getLong("person_id"))
                .brand(row.getString("brand"))
                .color(row.getString("color"))
                .release(row.getString("release"))
                .build();
    }
}
