package com.embedika.dao.implementation;

import com.embedika.dao.StatisticsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Slf4j
@Repository
@Transactional
public class StatisticsDaoImpl extends JdbcDaoSupport implements StatisticsDao {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;


    public StatisticsDaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public Long countCars() {
        var query = "SELECT COUNT(*) FROM embedika.public.cars";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    @Override
    public Long countPerson() {
        var query = "SELECT COUNT(*) FROM embedika.public.person";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    @Override
    public String firstCarTimeInsert() {
        var query = "SELECT MIN(time_insert) from embedika.public.cars";
        return jdbcTemplate.queryForObject(query, String.class);
    }

    @Override
    public String lastCarTimeInsert() {
        var query = "SELECT MAX(time_insert) from embedika.public.cars";
        return jdbcTemplate.queryForObject(query, String.class);
    }

    @Override
    public String mostPopularBrand() {
        var query = """
                SELECT brand FROM embedika.public.cars
                GROUP BY brand
                HAVING COUNT(brand) = (SELECT MAX(mycount) FROM (
                                       SELECT brand, COUNT(brand) mycount
                                       FROM embedika.public.cars
                                       GROUP BY brand) as bm) LIMIT 1""";
        return jdbcTemplate.queryForObject(query, String.class);
    }

    @Override
    public String mostRareColor() {
        var query = """
                SELECT color FROM embedika.public.cars
                GROUP BY color
                HAVING COUNT(color) = (SELECT MIN(mycount) FROM (
                                       SELECT color, COUNT(color) mycount
                                       FROM embedika.public.cars
                                       GROUP BY color) as bm) LIMIT 1""";
        return jdbcTemplate.queryForObject(query, String.class);
    }

    @Override
    public String averageReleaseYear() {
        var query = "SELECT CAST(AVG(CAST(release AS integer)) AS INTEGER) FROM embedika.public.cars";
        return jdbcTemplate.queryForObject(query, String.class);
    }
}
