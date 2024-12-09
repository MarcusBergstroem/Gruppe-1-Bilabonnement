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
        System.out.println("DEBUG: Modtaget bil:");
        System.out.println("  CarBrand: " + c.getCarBrand());
        System.out.println("  CarModel: " + c.getCarModel());
        System.out.println("  EquipmentLevel: " + c.getEquipmentLevel());
        System.out.println("  VIN: " + c.getVin());
        // Finder højeste VehicleNumber og sætter fallback til 0, hvis tabellen er tom
        String sql = "SELECT COALESCE(MAX(VehicleNumber), 0) FROM car";
        int highest = template.queryForObject(sql, Integer.class);

        // Indsætter en ny bil i databasen
        sql = "INSERT INTO car (CarBrand, CarModel, EquipmentLevel, VIN) VALUES (?, ?, ?, ?)";
        template.update(sql, c.getCarBrand(), c.getCarModel(), c.getEquipmentLevel(), c.getVin());

    }
}
