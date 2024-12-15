package com.example.gruppe1bilabonnement.Controllers;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Model.DamageReport;
import com.example.gruppe1bilabonnement.Model.RentalContract;
import com.example.gruppe1bilabonnement.Model.Renter;
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

    @GetMapping("/skadeshåndtering")
    public String damageReportOverview(Model model){
        model.addAttribute("rentalContracts", carService.fetchAllSoldCars());
        return "home/skadeshåndtering";
    }

    @GetMapping("opret_skadejournal")
    public String createDamageReport(Model model, @RequestParam String vehicleNumber) {
        model.addAttribute("vehicleNumber", vehicleNumber);
        return "home/opret_skadejournal";
    }

    @PostMapping("opret_skadejournal")
    public String createDamageReport(@RequestParam Map<String, String> formData) throws UnsupportedEncodingException {
        carService.addDamageReport(formData);
        return "redirect:/" + URLEncoder.encode("skadeshåndtering", "UTF-8");
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
