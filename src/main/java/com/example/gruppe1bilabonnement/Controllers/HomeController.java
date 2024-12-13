package com.example.gruppe1bilabonnement.Controllers;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Model.Renter;
import com.example.gruppe1bilabonnement.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final CarService carService;

    public HomeController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public String index(Model model) {
        //model.addAttribute("madvarer", carService.fetchAll())
        return "home/index";
    }

    @GetMapping("/salg_og_udlejning")
    public String sales_and_rentals(Model model) {
        //model.addAttribute("madvarer", carService.fetchAll())
        return "home/salg_og_udlejning";
    }

    @GetMapping("/opret_bil")
    public String createCar() {
        return "home/opret_bil";
    }
    @PostMapping("/opret_bil")
    public String createCar(@ModelAttribute Car C){
        carService.addCar(C);
        return "redirect:/";
    }

    @GetMapping("/udlejede_biler")
    public String udlejedeBiler(Model model, @RequestParam Map<String, String> regNumber ) {

        if (regNumber.containsKey("regNumber")) {
            model.addAttribute("rentalContracts", carService.searchRentalContracts(regNumber.get("regNumber")));
            return "home/udlejede_biler";
        }
        model.addAttribute("rentalContracts", carService.fetchAllRentalContracts());
        return "home/udlejede_biler";
    }

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
    public String saveRentalContract(@ModelAttribute RentalContract rentalContract) {
        System.out.println("DeliveryLocationID: " + rentalContract.getDeliveryLocationId());
        System.out.println("ReturnLocationID: " + rentalContract.getReturnLocationId());
        //tilføjer kontrakt og ændrer status til 'leased'
        carService.addRentalContract(rentalContract);

        return "redirect:/";
    }

    @GetMapping("/opret_lejer")
    public String createRenter() {
        return "home/opret_lejer";
    }

    @PostMapping("/opret_lejer")
    public String createRenter(@ModelAttribute Renter r){
        System.out.println("Renter received: " + r);
        carService.addRenter(r);
        return "redirect:/";
    }

    @GetMapping("/lejedetaljer")
    public String rentalDetails(Model model, @RequestParam String regNumber) {
        model.addAttribute("rentalContractDetails", carService.fetchRentalContractDetails(regNumber));
        return "home/lejedetaljer";
    }

    @GetMapping("/statistik")
    public String statistik(Model model){
        return "home/statistik";
    }

    @GetMapping("/vis_alle_biler")
    public String listAllCars(Model model){
        model.addAttribute("allCarDetails", carService.fetchAllCarDetails());
        return "home/vis_alle_biler";
    }

    @GetMapping("/vis_alle_lejekontrakter")
    public String listAllContracts(Model model){
        model.addAttribute("allContractDetails", carService.fetchAllContractDetails());
        model.addAttribute("rentedCarsThisMonthRevenue", carService.rentedCarsThisMonthRevenue());
        return "home/vis_alle_lejekontrakter";
    }

    @GetMapping("/vis_alle_lejere")
    public String listAllRenters(Model model){
        model.addAttribute("allRenterDetails", carService.fetchAllRenterDetails());
        return "home/vis_alle_lejere";
    }

    @GetMapping("/omsaetning_aar_til_dato")
    public String revenueYearToDate(Model model){
        model.addAttribute("revenueYearToDate", carService.revenueYearToDate());
        return "home/omsaetning_aar_til_dato";
    }



//    @PostMapping
//    public String createCar(@ModelAttribute Car C) {
//        //carService.addCar(C);
//        return "redirect:/";
//    }

//    @PostMapping
//    public String createRentalContract(@ModelAttribute RentalContract R) {
//        //carService.addRentalContract(R);
//        return "redirect:/";
//    }

//    @PostMapping
//    public String createRenter(@ModelAttribute Renter R) {
//        //carService.addRenter(R);
//        return "redirect:/";
//    }
}
