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

    public RentalContract fetchRentalContractDetails(String regNumber){
        return rentalContractRepo.fetchRentalContractDetails(regNumber);
    }

    public List<RentalContract> searchRentalContracts(String regNumber){
        return rentalContractRepo.searchRentalContracts(regNumber);
    }

}
