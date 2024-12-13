package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Model.Renter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Repository
public class StatsRepo {

    private final JdbcTemplate template;

    // Default konstruktør
    public StatsRepo(JdbcTemplate template){
        this.template = template;
    }

    // Metode som laver en liste af bilobjekter, som skal bruges til at skrive i HTML
    public List<Car> fetchAllCarDetails() {
        String sql = "SELECT * FROM car";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        List<Car> carList = template.query(sql, rowMapper);
        return carList;
    }

    // Metode som laver en liste af kontraktobjekter, som skal bruges til at skrive i HTML
    public List<RentalContract> fetchAllContractDetails() {

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
                """;

        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        List<RentalContract> rentalContractList = template.query(sql, rowMapper);

        // Da vi har med en liste af kontrakter at gøre, loopes over listen
        for (RentalContract rentalContract: rentalContractList){

            // Tilføjer et bil-objekt som felt i lejekontrakt-objektet
            String sqlCar = "SELECT * FROM car WHERE VehicleNumber = ?";
            RowMapper<Car> rowMapper2 = new BeanPropertyRowMapper<>(Car.class);
            Car carTmp = template.queryForObject(sqlCar, rowMapper2, rentalContract.getCarVehicleNumber());
            rentalContract.setRentalCar(carTmp);

            // Tilføjer et renter-objekt som felt i lejekontrakt-objektet
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
            Renter renterTmp = template.queryForObject(sqlRenter, rowMapper3, rentalContract.getRenterID());
            rentalContract.setRentalRenter(renterTmp);

        }

        return rentalContractList;
    }

    // Metode som laver liste af renter-objekter som skal skrives til HTML
    public List<Renter> fetchAllRenterDetails() {

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
                """;

        RowMapper<Renter> rowMapper = new BeanPropertyRowMapper<>(Renter.class);
        return template.query(sqlRenter, rowMapper);
    }

    // Metode returnerer månedlig omsætning på nuværende udlejede biler
    public double rentedCarsThisMonthRevenue(){
        String sql = "SELECT SUM(monthlyPayment) FROM rentalcontract WHERE carVehicleNumber in (SELECT vehiclenumber FROM car WHERE rentalStatus = 'Leased')";
        return template.queryForObject(sql, Integer.class);
    }

    // Metode returnerer hhv. førstegangs- og månedlig ydelse fordelt på måneder
    public List<List<Double>> revenueYearToDate(){

        // Opretter tomme lister som værdierne skal skrives ind i
        List<Double> initialList = new ArrayList<>();
        List<Double> monthlyList = new ArrayList<>();
        List<Double> totalList = new ArrayList<>();

        // Disse lister indeholder 2 værdi (årets initial og årets månedlige)
        List<Double> yearTotals = new ArrayList<>();

        double sumOfInitial = 0.0;
        double sumOfMonthly = 0.0;

        // Looper over de 12 måneder og finder både initial payment og monthly payment (24 datafelter i alt)
        for (int i = 1; i < 13; i++){

            // Finder indeværende år
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);

            String startDate = currentYear + "-" + String.format("%02d", i) + "-01";
            String endDate = currentYear + "-" + String.format("%02d", i) + "-31";

            // Initial payment
            String sqlInitial = "SELECT SUM(InitialPayment) FROM rentalcontract WHERE deliveryDate BETWEEN ? AND ?";
            Double initial = template.queryForObject(sqlInitial, Double.class, startDate, endDate);
            if (initial == null) {
                initial = 0.0;
            }
            initialList.add(initial);

            // Tilføjer til sum af førstegangsydelser
            sumOfInitial += initial;

            // MonthlyPayment
            String sqlMonthly = "SELECT SUM(MonthlyPayment) FROM rentalcontract WHERE deliveryDate BETWEEN ? AND ?";
            Double monthly = template.queryForObject(sqlMonthly, Double.class, startDate, endDate);
            if (monthly == null) {
                monthly = 0.0;
            }
            monthlyList.add(monthly);

            // Tilføjer til sum af månedlige ydelser
            sumOfMonthly += monthly;

            // Denne liste indeholder samlet omsætning pr måned (12 værdier).
            totalList.add(initial+monthly);

        }

        // Gemmer årstotalerne i listen hertil
        yearTotals.add(sumOfInitial);
        yearTotals.add(sumOfMonthly);

        System.out.println(yearTotals);

        List<List<Double>> revenueList = new ArrayList<>();
        revenueList.add(initialList);
        revenueList.add(monthlyList);
        revenueList.add(totalList);
        revenueList.add(yearTotals);

        return revenueList;
    }









}
