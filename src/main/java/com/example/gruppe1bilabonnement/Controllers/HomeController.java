package com.example.gruppe1bilabonnement.Controllers;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Model.Renter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        //model.addAttribute("madvarer", carService.fetchAll())
        return "home/index";
    }

    @GetMapping("/opret_bil")
    public String createCar() {
        return "home/opret_bil"; // This should point to your HTML form for creating a rental contract
    }

    @GetMapping("/opret_lejekontrakt")
    public String createRentalContract() {
        return "home/opret_lejekontrakt"; // This should point to your HTML form for creating a rental contract
    }

    @GetMapping("/opret_lejer")
    public String createRenter() {
        return "home/opret_lejer"; // This should point to your HTML form for creating a rental contract
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
