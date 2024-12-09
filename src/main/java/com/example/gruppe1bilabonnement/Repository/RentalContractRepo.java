package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Model.Renter;
import org.springframework.dao.EmptyResultDataAccessException;
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
        //Vi bruger try catch, for at siden ikke skal vise fejl, hvis registreringsnummeret ikke finde i databasen
        try {
            String sql = "SELECT * FROM rentalcontract WHERE RegistrationNumber = ?";
            RowMapper<RentalContract> rowMapper1 = new BeanPropertyRowMapper<>(RentalContract.class);
            RentalContract rentalTmp = template.queryForObject(sql, rowMapper1, regNumber);

            String sqlCar = "SELECT * FROM car WHERE VehicleNumber = ?";
            RowMapper<Car> rowMapper2 = new BeanPropertyRowMapper<>(Car.class);
            Car carTmp = template.queryForObject(sqlCar, rowMapper2, rentalTmp.getCarVehicleNumber());
            rentalTmp.setRentalCar(carTmp);

            String sqlRenter = "SELECT * FROM renter WHERE id = ?";
            RowMapper<Renter> rowMapper3 = new BeanPropertyRowMapper<>(Renter.class);
            Renter renterTmp = template.queryForObject(sqlRenter, rowMapper3, rentalTmp.getRenterID());
            rentalTmp.setRentalRenter(renterTmp);

            // Laver et rentalContract objekt som indeholder alle detaljer for reg nr.

            return rentalTmp;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<RentalContract> fetchAll() {
        String sql = "select * from rentalcontract";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        List<RentalContract> rentalContractList = template.query(sql, rowMapper);

        String sqlCar = "select * from car";
        RowMapper<Car> rowMapper2 = new BeanPropertyRowMapper<>(Car.class);
        List<Car> carTmp = template.query(sqlCar, rowMapper2);

        for (RentalContract rentalContract : rentalContractList) {
            for (Car car : carTmp) {
                if (car.getVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setRentalCar(car);
                }
            }
        }
        return rentalContractList;
    }

}
