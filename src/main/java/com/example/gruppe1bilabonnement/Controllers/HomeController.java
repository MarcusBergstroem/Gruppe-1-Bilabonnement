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

    @GetMapping("/opret_bil")
    public String createCar() {
        return "home/opret_bil"; // This should point to your HTML form for creating a rental contract
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
    public String createRentalContract() {
        return "home/opret_lejekontrakt"; // This should point to your HTML form for creating a rental contract
    }

    @GetMapping("/opret_lejer")
    public String createRenter() {
        return "home/opret_lejer"; // This should point to your HTML form for creating a rental contract
    }

    @PostMapping("/opretlejer")
    public String createRenter(@ModelAttribute Renter r){
        System.out.println("Renter received: " + r);
        return "redirect:/";
    }

    @GetMapping("/lejedetaljer")
    public String rentalDetails(Model model, @RequestParam String regNumber) {
        model.addAttribute("rentalContractDetails", carService.fetchRentalContractDetails(regNumber));
        return "home/lejedetaljer";
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
