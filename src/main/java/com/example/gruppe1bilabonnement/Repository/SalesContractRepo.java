package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Buyer;
import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SalesContractRepo {
    private final JdbcTemplate template;

    @Autowired
    public SalesContractRepo(JdbcTemplate template) {
        this.template = template;
    }

    public void addSalesContract (SalesContract salesContract){
        String sql = "INSERT INTO salescontract (BuyerId, VehicleNumber, SaleDate, DeliveryAddress, SalePrice, " +
                "TotalKilometers, Options, RochsTransport) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        template.update(sql,
                salesContract.getBuyerId(),
                salesContract.getVehicleNumber(),
                salesContract.getSaleDate(),
                salesContract.getDeliveryAddress(),
                salesContract.getSalePrice(),
                salesContract.getTotalKilometers(),
                salesContract.getOptions(),
                salesContract.getRochsTransport()
        );
        String updateCarStatusSql = "UPDATE car SET RentalStatus = ? WHERE VehicleNumber = ?";
        template.update(updateCarStatusSql, "sold", salesContract.getVehicleNumber());
    }
    public List<SalesContract> fetchSalesContracts() {
        String sql = """
    SELECT 
        sc.ContractId,
        sc.SaleDate,
        sc.SalePrice,
        sc.DeliveryAddress,
        sc.TotalKilometers,
        sc.Options,
        sc.RochsTransport,
        b.CompanyName,
        b.CVR,
        b.PhoneNumber,
        bg.Address,
        bg.City,
        bg.Zipcode,
        bg.Country,
        c.CarBrand,
        c.CarModel,
        c.EquipmentLevel,
        c.VIN
    FROM 
        salescontract sc
    INNER JOIN 
        car c ON sc.VehicleNumber = c.VehicleNumber
    INNER JOIN 
        buyer b ON sc.BuyerId = b.buyerId
    INNER JOIN 
        buyerGeo bg ON b.BuyerGeoID = bg.id;
    """;

        return template.query(sql, (rs, rowNum) -> {
            SalesContract salesContract = new SalesContract();

            // Populate SalesContract fields
            salesContract.setContractId(rs.getInt("ContractId"));
            salesContract.setSaleDate(rs.getDate("SaleDate").toLocalDate());
            salesContract.setSalePrice(rs.getDouble("SalePrice"));
            salesContract.setDeliveryAddress(rs.getString("DeliveryAddress"));
            salesContract.setTotalKilometers(rs.getInt("TotalKilometers"));
            salesContract.setOptions(rs.getString("Options"));
            salesContract.setRochsTransport(rs.getBoolean("RochsTransport"));

            // Populate Buyer details
            Buyer buyer = new Buyer();
            buyer.setCompanyName(rs.getString("CompanyName"));
            buyer.setCvr(rs.getString("CVR"));
            buyer.setPhoneNumber(rs.getString("PhoneNumber"));
            buyer.setAddress(rs.getString("Address"));
            buyer.setCity(rs.getString("City"));
            buyer.setZipcode(rs.getString("Zipcode"));
            buyer.setCountry(rs.getString("Country"));
            salesContract.setBuyer(buyer);

            // Populate Car details
            Car car = new Car();
            car.setCarBrand(rs.getString("CarBrand"));
            car.setCarModel(rs.getString("CarModel"));
            car.setEquipmentLevel(rs.getString("EquipmentLevel"));
            car.setVin(rs.getString("VIN"));
            salesContract.setCar(car);

            return salesContract;
        });
    }
    public List<SalesContract> searchSalesContracts(String vin) {
        //fetcher alle lejekontraker som matcher på vin nummeret
        String sql = """        
        SELECT 
            sc.ContractId,
            sc.SaleDate,
            sc.SalePrice,
            sc.DeliveryAddress,
            sc.TotalKilometers,
            sc.Options,
            sc.RochsTransport,
            b.BuyerId,
            c.VehicleNumber
        FROM 
            salescontract sc
        INNER JOIN 
            car c ON sc.VehicleNumber = c.VehicleNumber
        INNER JOIN 
            buyer b ON sc.BuyerId = b.BuyerId
        WHERE 
            c.VIN LIKE CONCAT('%', ?, '%');
    """;
        RowMapper<SalesContract> rowMapper = new BeanPropertyRowMapper<>(SalesContract.class);
        List<SalesContract> salesContractList = template.query(sql, rowMapper, vin);

        // Fetcher alle biler
        String sqlCar = "SELECT * FROM car";
        RowMapper<Car> carRowMapper = new BeanPropertyRowMapper<>(Car.class);
        List<Car> carList = template.query(sqlCar, carRowMapper);

        // Fetcher alle købere
        String sqlBuyer = """
        SELECT 
            b.*, 
            bg.Address, 
            bg.City, 
            bg.Zipcode, 
            bg.Country
        FROM 
            buyer b
        INNER JOIN 
            buyergeo bg ON b.BuyerGeoID = bg.id;
    """;
        RowMapper<Buyer> buyerRowMapper = new BeanPropertyRowMapper<>(Buyer.class);
        List<Buyer> buyerList = template.query(sqlBuyer, buyerRowMapper);

        // Her tilføjes biloplysningerne til lejekontrakten
        for (SalesContract salesContract : salesContractList) {
            // Match and set car
            for (Car car : carList) {
                if (car.getVehicleNumber() == salesContract.getVehicleNumber()) {
                    salesContract.setCar(car);
                    break;
                }
            }
            //Her tilføjes køberoplysningerne
            for (Buyer buyer : buyerList) {
                if (buyer.getBuyerId() == salesContract.getBuyerId()) {
                    salesContract.setBuyer(buyer);
                    break;
                }
            }
        }
        return salesContractList;
    }
}
