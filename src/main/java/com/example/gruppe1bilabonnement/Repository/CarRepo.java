package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Car;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepo {

    private final JdbcTemplate template;

    public CarRepo(JdbcTemplate template) {
        this.template = template;
    }
    public void addCar(Car c) {

        String sql = "INSERT INTO car (CarBrand, CarModel, EquipmentLevel, VIN) VALUES (?, ?, ?, ?)";
        template.update(sql, c.getCarBrand(), c.getCarModel(), c.getEquipmentLevel(), c.getVin());

    }
}
