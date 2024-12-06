package com.example.gruppe1bilabonnement.Controllers;

import com.example.gruppe1bilabonnement.Service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        //model.addAttribute("madvarer", carService.fetchAll());
        System.out.println("Vigtig data");
        System.out.println("Hej med dig 1000");
        System.out.println("Hej med dig 2000");
        System.out.println("Hej med dig 3000");
        System.out.println("Hej med dig VR");
        return "home/index";
    }

    //KAN DU SET DETTE?

    @GetMapping("/udlejede_biler")
    public String udlejedeBiler(Model model, @RequestParam Map<String, String> regNumber ) {

        if (regNumber.containsKey("regNumber")) {
            model.addAttribute("cars", carService.fetchAllWithReturnDate(regNumber.get("regNumber")));
            return "home/udlejede_biler";
        }
        model.addAttribute("cars", carService.fetchAllWithReturnDate());
        return "home/udlejede_biler";
    }

    @GetMapping("/createVirksomhed")
    public String createVirksomhed() {
        return "home/index";
    }

    @GetMapping("/Create_rental_contract")
    public String Create_rental_contract() {
        return "home/Create_rental_contract"; // This should point to your HTML form for creating a rental contract
    }
}

