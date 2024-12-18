package com.example.gruppe1bilabonnement;

import com.example.gruppe1bilabonnement.Model.Car;
import com.example.gruppe1bilabonnement.Repository.CarRepo;
import com.example.gruppe1bilabonnement.Service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    // Annotationen viser at dette er en mock af CarRepo (en simuleret udgave)
    //bruges til at isolere testen og dermed undgå at den snakker med databassen
    private CarRepo carRepo;
    @InjectMocks
    // Injicerer den mockede CarRepo ind i CarService
    // Gør det muligt at teste CarService med den mockede version
    private CarService carService;
    @BeforeEach
        // Metoden kører før hver test og initialiserer alle mocks der er i testklassen
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testFetchAllAvailableCars() {
        // Tester fetchAllAvailableCars-metoden i CarService
        // Checker om at den returnerer korrekte biler og kalder repository korrekt
        List<Car> mockCars = List.of(
                //Den simulerede liste af biler fra databasen
                new Car(1, 123, "Toyota", "Corolla",
                        "Basic", "VIN123", "2024-12-31", "available"),
                new Car(2, 456, "Ford", "Focus", "Luxury",
                        "VIN456", "2024-10-10", "available"));
        // Stub opførsel: Når fetchAllAvailableCars() bliver kaldt, returneres den simulerede data (mocken) - mockCars
        when(carRepo.fetchAllAvailableCars()).thenReturn(mockCars);
        // Kald metoden vi tester
        List<Car> result = carService.fetchAllAvailableCars();
        // Checker om listen indeholder 2 biler
        assertEquals(2, result.size(), "Der burde være 2 biler i listen");
        //Checker om den første bil er en Toyota
        assertEquals("Toyota", result.get(0).getCarBrand(), "Den første bil burde være Toyota");
        //Checker om metoden bliver kørt kun en gang
        verify(carRepo, times(1)).fetchAllAvailableCars();
    }
    @Test
    void testAddCar(){
        Car testCar = new Car(1, 123, "Toyota", "Corolla",
                "Basic", "VIN123", "2024-12-31", "available");
        carService.addCar(testCar);
        verify(carRepo, times(1)).addCar(testCar);
    }
    @Test
    void testFetchAllCarsAtStorage() {

        List<Car> mockCars = List.of(
                new Car(1, 123, "Tesla", "Model S", "Luxury", "VIN001", "2024-12-31", "storage"),
                new Car(2, 456, "BMW", "X5", "Sport", "VIN002", "2025-01-10", "storage")
        );


        when(carRepo.fetchAllCarsLeasedOrAtStorage()).thenReturn(mockCars);


        List<Car> result = carService.fetchAllCarsLeasedOrAtStorage();


        assertEquals(2, result.size(), "Der burde være 2 biler i listen");
        assertEquals("Tesla", result.get(0).getCarBrand(), "Den første bil burde være en Tesla");
        assertEquals("Sport", result.get(1).getEquipmentLevel(), "Den anden bil burde være en BMW");


        verify(carRepo, times(1)).fetchAllCarsLeasedOrAtStorage();
    }

}

