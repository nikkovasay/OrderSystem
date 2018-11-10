package com.example.nikko.ordersystem.models;

public class model_AdminUser {


    private String username;
    private String password;
    private String address;

    public model_AdminUser(String newUsername , String newPassword ){
        this.setUsername(newUsername);
        this.setPassword(newPassword);

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
}
