package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RentalContractRepo {

    private final JdbcTemplate template;

    public RentalContractRepo (JdbcTemplate template) {
        this.template = template;
    }

    public RentalContract fetchRentalContractDetails(String regNumber){
        String sql = "SELECT * FROM rentalcontract WHERE RegistrationNumber = ?";

        String sql = "SELECT * FROM car WHERE "

        // Laver et rentalContract objekt som indeholder alle detaljer for reg nr.
        RowMapper<RentalContract> rowMapper1 = new BeanPropertyRowMapper<>(RentalContract.class);

        return template.queryForObject(sql, rowMapper1, regNumber);

    }

    public List<Car> fetchAllWithReturnDate() {
        String sql = "SELECT rentalcontract.ReturnDate, car.*  FROM rentalcontract left join car on car.VehicleNumber = rentalcontract.CarVehicleNumber where ReturnDate is not null";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

}
