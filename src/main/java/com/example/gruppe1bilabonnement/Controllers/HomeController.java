package com.example.gruppe1bilabonnement.Controllers;

import com.example.gruppe1bilabonnement.Model.*;
import com.example.gruppe1bilabonnement.Repository.RentalContractRepo;
import com.example.gruppe1bilabonnement.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private RentalContractRepo rentalContractRepo;
    private final CarService carService;

    public HomeController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "home/index";
    }
    @GetMapping("/bilsalg")
    public String car_sale(Model model){
        return "home/bilsalg";
    }

    @GetMapping("/opret_koeber")
    public String createBuyer(Model model){
        return "home/opret_koeber";
    }

    @PostMapping("/opret_koeber")
    public String addBuyer(Model model, @ModelAttribute Buyer b) {
        try {
            // Pass the Buyer object to the service layer
            carService.addBuyer(b);
            model.addAttribute("message", "Operation fuldført. Køber blev oprettet!");
            model.addAttribute("alertType", "success");
        }catch (Exception e) {
            model.addAttribute("message", "Operation fejlet. Intet blev oprettet!");
            model.addAttribute("alertType", "error");
        }
        return "home/index"; // Redirect to the home page or another relevant page
    }

    @GetMapping("/opret_salgsaftale")
    public String createSalesContract(Model model){

        List<Buyer> buyers = carService.fetchAllBuyers();
        List<Car> carsAtStorage = carService.fetchAllCarsLeasedOrAtStorage();

        model.addAttribute("buyers", buyers);
        model.addAttribute("cars", carsAtStorage);
        return "home/opret_salgsaftale";
    }
    @PostMapping("/opret_salgsaftale")
    public String saveSalesContract(Model model, @ModelAttribute SalesContract salesContract) {

        try {
            //tilføjer kontrakt og ændrer status til 'leased'
            carService.addSalesContract(salesContract);
            model.addAttribute("message", "Operation fuldført. Salgsaftale blev oprettet!");
            model.addAttribute("alertType", "success");
        } catch (Exception e) {
            model.addAttribute("message", "Operation fejlet. Intet blev oprettet!");
            model.addAttribute("alertType", "error");

        }
        return "home/index";
    }

    @GetMapping("/alle_salgsaftaler")
    public String alleSalgsaftaler(Model model, @RequestParam Map<String, String> vin) {


        //Checker om request har et VIN parameter
        if (vin.containsKey("vin")) {
            model.addAttribute("salesContracts", carService.searchSalesContracts(vin.get("vin")));
            return "home/alle_salgsaftaler";
        }

        // fetch alle salgsaftaler
        model.addAttribute("salesContracts", carService.fetchAllSalesContracts());
        return "home/alle_salgsaftaler";
    }

    @GetMapping("/salg_og_udlejning")
    public String sales_and_rentals(Model model) {
        return "home/salg_og_udlejning";
    }

    @GetMapping("/opret_bil")
    public String createCar() {
        return "home/opret_bil";
    }

    @PostMapping("/opret_bil")
    public String createCar(Model model, @ModelAttribute Car C) {
        try{
            carService.addCar(C);
            model.addAttribute("message", "Operation fuldført. Bil blev oprettet!");
            model.addAttribute("alertType", "success");
        } catch (Exception e) {
            model.addAttribute("message", "Operation fejlet. Intet blev oprettet!");
            model.addAttribute("alertType", "error");
        }
        return "home/index";
    }

    @GetMapping("/udlejede_biler")
    public String udlejedeBiler(Model model, @RequestParam Map<String, String> regNumber) {

        if (regNumber.containsKey("regNumber")) {
            model.addAttribute("rentalContracts", carService.searchRentalContracts(regNumber.get("regNumber")));
            return "home/udlejede_biler";
        }
        model.addAttribute("rentalContracts", carService.fetchAllRentalContracts());
        return "home/udlejede_biler";
    }

    //Laver en en lejekontrakt vja.
    @GetMapping("/opret_lejekontrakt")
    public String createRentalContractForm(Model model) {

        List<Car> availableCars = carService.fetchAllAvailableCars();
        List<Renter> renters = carService.fetchAllRenters();
        List<Map<String, Object>> locations = carService.getAllLocations();

        model.addAttribute("cars", availableCars);
        model.addAttribute("renters", renters);
        model.addAttribute("locations", locations);


        return "home/opret_lejekontrakt";
    }

    @PostMapping("/opret_lejekontrakt")
    public String saveRentalContract(Model model, @ModelAttribute RentalContract rentalContract) {
        try {
            System.out.println("DeliveryLocationID: " + rentalContract.getDeliveryLocationId());
            System.out.println("ReturnLocationID: " + rentalContract.getReturnLocationId());
            //tilføjer kontrakt og ændrer status til 'leased'
            carService.addRentalContract(rentalContract);
            model.addAttribute("message", "Operation fuldført. Lejekontrakt blev oprettet!");
            model.addAttribute("alertType", "success");
        } catch (Exception e) {
            model.addAttribute("message", "Operation fejlet. Intet blev oprettet!");
            model.addAttribute("alertType", "error");

        }
        return "home/index";
    }

    @GetMapping("/opret_lejer")
    public String createRenter() {
        return "home/opret_lejer";
    }

    @PostMapping("/opret_lejer")
    public String createRenter(Model model, @ModelAttribute Renter r) {
        try {
            System.out.println("Renter received: " + r);
            carService.addRenter(r);
            model.addAttribute("message", "Operation fuldført. Lejer blev oprettet!");
            model.addAttribute("alertType", "success");
        } catch (Exception e) {
            model.addAttribute("message", "Operation fejlet. Intet blev oprettet!");
            model.addAttribute("alertType", "error");
        }
        return "home/index";
    }

    @GetMapping("/statistik")
    public String statistik(Model model) {
        return "home/statistik";
    }

    @GetMapping("/vis_alle_biler")
    public String listAllCars(Model model) {
        model.addAttribute("allCarDetails", carService.fetchAllCarDetails());
        return "home/vis_alle_biler";
    }

    @GetMapping("/vis_alle_lejekontrakter")
    public String listAllContracts(Model model) {
        model.addAttribute("allContractDetails", carService.fetchAllContractDetails());
        model.addAttribute("rentedCarsThisMonthRevenue", carService.rentedCarsThisMonthRevenue());
        return "home/vis_alle_lejekontrakter";
    }

    @GetMapping("/vis_alle_lejere")
    public String listAllRenters(Model model) {
        model.addAttribute("allRenterDetails", carService.fetchAllRenterDetails());
        return "home/vis_alle_lejere";
    }

    @GetMapping("/omsaetning_aar_til_dato")
    public String revenueYearToDate(Model model) {
        model.addAttribute("revenueYearToDate", carService.revenueYearToDate());
        return "home/omsaetning_aar_til_dato";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("soldCarsDates", carService.soldCarsDates());
        model.addAttribute("avgSalesTime", carService.avgSalesTime());
        return "home/dashboard";
    }

    @GetMapping("/skadeshåndtering")
    public String damageReportOverview(Model model, @RequestParam Map<String, String> regNumber) {

        if (regNumber.containsKey("regNumber")) {
            model.addAttribute("rentalContractsLeased", carService.searchAllLeasedCars(regNumber.get("regNumber")));
            model.addAttribute("rentalContractsWithDamageReport", carService.searchAllContracts_With_DamageReport(regNumber.get("regNumber")));
            return "home/skadeshåndtering";
        }
        model.addAttribute("rentalContractsLeased", carService.fetchAllLeasedCars());
        model.addAttribute("rentalContractsWithDamageReport", carService.fetchAllContracts_With_DamageReport());
        return "home/skadeshåndtering";
    }

    @GetMapping("opret_skadejournal")
    public String createDamageReport(Model model, @RequestParam String vehicleNumber) {
        model.addAttribute("vehicleNumber", vehicleNumber);
        return "home/opret_skadejournal";
    }

    @GetMapping("vis_alle_salgskontrakter")
    public String showAllSalesContracts(Model model) {
        // anvende samme metode som sælgeren allerede har i sit view
        model.addAttribute("salesContracts", carService.fetchAllSalesContracts());
        return "home/vis_alle_salgskontrakter";
    }

    @PostMapping("opret_skadejournal")
    public String createDamageReport(@RequestParam Map<String, String> formData) throws UnsupportedEncodingException {
        carService.addDamageReport(formData);
        return "redirect:/" + URLEncoder.encode("skadeshåndtering", "UTF-8");
    }

    @GetMapping("skadejournal_detaljer")
    public String damageReportOverview(Model model, @RequestParam int vehicleNumber) {
        model.addAttribute("damageReport", carService.fetchDamageReport(vehicleNumber));
        model.addAttribute("totalPrice", carService.getTotalPrice(vehicleNumber));
        model.addAttribute("mileage", carService.fetchMileage(vehicleNumber));
        return "home/skadejournal_detaljer";
    }

    @PostMapping("skadejournal_detaljer")
    public String deleteDamage(@RequestParam int id, int vehicleNumber) {
        carService.deleteDamage(id);
        return "redirect:/skadejournal_detaljer?vehicleNumber=" + vehicleNumber;
    }

    @GetMapping("/lejedetaljer")
    public String showRentalDetails(@RequestParam("regNumber") String regNumber, Model model) {
        RentalContract rentalContract = rentalContractRepo.findByRegistrationNumber(regNumber);
        if (rentalContract == null) {
            return "errorPage";
        }

        Double totalDamagePrice = rentalContractRepo.getTotalDamagePrice(rentalContract.getCarVehicleNumber());
        if (totalDamagePrice == null) {
            totalDamagePrice = 0.0;
        }

        Double additionalKMPrice = rentalContractRepo.calculateExtraKMPrice(rentalContract.getCarVehicleNumber());
        if (additionalKMPrice == null) {
            additionalKMPrice = 0.0;
        }

        Double totalPrice = totalDamagePrice + additionalKMPrice;

        model.addAttribute("rentalContractDetails", carService.fetchRentalContractDetails(regNumber));
        model.addAttribute("totalDamagePrice", totalDamagePrice);
        model.addAttribute("additionalKMPrice", additionalKMPrice);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("hasJournal", carService.hasJournal(regNumber));

        return "home/lejedetaljer";
    }

}