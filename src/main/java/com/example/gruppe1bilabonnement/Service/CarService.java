package com.example.gruppe1bilabonnement.Service;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.Renter;
import com.example.gruppe1bilabonnement.Repository.CarRepo;
import com.example.gruppe1bilabonnement.Repository.RenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepo carRepo;
    @Autowired
    RenterRepo renterRepo;

    public List<Car> fetchAllWithReturnDate(){
        return carRepo.fetchAllWithReturnDate();
    }

    public List<Car> fetchAllWithReturnDate(String regNumber){
        return carRepo.fetchAllWithReturnDate(regNumber);
    }
    public void addCar(Car c){
        carRepo.addCar(c);
    }
    public void addRenter(Renter r){
        int geography = renterRepo.addGeography(r.getCountry(), r.getCity(), r.getZipCode());
    }
}