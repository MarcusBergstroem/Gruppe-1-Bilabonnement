package com.example.gruppe1bilabonnement.Model;

public class Car {

    private int id;
    private int vehicleNumber;
    private String carBrand;
    private String carModel;
    private String equipmentLevel;
    private String vin;
    private String registrationNumber;

    // Constructor
    public Car(int id, int vehicleNumber, String carBrand, String carModel, String equipmentLevel, String vin, String registrationNumber) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.equipmentLevel = equipmentLevel;
        this.vin = vin;
        this.registrationNumber = registrationNumber;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(String equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
