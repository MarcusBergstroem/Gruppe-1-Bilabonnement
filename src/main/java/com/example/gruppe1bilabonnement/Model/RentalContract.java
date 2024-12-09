package com.example.gruppe1bilabonnement.Model;

import java.time.LocalDate;

public class RentalContract {

    private int id;
    private int renterCpr;
    private int carVehicleNumber;
    private int deliveryReturnLocationId;
    private String registrationNumber;
    private LocalDate deliveryDate;
    private LocalDate returnDate;
    private double initialPayment;
    private double monthlyPayment;
    private int totalKilometers;
    private double additionalKM;
    private String customChoices;
    private boolean isSigned;


    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public RentalContract(){

    }

    // Constructor
    public RentalContract(int id, int renterCpr, int carVehicleNumber, int deliveryReturnLocationId, String registrationNumber,
                          LocalDate deliveryDate, LocalDate returnDate, double initialPayment,
                          double monthlyPayment, int totalKilometers, double additionalKM,
                          String customChoices, boolean isSigned) {
        this.id = id;
        this.renterCpr = renterCpr;
        this.carVehicleNumber = carVehicleNumber;
        this.deliveryReturnLocationId = deliveryReturnLocationId;
        this.registrationNumber = registrationNumber;
        this.deliveryDate = deliveryDate;
        this.returnDate = returnDate;
        this.initialPayment = initialPayment;
        this.monthlyPayment = monthlyPayment;
        this.totalKilometers = totalKilometers;
        this.additionalKM = additionalKM;
        this.customChoices = customChoices;
        this.isSigned = isSigned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRenterCpr() {
        return renterCpr;
    }

    public void setRenterCpr(int renterCpr) {
        this.renterCpr = renterCpr;
    }

    public int getCarVehicleNumber() {
        return carVehicleNumber;
    }

    public void setCarVehicleNumber(int carVehicleNumber) {
        this.carVehicleNumber = carVehicleNumber;
    }

    public int getDeliveryReturnLocationId() {
        return deliveryReturnLocationId;
    }

    public void setDeliveryReturnLocationId(int deliveryReturnLocationId) {
        this.deliveryReturnLocationId = deliveryReturnLocationId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getInitialPayment() {
        return initialPayment;
    }

    public void setInitialPayment(double initialPayment) {
        this.initialPayment = initialPayment;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public int getTotalKilometers() {
        return totalKilometers;
    }

    public void setTotalKilometers(int totalKilometers) {
        this.totalKilometers = totalKilometers;
    }

    public double getAdditionalKM() {
        return additionalKM;
    }

    public void setAdditionalKM(double additionalKM) {
        this.additionalKM = additionalKM;
    }

    public String getCustomChoices() {
        return customChoices;
    }

    public void setCustomChoices(String customChoices) {
        this.customChoices = customChoices;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

}

