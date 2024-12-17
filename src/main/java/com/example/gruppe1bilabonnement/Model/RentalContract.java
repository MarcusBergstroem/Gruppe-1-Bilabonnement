package com.example.gruppe1bilabonnement.Model;

import java.time.LocalDate;

public class RentalContract {

    private int id;
    private int renterID;
    private int carVehicleNumber;
    private String registrationNumber;
    private LocalDate deliveryDate;
    private LocalDate returnDate;
    private double initialPayment;
    private double monthlyPayment;
    private int totalKilometers;
    private double additionalKM;
    private String customChoices;
    private boolean isSigned;
    private int deliveryLocationId;
    private int returnLocationId;

    private String deliveryLocationName;
    private String deliveryLocationAddress;
    private String deliveryLocationZipcode;
    private String deliveryLocationCity;
    private String deliveryLocationCountry;
    private String returnLocationName;
    private String returnLocationAddress;
    private String returnLocationZipcode;
    private String returnLocationCity;
    private String returnLocationCountry;

    private Car rentalCar;
    private Renter rentalRenter;
    private DamageReport damageReport;

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public RentalContract(){

    }

    public RentalContract(int id, int renterID, int carVehicleNumber, int deliveryLocationId, int returnLocationId, String registrationNumber,
                          LocalDate deliveryDate, LocalDate returnDate, double initialPayment,
                          double monthlyPayment, int totalKilometers, double additionalKM,
                          String customChoices, boolean isSigned, String deliveryLocationName, String deliveryLocationAddress, String deliveryLocationZipcode, String deliveryLocationCity, String deliveryLocationCountry, String returnLocationName, String returnLocationAddress, String returnLocationZipcode, String returnLocationCity, String returnLocationCountry) {
        this.id = id;
        this.renterID = renterID;
        this.carVehicleNumber = carVehicleNumber;
        this.deliveryLocationId = deliveryLocationId;
        this.returnLocationId = returnLocationId;
        this.registrationNumber = registrationNumber;
        this.deliveryDate = deliveryDate;
        this.returnDate = returnDate;
        this.initialPayment = initialPayment;
        this.monthlyPayment = monthlyPayment;
        this.totalKilometers = totalKilometers;
        this.additionalKM = additionalKM;
        this.customChoices = customChoices;
        this.isSigned = isSigned;
        this.deliveryLocationName = deliveryLocationName;
        this.deliveryLocationAddress = deliveryLocationAddress;
        this.deliveryLocationZipcode = deliveryLocationZipcode;
        this.deliveryLocationCity = deliveryLocationCity;
        this.deliveryLocationCountry = deliveryLocationCountry;
        this.returnLocationName = returnLocationName;
        this.returnLocationAddress = returnLocationAddress;
        this.returnLocationZipcode = returnLocationZipcode;
        this.returnLocationCity = returnLocationCity;
        this.returnLocationCountry = returnLocationCountry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRenterID() {
        return renterID;
    }

    public void setRenterID(int renterID) {
        this.renterID = renterID;
    }

    public int getCarVehicleNumber() {
        return carVehicleNumber;
    }

    public void setCarVehicleNumber(int carVehicleNumber) {
        this.carVehicleNumber = carVehicleNumber;
    }

    public int getReturnLocationId() {
        return returnLocationId;
    }

    public void setReturnLocationId(int returnLocationId) {
        this.returnLocationId = returnLocationId;
    }
    public int getDeliveryLocationId() {
        return deliveryLocationId;
    }

    public void setDeliveryLocationId(int deliveryLocationId) {
        this.deliveryLocationId = deliveryLocationId;
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

    public Car getRentalCar() {
        return rentalCar;
    }

    public void setRentalCar(Car rentalCar) {
        this.rentalCar = rentalCar;
    }

    public Renter getRentalRenter() {
        return rentalRenter;
    }

    public void setRentalRenter(Renter rentalRenter) {
        this.rentalRenter = rentalRenter;
    }

    public String getDeliveryLocationName() {
        return deliveryLocationName;
    }

    public void setDeliveryLocationName(String deliveryLocationName) {
        this.deliveryLocationName = deliveryLocationName;
    }

    public String getDeliveryLocationAddress() {
        return deliveryLocationAddress;
    }

    public void setDeliveryLocationAddress(String deliveryLocationAddress) {
        this.deliveryLocationAddress = deliveryLocationAddress;
    }

    public String getDeliveryLocationZipcode() {
        return deliveryLocationZipcode;
    }

    public void setDeliveryLocationZipcode(String deliveryLocationZipcode) {
        this.deliveryLocationZipcode = deliveryLocationZipcode;
    }

    public String getDeliveryLocationCity() {
        return deliveryLocationCity;
    }

    public void setDeliveryLocationCity(String deliveryLocationCity) {
        this.deliveryLocationCity = deliveryLocationCity;
    }

    public String getDeliveryLocationCountry() {
        return deliveryLocationCountry;
    }

    public void setDeliveryLocationCountry(String deliveryLocationCountry) {
        this.deliveryLocationCountry = deliveryLocationCountry;
    }

    public String getReturnLocationName() {
        return returnLocationName;
    }

    public void setReturnLocationName(String returnLocationName) {
        this.returnLocationName = returnLocationName;
    }

    public String getReturnLocationAddress() {
        return returnLocationAddress;
    }

    public void setReturnLocationAddress(String returnLocationAddress) {
        this.returnLocationAddress = returnLocationAddress;
    }

    public String getReturnLocationZipcode() {
        return returnLocationZipcode;
    }

    public void setReturnLocationZipcode(String returnLocationZipcode) {
        this.returnLocationZipcode = returnLocationZipcode;
    }

    public String getReturnLocationCity() {
        return returnLocationCity;
    }

    public void setReturnLocationCity(String returnLocationCity) {
        this.returnLocationCity = returnLocationCity;
    }

    public String getReturnLocationCountry() {
        return returnLocationCountry;
    }

    public void setReturnLocationCountry(String returnLocationCountry) {
        this.returnLocationCountry = returnLocationCountry;
    }

    public DamageReport getDamageReport() {
        return damageReport;
    }

    public void setDamageReport(DamageReport damageReport) {
        this.damageReport = damageReport;
    }
}

