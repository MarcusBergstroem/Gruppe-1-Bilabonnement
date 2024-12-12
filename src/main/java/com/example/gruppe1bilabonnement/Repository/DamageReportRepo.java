package com.example.gruppe1bilabonnement.Repository;

import com.example.gruppe1bilabonnement.Model.DamageReport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DamageReportRepo {
    private final JdbcTemplate template;

    public DamageReportRepo (JdbcTemplate template) {
        this.template = template;
    }

    public List<DamageReport> fetchAllDamageReports() {

        return null;
    }
}
