package com.example.gruppe1bilabonnement.Model;

public class Damage {

    private String damageDescription;
    private String classification;
    private String price;

    public Damage(String damageDescription, String classification, String price) {
        this.damageDescription = damageDescription;
        this.classification = classification;
        this.price = price;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
