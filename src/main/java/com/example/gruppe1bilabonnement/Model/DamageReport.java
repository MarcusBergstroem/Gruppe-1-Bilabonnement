package com.example.gruppe1bilabonnement.Model;


import java.util.ArrayList;
import java.util.List;

public class DamageReport {

    //Måske unødvendig
    private int carVehicleNumber;
    private int id;

    private List<Damage> damageList;

    //Default Constructor
    public DamageReport(){
        damageList = new ArrayList<>();
    }

    public DamageReport(int carVehicleNumber, List<Damage> damageList){
        this.carVehicleNumber = carVehicleNumber;
        this.damageList = damageList;
    }

    public void addDamageToList(Damage damage){
        damageList.add(damage);
    }

    public List<Damage> getDamageList() {
        return damageList;
    }

    public void setDamageList(List<Damage> damageList) {
        this.damageList = damageList;
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
