package com.example.gruppe1bilabonnement.Service;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.Renter;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Repository.CarRepo;
import com.example.gruppe1bilabonnement.Repository.RenterRepo;
import com.example.gruppe1bilabonnement.Repository.RentalContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepo carRepo;
    @Autowired
    RenterRepo renterRepo;
    @Autowired
    RentalContractRepo rentalContractRepo;

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


    public RentalContract fetchRentalContractDetails(String regNumber){
        return rentalContractRepo.fetchRentalContractDetails(regNumber);
    }

    public List<RentalContract> searchRentalContracts(String regNumber){
        return rentalContractRepo.searchRentalContracts(regNumber);
    }

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

}