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


    public SalesContract() {}

    public SalesContract(int contractId, int buyerId, int vehicleNumber, LocalDate saleDate,
                         String deliveryAddress, double salePrice, int totalKilometers, String options, Boolean rochsTransport) {
        this.contractId = contractId;
        this.buyerId = buyerId;
        this.vehicleNumber = vehicleNumber;
        this.saleDate = saleDate;
        this.deliveryAddress = deliveryAddress;
        this.salePrice = salePrice;
        this.totalKilometers = totalKilometers;
        this.options = options;
        this.rochsTransport = rochsTransport;
    }

    // Getters and Setters
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
}
