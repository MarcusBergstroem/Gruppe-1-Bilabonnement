package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Renter;
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

public class RenterRepo {

    private final JdbcTemplate template;

    @Autowired
    public RenterRepo(JdbcTemplate template) {
        this.template = template;
    }

    public List<Renter> fetchAllRenters() { //Henter alle lejere fra databasen
        String sql = "SELECT * FROM renter";
        RowMapper<Renter> rowMapper = new BeanPropertyRowMapper<>(Renter.class);
        return template.query(sql, rowMapper);
    }
    //Indsætter data i geography tabellen i databasen og genbruger existingId hvis databasen allerede har den geografi
    public int addGeography(String country, String city, String zipCode) {
        String selectSql = "SELECT ID FROM geography WHERE Country = ? AND City = ? AND ZipCode = ?";
        Integer existingId = null;
        try {
            existingId = template.queryForObject(selectSql, new Object[]{country, city, zipCode}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            // Hvis den ikke returnere noget kaster den denne exception
        }
        if (existingId != null) {
            return existingId; // Return the existing ID
        }
        // Insert a new geography record
        String insertSql = "INSERT INTO geography (Country, City, ZipCode) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, country);
            ps.setString(2, city);
            ps.setString(3, zipCode);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue(); // Return the newly generated ID
    }
    //Gemmer lejer i databasen inkl geografiID som er fremmednøgle for geotabellen
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
