package com.embedika.controller;

import com.embedika.dao.Dao;
import com.embedika.domain.dto.Car;
import com.embedika.domain.dto.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphController {
    private final Dao dao;

    @QueryMapping
    public List<Car> cars(){
        return dao.getAllCars("");
    }

    @QueryMapping
    public List<Person> persons(){
        return dao.getPersons();
    }

    @QueryMapping
    public Car car(@Argument Long id){
        return dao.getCar(id);
    }

    @QueryMapping
    public List<Car> personCar(@Argument Long id){
        return dao.getPersonCars(id);
    }

    @MutationMapping
    public Car addCar(@Argument CarInput carInput){
        Car car = Car
                .builder()
                .number(carInput.number())
                .person_id(carInput.personId())
                .brand(carInput.brand())
                .color(carInput.color())
                .release(carInput.release())
                .build();
        dao.insertCar(car);
        return car;
    }

    record CarInput(String number, Long personId, String brand, String color, String release) {}
}
