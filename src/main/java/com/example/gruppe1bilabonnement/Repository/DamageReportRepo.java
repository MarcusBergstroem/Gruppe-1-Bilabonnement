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


    //Tilføjer en skade i damage tabellen
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

    //Samler en skadejournal med de oplysninger, som er blevet sendt fra opret_skadejournal.html
    public DamageReport assembleDamageReport(Map<String, String> formData) {
        //Laver ny Damagereport objekt
        DamageReport damageReport = new DamageReport();

        //Fjerner tomme felter som ikke blev fyldt ud.
        formData.values().removeIf(s -> s.trim().isEmpty());

        //Laver to lister, values som indeholder alle felternes data, og chunk som holder op til 3 felter ad gangen
        List<String> values = new ArrayList<>(formData.values());
        List<String> chunk = new ArrayList<>();

        //Looper over values listen
        for (String value : values) {
            //Tilføjer nuværende value til chunk listen
            chunk.add(value);

            //Hvis chunk består af tre felter
            if (chunk.size() == 3) {
                System.out.println("Processing chunk: " + chunk);
                //Tilføjer et damage objekt, til listen på damagereport-objektet, bestående af de tre felter i chunk listen
                damageReport.addDamageToList(new Damage(chunk.get(0), chunk.get(1), chunk.get(2)));
                //Fjerner alt i chunk, så tre nye felter kan lægges i
                chunk.clear();
            }
        }
        //Til sidst bruger vi vehiclenumber og mileage, som også er sendt af opret_skadejournal.html.
        //Det er det resterende i chunk listen, efter alle damages er tilføjet.
        if (!chunk.isEmpty()) {
            System.out.println("Processing extra chunk: " + chunk);
            damageReport.setCarVehicleNumber(Integer.parseInt(chunk.get(0)));
            if(chunk.size() > 1) {
                addMileage(Integer.parseInt(chunk.get(1)), damageReport.getCarVehicleNumber());
            }
        }
        return damageReport;
    }

    //Tilføjer kilometerstand i car tabellen, hvor vehiclenumber passer
    public void addMileage(int mileage, int vehicleNumber) {
        String sql = "update car set mileage = ? where vehiclenumber = ?";
        template.update(sql, mileage, vehicleNumber);
    }

    //Tilføjer skader, skadejournal til databasen, og opdatere felter i car og rentalcontract tabellen
    public void addDamageReport(DamageReport damageReport) {

        //Indsætter kun en række i damagereport tabellen, hvis der ikke allerede findes en med det gældende carvehiclenumber
        String sql = "insert ignore into damagereport (carvehiclenumber) values (?)";
        template.update(sql, damageReport.getCarVehicleNumber());

        //Finder id på den allerede eksisterende eller nylavet række
        String sqlReport = "select id from damagereport where CarVehicleNumber = ?";
        damageReport.setId(template.queryForObject(sqlReport, Integer.class, damageReport.getCarVehicleNumber()));

        //Sætter bilens status til at være atstorage
        String sqlStatus = "update car set rentalstatus = 'atstorage' where vehiclenumber=?";
        template.update(sqlStatus, damageReport.getCarVehicleNumber());

        //Indsætter id på skadejournalen i rentalcontract tabellen, hvor vehiclenumber passer
        String sqlReportID = "update rentalcontract set damagereportid = ? where carvehiclenumber=?";
        template.update(sqlReportID, damageReport.getId(), damageReport.getCarVehicleNumber());

        //Indsæter alle skader i damage tabellen
        for (Damage damage : damageReport.getDamageList()){
            addDamage(damage, damageReport.getId());
        }

    }

    public DamageReport fetchDamageReport(int vehicleNumber) {

        //Finder id på skadejournal med gældende vehiclenumber
        String sqlReport = "select id from damagereport where CarVehicleNumber = ?";
        int id = template.queryForObject(sqlReport, Integer.class, vehicleNumber);

        //Finder alle skader med det gældende skadejuornal id, og putter i en liste af Damage objekter
        String sqlDamage = "select * from damage where damagereportid = ?";
        RowMapper<Damage> rowMapper = new BeanPropertyRowMapper<>(Damage.class);
        List<Damage> damageList = template.query(sqlDamage, rowMapper, id);

        //Returnere et ny damagereport objekt, med vehiclenubmer og den nye liste damage objekter
        return new DamageReport(vehicleNumber,damageList);
    }

    //Sletter en skade i damage tabellen
    public void deleteDamage(int id){
        String sql = "delete from damage where id = ?";
        template.update(sql, id);
    }

    public double getTotalPrice(int vehicleNumber) {
        //Finder id på skadejuornal, udfra vehiclenumber
        String sql = "select id from damagereport where CarVehicleNumber = ?";
        int id = template.queryForObject(sql, Integer.class, vehicleNumber);

        //Finder totale pris på alle skader, vec hjælp af SUM funktion i mysql
        String sqlPrice = "select IFNULL(SUM(price), 0) from damage where damagereportid = ?";
        return template.queryForObject(sqlPrice, Double.class, id);
    }
}
