package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Model.Renter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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





}
