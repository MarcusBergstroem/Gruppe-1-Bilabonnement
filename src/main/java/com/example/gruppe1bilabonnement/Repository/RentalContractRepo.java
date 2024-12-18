package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.DamageReport;
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
                geo.Country AS country,
                geo.Address AS address
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

        //SQL query som finder alle lejekontrakt oplysninger, men med en where clause, hvor hvad brugeren søger efter er omringet af wildcard symboler
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
            where registrationNumber like concat('%', ?, '%')
        """;
        //Oprettet rowmapper for et RentalContract objekt
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        List<RentalContract> rentalContractList = template.query(sql, rowMapper, regNumber);

        //Finder alle biler i databasen
        String sqlCar = "select * from car";
        RowMapper<Car> rowMapper2 = new BeanPropertyRowMapper<>(Car.class);
        List<Car> carTmp = template.query(sqlCar, rowMapper2);

        //Finder alle skadejournaler i databasen
        String sqlDamageReport = "select * from damagereport";
        RowMapper<DamageReport> rowMapper3 = new BeanPropertyRowMapper<>(DamageReport.class);
        List<DamageReport> damageReportTmp = template.query(sqlDamageReport, rowMapper3);

        //Itererer over alle RentalContract og Car objekter som er fundet, og tildeler RentalContract's car field til det Car objekt, som har ens vehiclenumber
        for (RentalContract rentalContract : rentalContractList) {
            for (Car car : carTmp) {
                if (car.getVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setRentalCar(car);
                }
            }
        }

        //Gør det samme for skadejournaler
        for (RentalContract rentalContract : rentalContractList) {
            for (DamageReport damageReport : damageReportTmp) {
                if (damageReport.getCarVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setDamageReport(damageReport);
                }
            }
        }

        return rentalContractList;
    }

    public List<RentalContract> fetchAll() {

        //SQL query som finder alle lejekontrakter i databasen
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
                    delivery_return_location drl2 ON rc.ReturnLocationID = drl2.id;
                """;
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        List<RentalContract> rentalContractList = template.query(sql, rowMapper);

        //Finder alle biler i databsen
        String sqlCar = "select * from car";
        RowMapper<Car> rowMapper2 = new BeanPropertyRowMapper<>(Car.class);
        List<Car> carTmp = template.query(sqlCar, rowMapper2);

        //Finder alle skadejournaler i databsen
        String sqlDamageReport = "select * from damagereport";
        RowMapper<DamageReport> rowMapper3 = new BeanPropertyRowMapper<>(DamageReport.class);
        List<DamageReport> damageReportTmp = template.query(sqlDamageReport, rowMapper3);

        //Fylder hver RentalContract objekt med de rigtige biler
        for (RentalContract rentalContract : rentalContractList) {
            for (Car car : carTmp) {
                if (car.getVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setRentalCar(car);
                }
            }
        }

        //Samme for skadejournaler
        for (RentalContract rentalContract : rentalContractList) {
            for (DamageReport damageReport : damageReportTmp) {
                if (damageReport.getCarVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setDamageReport(damageReport);
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
    public List<RentalContract> fetchAllLeasedCars(){

        //SQL Query der finder alle lejekontraker med biler som har rentalstatus = leased
        String sql = """
        SELECT 
            rc.*,
            c.rentalstatus,
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
        INNER JOIN
            car c on rc.carvehiclenumber = c.vehiclenumber
        where rentalstatus='leased' and rentalstatus='sold'
        order by returndate asc 
    """;
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        List<RentalContract> rentalContractList = template.query(sql, rowMapper);

        //Finder alle biler
        String sqlCar = "select * from car";
        RowMapper<Car> rowMapper2 = new BeanPropertyRowMapper<>(Car.class);
        List<Car> carTmp = template.query(sqlCar, rowMapper2);


        //finder alle skadejournaler
        String sqlDamageReport = "select * from damagereport";
        RowMapper<DamageReport> rowMapper3 = new BeanPropertyRowMapper<>(DamageReport.class);
        List<DamageReport> damageReportTmp = template.query(sqlDamageReport, rowMapper3);

        //Fylder hver RentalContract objekt med de rigtige biler
        for (RentalContract rentalContract : rentalContractList) {
            for (Car car : carTmp) {
                if (car.getVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setRentalCar(car);
                }
            }
        }
        //Gør det samme for skadejournaler
        for (RentalContract rentalContract : rentalContractList) {
            for (DamageReport damageReport : damageReportTmp) {
                if (damageReport.getCarVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setDamageReport(damageReport);
                }
            }
        }

        return rentalContractList;
    }

    //Gør det samme som fetchAllLeasedCars(), men bruger wilcards til at lade brugeren søge
    public List<RentalContract> searchAllLeasedCars(String regNumber){

        String sql = """
        SELECT 
            rc.*,
            c.rentalstatus,
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
        INNER JOIN
            car c on rc.carvehiclenumber = c.vehiclenumber
        where rentalstatus='leased' and registrationNumber like concat('%', ?, '%')
        order by returndate asc 
    """;
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        List<RentalContract> rentalContractList = template.query(sql, rowMapper, regNumber);

        String sqlCar = "select * from car";
        RowMapper<Car> rowMapper2 = new BeanPropertyRowMapper<>(Car.class);
        List<Car> carTmp = template.query(sqlCar, rowMapper2);

        String sqlDamageReport = "select * from damagereport";
        RowMapper<DamageReport> rowMapper3 = new BeanPropertyRowMapper<>(DamageReport.class);
        List<DamageReport> damageReportTmp = template.query(sqlDamageReport, rowMapper3);

        for (RentalContract rentalContract : rentalContractList) {
            for (Car car : carTmp) {
                if (car.getVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setRentalCar(car);
                }
            }
        }
        for (RentalContract rentalContract : rentalContractList) {
            for (DamageReport damageReport : damageReportTmp) {
                if (damageReport.getCarVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setDamageReport(damageReport);
                }
            }
        }

        return rentalContractList;
    }

    public List<RentalContract> fetchAllContractsWithDamageReport(){
        //Finder alle lejekontrakter, som har et damagereportid knyttet til sig.
        String sql = """
        SELECT 
            rc.*,
            c.rentalstatus,
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
        INNER JOIN
            car c on rc.carvehiclenumber = c.vehiclenumber
        where damagereportid is not null 
        order by returndate asc
    """;
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        List<RentalContract> rentalContractList = template.query(sql, rowMapper);

        String sqlCar = "select * from car";
        RowMapper<Car> rowMapper2 = new BeanPropertyRowMapper<>(Car.class);
        List<Car> carTmp = template.query(sqlCar, rowMapper2);

        String sqlDamageReport = "select * from damagereport";
        RowMapper<DamageReport> rowMapper3 = new BeanPropertyRowMapper<>(DamageReport.class);
        List<DamageReport> damageReportTmp = template.query(sqlDamageReport, rowMapper3);


        //Samler lejekontrakter, biler og skade journaler
        for (RentalContract rentalContract : rentalContractList) {
            for (Car car : carTmp) {
                if (car.getVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setRentalCar(car);
                }
            }
        }
        for (RentalContract rentalContract : rentalContractList) {
            for (DamageReport damageReport : damageReportTmp) {
                if (damageReport.getCarVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setDamageReport(damageReport);
                }
            }
        }

        return rentalContractList;
    }

    //Fungere ligesom fetchAllContractsWithDamageReport(), men bruger wildcards til at søge på registreringsnummer
    public List<RentalContract> searchAllContractsWithDamageReport(String regNumber){

        String sql = """
        SELECT 
            rc.*,
            c.rentalstatus,
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
        INNER JOIN
            car c on rc.carvehiclenumber = c.vehiclenumber
        where damagereportid is not null and registrationNumber like concat('%', ?, '%')
        order by returndate asc
    """;
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        List<RentalContract> rentalContractList = template.query(sql, rowMapper, regNumber);

        String sqlCar = "select * from car";
        RowMapper<Car> rowMapper2 = new BeanPropertyRowMapper<>(Car.class);
        List<Car> carTmp = template.query(sqlCar, rowMapper2);

        String sqlDamageReport = "select * from damagereport";
        RowMapper<DamageReport> rowMapper3 = new BeanPropertyRowMapper<>(DamageReport.class);
        List<DamageReport> damageReportTmp = template.query(sqlDamageReport, rowMapper3);

        for (RentalContract rentalContract : rentalContractList) {
            for (Car car : carTmp) {
                if (car.getVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setRentalCar(car);
                }
            }
        }
        for (RentalContract rentalContract : rentalContractList) {
            for (DamageReport damageReport : damageReportTmp) {
                if (damageReport.getCarVehicleNumber() == rentalContract.getCarVehicleNumber()) {
                    rentalContract.setDamageReport(damageReport);
                }
            }
        }

        return rentalContractList;
    }



    public RentalContract findByRegistrationNumber(String regNumber) {
        String sql = "SELECT * FROM RentalContract WHERE registrationNumber = ?";
        List<RentalContract> contracts = template.query(sql, new BeanPropertyRowMapper<>(RentalContract.class), regNumber);
        if (contracts.isEmpty()) {
            return null;
        }
        return contracts.get(0);
    }


    public int getDamageReportID(int carVehicleNumber) {
        try {
            String sql = "SELECT ID FROM damagereport where CarVehicleNumber = ?";
            return template.queryForObject(sql, Integer.class, carVehicleNumber);
        }catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public Double getTotalDamagePrice(int carVehicleNumber) {
        String sql = "SELECT SUM(Price) FROM damage WHERE DamageReportID = ?";
        if (getDamageReportID(carVehicleNumber) == -1) {
            return 0.0;
        }
        Double totalDamagePrice = template.queryForObject(sql, Double.class, getDamageReportID(carVehicleNumber));
        return totalDamagePrice != null ? totalDamagePrice : 0.0;
    }

    public Double fetchAdditionalKMPrice(int carVehicleNumber) {
        String sql = "SELECT AdditionalKM FROM rentalcontract WHERE carVehicleNumber = ?";
        return template.queryForObject(sql, Double.class, carVehicleNumber);
    }

    public Double fetchTotalKilometers(int carVehicleNumber) {
        String sql = "SELECT TotalKilometers FROM rentalcontract WHERE carVehicleNumber = ?";
        try {
            return template.queryForObject(sql, Double.class, carVehicleNumber);
        } catch (EmptyResultDataAccessException e) {
            return 0.0;
        }
    }

    public Double fetchCarMileage(int vehicleNumber) {
        String sql = "SELECT IFNULL(MIleage, 0) FROM car WHERE VehicleNumber = ?";
        try {
            return template.queryForObject(sql, Double.class, vehicleNumber);
        } catch (EmptyResultDataAccessException e) {
            return 0.0;
        }
    }

    public Double calculateExtraKMPrice(int carVehicleNumber) {
        double totalKilometers = fetchTotalKilometers(carVehicleNumber);

        Double carMileage = fetchCarMileage(carVehicleNumber);

        if (carMileage == null || carMileage <= totalKilometers) {
            return 0.0;
        }

        double additionalKilometers = carMileage - totalKilometers;

        Double pricePerExtraKM = fetchAdditionalKMPrice(carVehicleNumber);

        System.out.println();
        System.out.println("Ekstra kilometer: " + additionalKilometers);
        System.out.println("Total kilometer: " + totalKilometers);
        System.out.println("Total mileage: " + fetchCarMileage(carVehicleNumber));
        System.out.println("Mileage: " + carMileage);
        System.out.println("Pris per ekstra kilometer: " + pricePerExtraKM);

        return pricePerExtraKM != null ? pricePerExtraKM * additionalKilometers : 0.0;
    }

    public Boolean hasJournal(String regNumber) {

        //Finder vehiclenumber på den kontrakt, som har det givet registreringsnummer
        String sqlContract = "select carvehiclenumber from rentalcontract where registrationNumber = ?";
        int carVNumber = template.queryForObject(sqlContract, Integer.class, regNumber);

        //Finder hvor mange rows der er i damagereport tabellen, hvor kotraktens carvehiclenumber bruges
        //Vores system tillader ikke flere skadejournaler pr. bil, så count vil altid svare til en boolean, om skadejournalen findes eller ej.
        String sqlDamageReport = "select count(*) from damagereport where carvehiclenumber=?";
        return template.queryForObject(sqlDamageReport, Boolean.class, carVNumber);
    }
}