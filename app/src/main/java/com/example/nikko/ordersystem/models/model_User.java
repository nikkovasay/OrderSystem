package com.example.nikko.ordersystem.models;

public class model_User {

    private String username;
    private String password;
    private String address;

    public model_User(String newUsername , String newPassword , String newAddress){
        this.setUsername(newUsername);
        this.setPassword(newPassword);
        this.setAddress(newAddress);
    }

    public model_User(String userEmail, String userPassword) {
        this.username = userEmail;
        this.password = userPassword;
    }


    //Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
