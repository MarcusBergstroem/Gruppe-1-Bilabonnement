package com.example.gruppe1bilabonnement.Service;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.Renter;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Repository.CarRepo;
import com.example.gruppe1bilabonnement.Repository.RenterRepo;
import com.example.gruppe1bilabonnement.Repository.StatsRepo;
import com.example.gruppe1bilabonnement.Repository.RentalContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CarService {

    @Autowired
    CarRepo carRepo;
    @Autowired
    RenterRepo renterRepo;
    @Autowired
    RentalContractRepo rentalContractRepo;
    @Autowired
    StatsRepo statsRepo;

    public List<RentalContract> fetchAllRentalContracts() {
        return rentalContractRepo.fetchAll();
    }

    public void addCar(Car c){
        carRepo.addCar(c);
    }

    //Henter alle biler hvis status er "Available"
    public List<Car> fetchAllAvailableCars() {
        return carRepo.fetchAllAvailableCars();
    }
    public List<Map<String, Object>> getAllLocations() {
        return rentalContractRepo.fetchAllLocations();
    }

    public RentalContract fetchRentalContractDetails(String regNumber){
        return rentalContractRepo.fetchRentalContractDetails(regNumber);
    }

    public List<RentalContract> searchRentalContracts(String regNumber){
        return rentalContractRepo.searchRentalContracts(regNumber);
    }
    //addRenter laver en unik geografiNøgle og efterfølgende gemmer den resten til geografitabellen.
    //Efterfølgende tilføjer den til rentertabellen.
    public void addRenter(Renter r){
        int geographyId = renterRepo.addGeography(r.getCountry(), r.getCity(), r.getZipCode());
        renterRepo.addRenter(r, geographyId);
    }
    //Henter alle renters
    public List<Renter> fetchAllRenters() {
        return renterRepo.fetchAllRenters();
    }
    // Tilføjer ny kontrakt og ændrer bilens status til "Leased"
    public void addRentalContract(RentalContract rentalContract) {
        rentalContractRepo.addRentalContract(rentalContract);
    }

    // Til statistik: henter alle car details
    public List<Car> fetchAllCarDetails(){
        return statsRepo.fetchAllCarDetails();
    }

    // Til statistik: henter alle car details
    public List<RentalContract> fetchAllContractDetails(){
        return statsRepo.fetchAllContractDetails();
    }

}