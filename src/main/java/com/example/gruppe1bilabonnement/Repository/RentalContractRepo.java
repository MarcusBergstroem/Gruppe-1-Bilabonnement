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
import java.util.Map;

@Repository
public class RentalContractRepo {

    private final JdbcTemplate template;

    public RentalContractRepo (JdbcTemplate template) {
        this.template = template;
    }
    public List<Map<String, Object>> fetchAllLocations() {
        String sql = "SELECT ID, LocationName FROM delivery_return_location";
        return template.queryForList(sql);
    }

    public RentalContract fetchRentalContractDetails(String regNumber) {
        // SQL-forespørgsel for at hente oplysninger om lejekontrakten
        String sql = """
            SELECT 
                rc.*, 
                drl.LocationName AS deliveryLocationName, 
                drl.LocationAddress AS deliveryLocationAddress, 
                drl.LocationZipcode AS deliveryLocationZipcode, 
                drl.LocationCity AS deliveryLocationCity, 
                drl.LocationCountry AS deliveryLocationCountry,
                drl2.LocationName AS returnLocationName, 
                drl2.LocationAddress AS returnLocationAddress, 
                drl2.LocationZipcode AS returnLocationZipcode, 
                drl2.LocationCity AS returnLocationCity, 
                drl2.LocationCountry AS returnLocationCountry
            FROM 
                rentalcontract rc
            INNER JOIN 
                delivery_return_location drl ON rc.DeliveryLocationID = drl.id
            INNER JOIN 
                delivery_return_location drl2 ON rc.ReturnLocationID = drl2.id
            WHERE 
                rc.RegistrationNumber = ?;
            """;

        // Brug `query` i stedet for `queryForObject` for at håndtere flere resultater
        RowMapper<RentalContract> rowMapper1 = new BeanPropertyRowMapper<>(RentalContract.class);
        List<RentalContract> rentalContracts = template.query(sql, rowMapper1, regNumber);

        // Hvis der ikke er nogen resultater, returner null
        if (rentalContracts.isEmpty()) {
            return null;
        }

        // Brug den første lejekontrakt i listen
        RentalContract rentalTmp = rentalContracts.get(0);

        // Hent biloplysninger
        String sqlCar = "SELECT * FROM car WHERE VehicleNumber = ?";
        RowMapper<Car> rowMapper2 = new BeanPropertyRowMapper<>(Car.class);
        Car carTmp = template.queryForObject(sqlCar, rowMapper2, rentalTmp.getCarVehicleNumber());
        rentalTmp.setRentalCar(carTmp);

        // Hent lejers oplysninger
        String sqlRenter = """
            SELECT 
                rent.*, 
                geo.Zipcode AS zipCode, 
                geo.City AS city, 
                geo.Country AS country
            FROM 
                renter rent
            INNER JOIN 
                geography geo ON rent.GeographyID = geo.id
            WHERE 
                rent.id = ?;
            """;

        RowMapper<Renter> rowMapper3 = new BeanPropertyRowMapper<>(Renter.class);
        Renter renterTmp = template.queryForObject(sqlRenter, rowMapper3, rentalTmp.getRenterID());
        rentalTmp.setRentalRenter(renterTmp);

        return rentalTmp;
    }


    public List<RentalContract> searchRentalContracts(String regNumber){
        String sql = "select * from rentalcontract where registrationNumber like concat('%', ?, '%')";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        List<RentalContract> rentalContractList = template.query(sql, rowMapper, regNumber);

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

    public List<RentalContract> fetchAll() {
        String sql = "select * from rentalcontract ORDER BY returnDate";
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
    public void addRentalContract (RentalContract rentalContract){
        String sql = "INSERT INTO rentalcontract (RenterID, CarVehicleNumber, DeliveryDate, ReturnDate, InitialPayment, " +
                    "MonthlyPayment, TotalKilometers, AdditionalKM, CustomChoices, IsSigned, RegistrationNumber, " +
                    "DeliveryLocationID, ReturnLocationID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        template.update(sql,
              rentalContract.getRenterID(),
              rentalContract.getCarVehicleNumber(),
              rentalContract.getDeliveryDate(),
              rentalContract.getReturnDate(),
              rentalContract.getInitialPayment(),
              rentalContract.getMonthlyPayment(),
              rentalContract.getTotalKilometers(),
              rentalContract.getAdditionalKM(),
              rentalContract.getCustomChoices(),
              rentalContract.isSigned(),
              rentalContract.getRegistrationNumber(),
              rentalContract.getDeliveryLocationId(),
              rentalContract.getReturnLocationId()
        );
        String updateCarStatusSql = "UPDATE car SET RentalStatus = ? WHERE VehicleNumber = ?";
        template.update(updateCarStatusSql, "Leased", rentalContract.getCarVehicleNumber());
    }





    public RentalContract findByRegistrationNumber(String regNumber) {
        String sql = "SELECT * FROM RentalContract WHERE registrationNumber = ?";
        List<RentalContract> contracts = template.query(sql, new BeanPropertyRowMapper<>(RentalContract.class), regNumber);
        if (contracts.isEmpty()) {
            return null;
        }
        return contracts.get(0); // Vælger den første række, hvis der er flere
    }

    public int getDamageReportID(int carVehicleNumber) {
        String sql = "SELECT ID FROM damagereport where CarVehicleNumber = ?";
        return template.queryForObject(sql, Integer.class, carVehicleNumber);
    }

    public Double getTotalDamagePrice(int carVehicleNumber) {
        // Brug carVehicleNumber til at finde totalDamagePrice
        try {
            String sql = "SELECT SUM(Price) FROM damage WHERE DamageReportID = ?";
            Double totalDamagePrice = template.queryForObject(sql, Double.class, getDamageReportID(carVehicleNumber));
            return totalDamagePrice != null ? totalDamagePrice : 0.0;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Double getAdditionalKM(int carVehicleNumber) {
        String sql = "SELECT AdditionalKM FROM rentalcontract WHERE carVehicleNumber = ?";
        return template.queryForObject(sql, Double.class, carVehicleNumber);
    }

        public Double calculateAdditionalKMPrice(int carVehicleNumber, double pricePerKM) {
            Double additionalKM = getAdditionalKM(carVehicleNumber);
            return additionalKM != null ? additionalKM * pricePerKM : 0.0;
        }
    }