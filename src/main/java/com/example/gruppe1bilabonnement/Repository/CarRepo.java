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
    //metode der tilføjer bil til databasen
    public void addCar(Car c) {

        String sql = "INSERT INTO car (CarBrand, CarModel, EquipmentLevel, VIN) VALUES (?, ?, ?, ?)";
        template.update(sql, c.getCarBrand(), c.getCarModel(), c.getEquipmentLevel(), c.getVin());

    }
    //metode der viser en liste af alle biler der er klar til at blive lejet ud
    public List<Car> fetchAllAvailableCars() {
        String sql = "SELECT * FROM car WHERE RentalStatus = 'available'";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    public int fetchMileage(int vehicleNumber){
        String sql = "select IFNULL(mileage,0) from car where vehicleNumber = ?";
        return template.queryForObject(sql, Integer.class, vehicleNumber);
    }
    //metode der viser alle biler der er enten er på lager eller er lejet ud
    public List<Car> fetchAllCarsLeasedOrAtStorage() {
        String sql = "SELECT * FROM car WHERE RentalStatus = 'atStorage' OR RentalStatus = 'Leased'";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

}
