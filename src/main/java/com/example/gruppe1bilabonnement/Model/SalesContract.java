package com.example.gruppe1bilabonnement.Model;

import java.time.LocalDate;

public class SalesContract {
    private int contractId;
    private int buyerId;
    private int vehicleNumber;
    private LocalDate saleDate;
    private String deliveryAddress;
    private double salePrice;
    private int totalKilometers;
    private String options;
    private Boolean rochsTransport;

    private Buyer buyer;
    private Car Car;

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    private String buyerName;
    private String carBrand;
    private String carModel;
    private String vin;

    public SalesContract() {}

    public SalesContract(int contractId, int buyerId, int vehicleNumber, LocalDate saleDate,
                         String deliveryAddress, double salePrice, int totalKilometers, String options, Boolean rochsTransport, String buyerName, String carBrand, String carModel, String vin) {
        this.contractId = contractId;
        this.buyerId = buyerId;
        this.vehicleNumber = vehicleNumber;
        this.saleDate = saleDate;
        this.deliveryAddress = deliveryAddress;
        this.salePrice = salePrice;
        this.totalKilometers = totalKilometers;
        this.options = options;
        this.rochsTransport = rochsTransport;
        this.buyerName = buyerName;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.vin = vin;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getTotalKilometers() {
        return totalKilometers;
    }

    public void setTotalKilometers(int totalKilometers) {
        this.totalKilometers = totalKilometers;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
    public Boolean getRochsTransport() {
        return rochsTransport;
    }

    public void setRochsTransport(Boolean rochsTransport) {
        this.rochsTransport = rochsTransport;
    }
    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Car getCar() {
        return Car;
    }

    public void setCar(Car car) {
        Car = car;
    }


    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
