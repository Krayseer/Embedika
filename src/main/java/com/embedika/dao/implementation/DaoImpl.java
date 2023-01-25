package com.embedika.dao.implementation;

import com.embedika.dao.Dao;
import com.embedika.domain.dto.Car;
import com.embedika.domain.dto.Person;
import com.embedika.domain.mapper.CarMapper;
import com.embedika.domain.mapper.PersonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class DaoImpl extends JdbcDaoSupport implements Dao {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;


    public DaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public List<Car> getAllCars(String descending) {
        var query = "SELECT * FROM embedika.public.cars ORDER BY ?";
        return jdbcTemplate.query(query, new CarMapper(), descending);
    }

    @Override
    public List<Car> getPersonCars(Long id) {
        var query = "SELECT cars.id, number, person_id, brand, color, release, time_insert FROM embedika.public.cars " +
                    "JOIN embedika.public.person p ON cars.person_id = p.id WHERE p.id = ?";
        return jdbcTemplate.query(query, new CarMapper(), id);
    }

    @Override
    public Car getCar(Long id) {
        var query = "SELECT * FROM embedika.public.cars WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new CarMapper(), id);
    }

    @Override
    public List<Person> getPersons() {
        var query = "SELECT * FROM embedika.public.person";
        return jdbcTemplate.query(query,new PersonMapper());
    }

    @Override
    public void deleteCar(Long id) {
        jdbcTemplate.update("DELETE FROM embedika.public.cars WHERE id = ?", id);
    }

    @Override
    public void updateCar(Long id, Car updatedCar) {
        jdbcTemplate.update("UPDATE embedika.public.cars SET id=default, number=?, person_id=?, brand=?, color=?, release=? WHERE id=?",
                updatedCar.getNumber(), updatedCar.getPerson_id(), updatedCar.getBrand(),
                updatedCar.getColor(), updatedCar.getRelease(), id);
    }

    @Override
    public void insertCar(Car car) {
        jdbcTemplate.update("INSERT INTO embedika.public.cars(id, number, person_id, brand, color, release) VALUES " +
                "(default, ?, ?, ?, ?, ?)", car.getNumber(), car.getPerson_id(), car.getBrand(), car.getColor(), car.getRelease());
    }

    @Override
    public void insertPerson(Person person) {
        jdbcTemplate.update("INSERT INTO embedika.public.person(id, username) VALUES " +
                "(default, ?)", person.getUsername());
    }

    @Override
    public Boolean isExistsUsername(String username) {
        String query = "SELECT EXISTS (SELECT * FROM embedika.public.person WHERE username = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, username));
    }

    @Override
    public Boolean isExistsPersonId(Long id) {
        String query = "SELECT EXISTS (SELECT * FROM embedika.public.person WHERE id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, id));
    }

    @Override
    public Boolean isExistsNumber(String number) {
        String query = "SELECT EXISTS (SELECT * FROM embedika.public.cars WHERE number = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, number));
    }

    @Override
    public Boolean isExistsCarId(Long id) {
        String query = "SELECT EXISTS (SELECT * FROM embedika.public.cars WHERE id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, id));
    }
}
