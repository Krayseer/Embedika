package com.embedika.service.implementation;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.embedika.dao.Dao;
import com.embedika.dao.StatisticsDao;
import com.embedika.domain.constant.Code;
import com.embedika.domain.dto.Car;
import com.embedika.domain.dto.Person;
import com.embedika.domain.response.Response;
import com.embedika.domain.response.SuccessResponse;
import com.embedika.domain.response.exception.ServiceException;
import com.embedika.service.CarsService;
import com.embedika.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "cars")
public class CarsServiceImpl implements CarsService {
    private final Dao dao;
    private final StatisticsDao statisticsDao;
    private final ValidationUtils validationUtils;

    @Override
    public ResponseEntity<Response> getAllCars(String descending) {
        log.info("Get all cars from database");
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .data(dao.getAllCars(descending))
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getAllPersons() {
        log.info("Get all persons from database");
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .data(dao.getPersons())
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getStatistics() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Количество машин в базе данных", statisticsDao.countCars());
        result.put("Количество владельцев машин в базе данных", statisticsDao.countPerson());
        result.put("Время публикации первого автомобиля", statisticsDao.firstCarTimeInsert());
        result.put("Время публикации последнего автомобиля", statisticsDao.lastCarTimeInsert());
        result.put("Самая популярная марка автомобиля", statisticsDao.mostPopularBrand());
        result.put("Самый редкий цвет автомобиля", statisticsDao.mostRareColor());
        result.put("Средний год выпуска автомобилей", statisticsDao.averageReleaseYear());

        log.info("Get statistics about cars");
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .data(result)
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> insertCar(Car car) {
        validationUtils.validation(car);

        if(!dao.isExistsPersonId(car.getPerson_id()))
            throw ServiceException
                    .builder()
                    .code(Code.NOT_EXISTS_PERSON_ID)
                    .message("Человека с таким id не существует")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();

        if(dao.isExistsNumber(car.getNumber()))
            throw ServiceException
                    .builder()
                    .code(Code.IS_EXISTS_NUMBER)
                    .message("Такой номер уже существует в базе данных")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();

        dao.insertCar(car);
        log.info("Add new car in database: " + car);
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .data("Автомобиль успешно добавлен!")
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> insertPerson(Person person) {
        validationUtils.validation(person);

        if(dao.isExistsUsername(person.getUsername()))
            throw ServiceException
                    .builder()
                    .code(Code.IS_EXISTS_USERNAME)
                    .message("Человек с таким username уже существует")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();

        dao.insertPerson(person);
        log.info("Add new person in database: " + person);
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .data("Человек успешно добавлен!")
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getPersonCars(Long id) {
        if (!dao.isExistsPersonId(id))
            throw ServiceException
                    .builder()
                    .code(Code.NOT_EXISTS_PERSON_ID)
                    .message("Человек с таким id не существует")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();

        log.info("Get all cars from person with id: " + id);
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .data(dao.getPersonCars(id))
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getCar(Long id) {
        if (!dao.isExistsCarId(id))
            throw ServiceException
                    .builder()
                    .code(Code.NOT_EXISTS_CAR_ID)
                    .message("Автомобиля с таким id не существует")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();

        log.info("Get info about car with id: " + id);
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .data(dao.getCar(id))
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> updateCar(Long id, Car car) {
        if (!dao.isExistsCarId(id))
            throw ServiceException
                    .builder()
                    .code(Code.NOT_EXISTS_CAR_ID)
                    .message("Автомобиля с таким id не существует")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();

        dao.updateCar(id, car);
        log.info("Update car with id " + id + " on car: " + car);
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .data("Обновил данные о машине" )
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> deleteCar(Long id) {
        if (!dao.isExistsCarId(id))
            throw ServiceException
                    .builder()
                    .code(Code.NOT_EXISTS_CAR_ID)
                    .message("Автомобиля с таким id не существует")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();

        dao.deleteCar(id);
        log.info("Delete car success");
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .data("Удалил автомобиль с идентификатором: " + id)
                .build(), HttpStatus.OK);
    }
}
