package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.Damage;
import com.example.gruppe1bilabonnement.Model.DamageReport;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public void addDamage(Damage damage, int vehicleNumber) {
        String sql = "INSERT INTO damagereport (CarVehicleNumber, DamageDescription, Classification, Price)" +
                "VALUES (?, ?, ?, ?)";

        template.update(sql,
                vehicleNumber,
                damage.getDamageDescription(),
                damage.getClassification(),
                damage.getPrice()
        );
    }

    public void addDamageReport(Map<String, String> formData) {
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

        for (Damage damage : damageReport.getDamageReportList()){
            addDamage(damage, damageReport.getCarVehicleNumber());
        }

    }
}
