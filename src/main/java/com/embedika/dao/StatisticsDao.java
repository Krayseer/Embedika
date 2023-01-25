package com.embedika.dao;

import org.springframework.stereotype.Service;

@Service
public interface StatisticsDao {
    Long countCars();

    Long countPerson();

    String firstCarTimeInsert();

    String lastCarTimeInsert();

    String mostPopularBrand();

    String mostRareColor();

    String averageReleaseYear();
}
