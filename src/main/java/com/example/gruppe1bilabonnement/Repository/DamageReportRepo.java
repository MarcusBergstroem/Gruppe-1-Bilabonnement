package com.example.gruppe1bilabonnement.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DamageReportRepo {
    private final JdbcTemplate template;

    public DamageReportRepo (JdbcTemplate template) {
        this.template = template;
    }

}
