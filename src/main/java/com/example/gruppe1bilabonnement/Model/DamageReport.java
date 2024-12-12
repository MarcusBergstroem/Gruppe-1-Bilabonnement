package com.example.gruppe1bilabonnement.Model;


public class DamageReport {

    //Måske unødvendig
    private int carVehicleNumber;
    private int id;

    private String damageDescription;
    private String classification;
    private String price;

    //Default Constructor
    public DamageReport(){

    }

    public DamageReport(int id, int carVehicleNumber, String damageDescription, String classification, String price) {
        this.id = id;
        this.carVehicleNumber = carVehicleNumber;
        this.damageDescription = damageDescription;
        this.classification = classification;
        this.price = price;
    }

    public int getCarVehicleNumber() {
        return carVehicleNumber;
    }

    public void setCarVehicleNumber(int carVehicleNumber) {
        this.carVehicleNumber = carVehicleNumber;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
