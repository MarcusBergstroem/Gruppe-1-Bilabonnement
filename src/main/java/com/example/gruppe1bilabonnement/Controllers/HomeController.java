package com.example.gruppe1bilabonnement.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        //model.addAttribute("madvarer", carService.fetchAll());
        return "home/sale_and_rental";
    }

    @GetMapping("/Create_rental_contract")
    public String Create_rental_contract() {
        return "home/Create_rental_contract"; // This should point to your HTML form for creating a rental contract
    }

}

