package com.embedika.service;

import com.embedika.domain.dto.Car;
import com.embedika.domain.dto.Person;
import com.embedika.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface CarsService {
    ResponseEntity<Response> getAllCars(String descendingColumn);
    ResponseEntity<Response> getAllPersons();

    ResponseEntity<Response> insertCar(Car car);

    ResponseEntity<Response> getStatistics();

    ResponseEntity<Response> insertPerson(Person person);

    ResponseEntity<Response> getPersonCars(Long id);

    ResponseEntity<Response> getCar(Long id);

    ResponseEntity<Response> updateCar(Long id, Car car);

    ResponseEntity<Response> deleteCar(Long id);
}
