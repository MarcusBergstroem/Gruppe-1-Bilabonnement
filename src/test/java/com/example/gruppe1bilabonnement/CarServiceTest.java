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
    private CarRepo carRepo;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAllAvailableCars() {

        List<Car> mockCars = List.of(
                new Car(1, 123, "Toyota", "Corolla", "Basic", "VIN123", "2024-12-31", "available"),
                new Car(2, 456, "Ford", "Focus", "Luxury", "VIN456", "2024-10-10", "available")
        );


        when(carRepo.fetchAllAvailableCars()).thenReturn(mockCars);


        List<Car> result = carService.fetchAllAvailableCars();


        assertEquals(2, result.size(), "Der burde være 2 biler i listen");
        assertEquals("Toyota", result.get(0).getCarBrand(), "Den første bil burde være Toyota");


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

