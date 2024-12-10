package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Renter;
import org.springframework.beans.factory.annotation.Autowired;
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

public class RenterRepo {

    private final JdbcTemplate template;

    @Autowired
    public RenterRepo(JdbcTemplate template) {
        this.template = template;
    }

    public List<Renter> fetchAllRenters() {
        String sql = "SELECT * FROM renter";
        RowMapper<Renter> rowMapper = new BeanPropertyRowMapper<>(Renter.class);
        return template.query(sql, rowMapper);
    }

    public int addGeography(String country, String city, String zipCode) {
        String sql = "INSERT INTO geography (Country, City, ZipCode) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, country);
            ps.setString(2, city);
            ps.setString(3, zipCode);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
    public void addRenter(Renter r, int geographyId) {
        String sql = "INSERT INTO renter (GeographyID, CPR, FirstName, LastName, Address, Blacklist, PhoneNumber) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        template.update(sql,
                geographyId,
                r.getCpr(),
                r.getFirstName(),
                r.getLastName(),
                r.getAddress(),
                r.isBlacklist(),
                r.getPhoneNumber());
    }
}
