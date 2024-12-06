package com.example.gruppe1bilabonnement.Model;

import java.time.LocalDate;

public class RentalContract {
    private int id;
    private int renterCpr;
    private int carVehicleNumber;
    private int deliveryReturnLocationId;
    private LocalDate deliveryDate;
    private LocalDate returnDate;
    private double initialPayment;
    private double monthlyPayment;
    private int totalKilometers;
    private double additionalKM;
    private String customChoices;
    private boolean isSigned;

    // Constructor
    public RentalContract(int id, int renterCpr, int carVehicleNumber, int deliveryReturnLocationId,
                          LocalDate deliveryDate, LocalDate returnDate, double initialPayment,
                          double monthlyPayment, int totalKilometers, double additionalKM,
                          String customChoices, boolean isSigned) {
        this.id = id;
        this.renterCpr = renterCpr;
        this.carVehicleNumber = carVehicleNumber;
        this.deliveryReturnLocationId = deliveryReturnLocationId;
        this.deliveryDate = deliveryDate;
        this.returnDate = returnDate;
        this.initialPayment = initialPayment;
        this.monthlyPayment = monthlyPayment;
        this.totalKilometers = totalKilometers;
        this.additionalKM = additionalKM;
        this.customChoices = customChoices;
        this.isSigned = isSigned;
    }

    // Getters and Setters
    // (Indsæt getters og setters her)
}

