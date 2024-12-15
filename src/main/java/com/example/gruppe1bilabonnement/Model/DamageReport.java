package com.example.gruppe1bilabonnement.Model;


import java.util.ArrayList;
import java.util.List;

public class DamageReport {

    //Måske unødvendig
    private int carVehicleNumber;
    private int id;

    private List<Damage> damageReportList;

    //Default Constructor
    public DamageReport(){
        damageReportList = new ArrayList<>();
    }

    public void addDamageToList(Damage damage){
        damageReportList.add(damage);
    }

    public List<Damage> getDamageReportList() {
        return damageReportList;
    }

    public void setDamageReportList(List<Damage> damageReportList) {
        this.damageReportList = damageReportList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarVehicleNumber() {
        return carVehicleNumber;
    }

    public void setCarVehicleNumber(int carVehicleNumber) {
        this.carVehicleNumber = carVehicleNumber;
    }
}
