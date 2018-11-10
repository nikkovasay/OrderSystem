package com.example.nikko.ordersystem.models;

public class model_PersonalInfo {

    private String name;
    private String email;
    private String address;
    private String phoneNumber;

    public model_PersonalInfo(){

    }

    public model_PersonalInfo(String newName, String newEmail, String newAddress, String newPhoneNumber ){
        this.setName(newName);
        this.setEmail(newEmail);
        this.setAddress(newAddress);
        this.setPhoneNumber(newPhoneNumber);
    }

    //Getters And Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
