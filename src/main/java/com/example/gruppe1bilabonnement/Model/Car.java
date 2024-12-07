package com.example.gruppe1bilabonnement.Model;

public class Car {
    private int id;
    private int vehicleNumber;
    private String carBrand;
    private String carModel;
    private String equipmentLevel;
    private String vin;
    private String registrationNumber;
    private String returnDate;

    //Default Constructor
    public Car(){

    }

    // Constructor
    public Car(int id, int vehicleNumber, String carBrand, String carModel, String equipmentLevel, String vin, String registrationNumber, String returnDate) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.equipmentLevel = equipmentLevel;
        this.vin = vin;
        this.registrationNumber = registrationNumber;
        this.returnDate = returnDate;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    public String getVin() {
        return vin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setEquipmentLevel(String equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
