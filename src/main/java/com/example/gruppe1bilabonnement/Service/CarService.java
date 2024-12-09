package com.example.gruppe1bilabonnement.Service;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Repository.CarRepo;
import com.example.gruppe1bilabonnement.Repository.RentalContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepo carRepo;

    @Autowired
    RentalContractRepo rentalContractRepo;

    public List<Car> fetchAllWithReturnDate(){
        return carRepo.fetchAllWithReturnDate();
    }

    public List<Car> fetchAllWithReturnDate(String regNumber){
        return carRepo.fetchAllWithReturnDate(regNumber);
    }

    public RentalContract fetchRentalContractDetails(String regNumber){

        System.out.println(rentalContractRepo.fetchRentalContractDetails(regNumber));

        return rentalContractRepo.fetchRentalContractDetails(regNumber);
    }

}
