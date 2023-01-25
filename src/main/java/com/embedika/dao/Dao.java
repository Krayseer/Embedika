package com.embedika.dao;

import com.embedika.domain.dto.Car;
import com.embedika.domain.dto.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Dao {
    List<Car> getAllCars(String descendingColumn);

    List<Car> getPersonCars(Long id);

    Car getCar(Long id);

    List<Person> getPersons();

    void deleteCar(Long id);

    void updateCar(Long id, Car car);

    void insertCar(Car car);

    void insertPerson(Person person);

    Boolean isExistsUsername(String username);

    Boolean isExistsPersonId(Long id);

    Boolean isExistsNumber(String number);

    Boolean isExistsCarId(Long id);
}
