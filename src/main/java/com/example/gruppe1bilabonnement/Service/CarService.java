package com.example.gruppe1bilabonnement.Service;

import com.example.gruppe1bilabonnement.Model.*;
import com.example.gruppe1bilabonnement.Repository.*;
import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.DamageReport;
import com.example.gruppe1bilabonnement.Model.Renter;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Repository.CarRepo;
import com.example.gruppe1bilabonnement.Repository.DamageReportRepo;
import com.example.gruppe1bilabonnement.Repository.RenterRepo;
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
    @Autowired
    BuyerRepo buyerRepo;
    @Autowired
    SalesContractRepo salesContractRepo;
    @Autowired
    DamageReportRepo damageReportRepo;

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
    public void addBuyer(Buyer b) {
        // Modtager geography ID
        int geoId = buyerRepo.addBuyerGeo(b.getCountry(), b.getCity(), b.getZipcode(), b.getAddress());

        // indsætter køberen med et bestemt geoID
        buyerRepo.addBuyer(b, geoId);
    }
    //addRenter laver en unik geografiNøgle og efterfølgende gemmer den resten til geografitabellen.
    //Efterfølgende tilføjer den til renter tabellen.
    public void addRenter(Renter r){
        int geographyId = renterRepo.addGeography(r.getCountry(), r.getCity(), r.getZipCode(), r.getAddress());
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

    // Til statistik: henter alle rentalcontract details
    public List<RentalContract> fetchAllContractDetails(){
        return statsRepo.fetchAllContractDetails();
    }
    public List<Car> fetchAllCarsLeasedOrAtStorage() {
        return carRepo.fetchAllCarsLeasedOrAtStorage();
    }
    public List<Buyer> fetchAllBuyers() {
        return buyerRepo.fetchAllBuyers();
    }
    public void addSalesContract(SalesContract salesContract) {
        salesContractRepo.addSalesContract(salesContract);
    }
    public List<SalesContract> fetchAllSalesContracts() {
        return salesContractRepo.fetchSalesContracts();
    }

    public List<SalesContract> searchSalesContracts(String vin) {
        return salesContractRepo.searchSalesContracts(vin);
    }


    // Til statistik: finder omsætning fra udlejde biler i indeværende måned
    public double rentedCarsThisMonthRevenue(){
        return statsRepo.rentedCarsThisMonthRevenue();
    }

    // Til statistik: henter alle renter details
    public List<Renter> fetchAllRenterDetails(){
        return statsRepo.fetchAllRenterDetails();
    }

    // Til statistik: finder revenue year to date fordelt på måneder og hhv. førstegangs- og månedlig ydelse
    public List<List<Double>> revenueYearToDate() {
        return statsRepo.revenueYearToDate();
    }

    public void addDamageReport(Map<String, String> formData){
        damageReportRepo.addDamageReport(damageReportRepo.assembleDamageReport(formData));
    }

    public List<RentalContract> fetchAllCarsWithoutDamageReport() {
        return rentalContractRepo.fetchAllCarsWithoutDamageReport();
    }

    public DamageReport fetchDamageReport(int vehicleNumber){
        return damageReportRepo.fetchDamageReport(vehicleNumber);
    }

    public void deleteDamage(int id){
        damageReportRepo.deleteDamage(id);
    }

    public Double getTotalPrice(int vehicleNumber){
        return damageReportRepo.getTotalPrice(vehicleNumber);
    }

    public int fetchMileage(int vehicleNumber){
        return carRepo.fetchMileage(vehicleNumber);
    }

    public List<List<String>> soldCarsDates(){
        return statsRepo.soldCarsDates();
    }

    // Til statistik: finder gennemsnitlig salgstid for solgte biler
    public double avgSalesTime(){
        return statsRepo.avgSalesTime();
    }

    public List<RentalContract> fetchAllContracts_With_DamageReport(){
        return rentalContractRepo.fetchAllContractsWithDamageReport();
    }

    public Boolean hasJournal(String regNumber){
        return rentalContractRepo.hasJournal(regNumber);
    }

    public List<RentalContract> searchAllCarsWithoutDamageReport(String regNumber){
        return rentalContractRepo.searchAllCarsWithoutDamageReport(regNumber);
    }
    public List<RentalContract> searchAllContracts_With_DamageReport(String regNubmer){
        return rentalContractRepo.searchAllContractsWithDamageReport(regNubmer);
    }
}