package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Damage;
import com.example.gruppe1bilabonnement.Model.DamageReport;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DamageReportRepo {
    private final JdbcTemplate template;

    public DamageReportRepo (JdbcTemplate template) {
        this.template = template;
    }

    public List<DamageReport> fetchAllDamageReports() {

        return null;
    }

    public void addDamage(Damage damage, int id) {
        String sql = "INSERT INTO damage (DamageDescription, Classification, Price, DamageReportID)" +
                "VALUES (?, ?, ?, ?)";

        template.update(sql,
                damage.getDamageDescription(),
                damage.getClassification(),
                damage.getPrice(),
                id
        );
    }

    public DamageReport assembleDamageReport(Map<String, String> formData) {
        DamageReport damageReport = new DamageReport();

        formData.values().removeIf(s -> s.trim().isEmpty());
        List<String> values = new ArrayList<>(formData.values());
        List<String> chunk = new ArrayList<>();

        for (String value : values) {
            chunk.add(value);

            if (chunk.size() == 3) {
                System.out.println("Processing chunk: " + chunk);
                damageReport.addDamageToList(new Damage(chunk.get(0), chunk.get(1), chunk.get(2)));
                chunk.clear();
            }
        }

        if (!chunk.isEmpty()) {
            damageReport.setCarVehicleNumber(Integer.parseInt(chunk.get(0)));
        }
        return damageReport;
    }

    public void addDamageReport(DamageReport damageReport) {

        String sql = "insert ignore into damagereport (carvehiclenumber) values (?)";
        template.update(sql, damageReport.getCarVehicleNumber());

        String sqlReport = "select id from damagereport where CarVehicleNumber = ?";
        int id = template.queryForObject(sqlReport, Integer.class, damageReport.getCarVehicleNumber());

        for (Damage damage : damageReport.getDamageList()){
            addDamage(damage, id);
        }

    }

    public DamageReport fetchDamageReport(int vehicleNumber) {
        String sqlReport = "select id from damagereport where CarVehicleNumber = ?";
        int id = template.queryForObject(sqlReport, Integer.class, vehicleNumber);

        String sqlDamage = "select * from damage where damagereportid = ?";
        RowMapper<Damage> rowMapper = new BeanPropertyRowMapper<>(Damage.class);
        List<Damage> damageList = template.query(sqlDamage, rowMapper, id);

        return new DamageReport(vehicleNumber,damageList);
    }

    public void deleteDamage(int id){
        String sql = "delete from damage where id = ?";
        template.update(sql, id);
    }


}
