package com.example.gruppe1bilabonnement.Model;

public class Buyer {
    private int buyerId;
    private String companyName;
    private String cvr;
    private String phoneNumber;
    private String address;
    private String city;
    private String zipcode;
    private String country;


    public Buyer() {}


    public Buyer(int buyerId, String companyName, String cvr, String phoneNumber,
                 String address, String city, String zipcode, String country) {
        this.buyerId = buyerId;
        this.companyName = companyName;
        this.cvr = cvr;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.country = country;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCvr() {
        return cvr;
    }

    public void setCvr(String cvr) {
        this.cvr = cvr;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
