package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Buyer;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Model.Renter;
import com.example.gruppe1bilabonnement.Model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class BuyerRepo {
    private final JdbcTemplate template;

    @Autowired
    public BuyerRepo(JdbcTemplate template) {
        this.template = template;
    }

    public int addBuyerGeo(String country, String city, String zipCode, String address) {
        String selectSql = "SELECT id FROM BuyerGeo WHERE Country = ? AND City = ? AND ZipCode = ? AND Address = ?";
        Integer existingId = null;

        try {
            existingId = template.queryForObject(selectSql, new Object[]{country, city, zipCode, address}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
        }

        if (existingId != null) {
            return existingId; // Returnere existingId
        }

        // Indsæter information i BuyerGeo-tabellen og finder primærnøglen
        String insertSql = "INSERT INTO BuyerGeo (Country, City, ZipCode, Address) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, country);
            ps.setString(2, city);
            ps.setString(3, zipCode);
            ps.setString(4, address);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue(); // Returnere id
    }
    //
    public void addBuyer(Buyer buyer, int buyerGeoId) {
        String sql = "INSERT INTO buyer (CompanyName, CVR, PhoneNumber, BuyerGeoID) VALUES (?, ?, ?, ?)";
        template.update(sql,
                buyer.getCompanyName(),
                buyer.getCvr(),
                buyer.getPhoneNumber(),
                buyerGeoId);
    }
    public List<Buyer> fetchAllBuyers() {
        String sql = "SELECT * FROM buyer";
        RowMapper<Buyer> rowMapper = new BeanPropertyRowMapper<>(Buyer.class);
        return template.query(sql, rowMapper);
    }
}
