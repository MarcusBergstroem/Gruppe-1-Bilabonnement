package com.example.gruppe1bilabonnement.Model;

public class Car {
    private int vehicleNumber;
    private String carBrand;
    private String carModel;
    private String equipmentLevel;
    private String vin;
    private String returnDate;
    private String rentalStatus;

    //Default Constructor
    public Car(){

    }

    // Constructor
    public Car(int id, int vehicleNumber, String carBrand, String carModel, String equipmentLevel, String vin, String returnDate, String rentalStatus) {
        this.vehicleNumber = vehicleNumber;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.equipmentLevel = equipmentLevel;
        this.vin = vin;
        this.returnDate = returnDate;
        this.rentalStatus = rentalStatus;
    }


    // Getters and Setters

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

    public String getReturnDate() {
        return returnDate;
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

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    @Override
    public String toString() {
        return "Car{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", equipmentLevel='" + equipmentLevel + '\'' +
                ", vin=" + vin +
                ", returndate=" + returnDate +
                ", rentalStatus=" + rentalStatus +
                '}';
    }
}
