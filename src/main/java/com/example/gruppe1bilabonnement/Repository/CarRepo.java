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

    public List<Car> fetchAllWithReturnDate() {
        String sql = "SELECT rentalcontract.ReturnDate, car.*  FROM rentalcontract left join car on car.VehicleNumber = rentalcontract.CarVehicleNumber where ReturnDate is not null";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    public List<Car> fetchAllWithReturnDate(String regNumber) {
        String sql = "SELECT rentalcontract.ReturnDate, car.*  FROM rentalcontract left join car on car.VehicleNumber = rentalcontract.CarVehicleNumber where (ReturnDate is not null and car.RegistrationNumber = ?)";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper, regNumber);
    }
}
