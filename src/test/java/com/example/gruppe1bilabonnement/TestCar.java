package com.example.gruppe1bilabonnement;

import com.example.gruppe1bilabonnement.Model.Car;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCar {
    @Test
    void testGetBrand() {
        Car car = new Car(10, 3, "Toyota", "Corolla", "Luxury", "1029384", "HS05934", "01-04-2025", "available");
        String brandName = car.getCarBrand();
        assertEquals("Toyota", brandName);
    }
}
