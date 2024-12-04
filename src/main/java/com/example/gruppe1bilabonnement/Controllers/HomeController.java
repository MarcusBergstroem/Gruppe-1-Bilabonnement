package com.example.gruppe1bilabonnement.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


    @GetMapping("/createVirksomhed")
    public String createVirksomhed(){
        return "home/index";
    }
}
