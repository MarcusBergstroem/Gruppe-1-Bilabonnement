package com.example.gruppe1bilabonnement.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.service.annotation.GetExchange;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        //model.addAttribute("madvarer", carService.fetchAll());
        System.out.println("soerenbanjomus");
        return "home/index";
    }

    //KAN DU SET DETTE?

    @GetMapping("/createBanjomus")
    public String createVirksomhed(){
        return "home/index";
    }
}
