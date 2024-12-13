package com.example.gruppe1bilabonnement.Model;


import java.time.LocalDate;


public class DamageReport {

    private Long id;
    private String description;
    private int classification; // 1 - 5 (eller et andet system for skadeklassifikation)
    private double price;
    private Long rentalContractId; // Kan relateres til en lejeaftale

    public DamageReport() {
    }

    public DamageReport(Long id, String description, int classification, double price, Long rentalContractId, LocalDate creationDate) {
        this.id = id;
        this.description = description;
        this.classification = classification;
        this.price = price;
        this.rentalContractId = rentalContractId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getRentalContractId() {
        return rentalContractId;
    }

    public void setRentalContractId(Long rentalContractId) {
        this.rentalContractId = rentalContractId;
    }
}
