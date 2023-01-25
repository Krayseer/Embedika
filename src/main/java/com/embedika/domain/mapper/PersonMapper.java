package com.embedika.domain.mapper;

import com.embedika.domain.dto.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet row, int rowNum) throws SQLException {
        return Person
                .builder()
                .id(row.getLong("id"))
                .username(row.getString("username"))
                .build();
    }
}
