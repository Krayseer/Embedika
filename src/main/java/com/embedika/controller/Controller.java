package com.embedika.controller;

import com.embedika.domain.dto.Car;
import com.embedika.domain.dto.Person;
import com.embedika.domain.response.Response;
import com.embedika.service.CarsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("cars")
@Api("Контроллеры справочника автомобилей")
public class Controller {
    private final CarsService carsService;
    @ApiOperation("Получить все автомобили из базы данных, в параметр descending указать по какому столбцу сделать сортировку ")
    @GetMapping("/all")
    public ResponseEntity<Response> allCars(@RequestParam(required = false) String descending){
        log.info("START get endpoint /cars/all");
        ResponseEntity<Response> response = carsService.getAllCars(descending);
        log.info("END endpoint");
        return response;
    }

    @ApiOperation("Получить автомобиль по заданному id")
    @GetMapping("/{id}")
    public ResponseEntity<Response> car(@PathVariable Long id){
        log.info("START get endpoint /cars/id");
        ResponseEntity<Response> response = carsService.getCar(id);
        log.info("END endpoint");
        return response;
    }

    @ApiOperation("Получить статистику по базе данных")
    @GetMapping("/statistics")
    public ResponseEntity<Response> statistics(){
        log.info("START get endpoint /cars/statistics");
        ResponseEntity<Response> response = carsService.getStatistics();
        log.info("END endpoint");
        return response;
    }

    @ApiOperation("Получить всех владельцев машин из базы данных")
    @GetMapping("/person/all")
    public ResponseEntity<Response> getAllPersons(){
        log.info("START get endpoint /cars/person/all");
        ResponseEntity<Response> response = carsService.getAllPersons();
        log.info("END endpoint");
        return response;
    }

    @ApiOperation("Получить все автомобили конкретного человека по id")
    @GetMapping("/person/{id}")
    public ResponseEntity<Response> getPersonCars(@PathVariable Long id){
        log.info("START get endpoint /cars/person/id");
        ResponseEntity<Response> response = carsService.getPersonCars(id);
        log.info("END endpoint");
        return response;
    }

    @ApiOperation("Добавить человека в базу данных")
    @PostMapping("/person/add")
    public ResponseEntity<Response> addPerson(@RequestBody final Person person){
        log.info("START post endpoint /cars/person/add");
        ResponseEntity<Response> response = carsService.insertPerson(person);
        log.info("END endpoint");
        return response;
    }

    @ApiOperation("Добавить автомобиль в базу данных")
    @PostMapping("/add")
    public ResponseEntity<Response> addCar(@RequestBody final Car car){
        log.info("START post endpoint /cars/add");
        ResponseEntity<Response> response = carsService.insertCar(car);
        log.info("END endpoint");
        return response;
    }

    @ApiOperation("Изменить данные о конкретном человеке в базе данных")
    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateCar(@PathVariable Long id,
                                              @RequestBody Car car){
        log.info("START patch endpoint /cars/id");
        ResponseEntity<Response> response = carsService.updateCar(id, car);
        log.info("END endpoint");
        return response;
    }

    @ApiOperation("Удалить автомобиль из базы данных по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCar(@PathVariable Long id){
        log.info("START delete endpoint /cars/id");
        ResponseEntity<Response> response = carsService.deleteCar(id);
        log.info("END endpoint");
        return response;
    }
}
