package com.example.gruppe1bilabonnement.Controllers;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Model.Renter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        //model.addAttribute("madvarer", carService.fetchAll());
        System.out.println("Vigtig data");
        System.out.println("Hej med dig 1000");
        System.out.println("Hej med dig 2000");
        System.out.println("Hej med dig 3000");
        System.out.println("Hej med dig VR");
        return "home/index";
    }

    //KAN DU SET DETTE?

    @GetMapping("/opret_lejekontrakt")
    public String createRentalContract() {
        return "home/opret_lejekontrakt";
    }
    @PostMapping
    public String createRentalContract(@ModelAttribute RentalContract R){
        carService.addRentalContract(R);
        return "redirect:/";
    }
    @GetMapping("/opret_lejer")
    public String createRenter() {
        return "home/opret_lejer";
    }
    @PostMapping
    public String createRenter(@ModelAttribute Renter R) {
        carService.addRenter(R);
        return "redirect:/";
    }
    @GetMapping("/opret_bil")
    public String createCar() {
        return "home/opret_bil";
    }
    @PostMapping
    public String createCar(@ModelAttribute Car C) {
        carService.addCar(C);
        return "redirect:/";
    }
}
