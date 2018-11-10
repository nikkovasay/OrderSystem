package com.example.nikko.ordersystem.models;

import android.database.Cursor;
import android.media.Image;

import com.example.nikko.ordersystem.Idb.IPizza;

import java.io.Serializable;

/**
 * Pizza Model Class
 * @author Nikko
 */
public class model_Pizza implements Serializable {

    private int pizzaId;
    private String pizzaName;
    private String pizzaDescription;
    private byte[] pizzaImage;
    private String pizzaPath;
    private int  pizzaQuantity, pizzaPrice;



    public model_Pizza(String piz_name, int piz_price, String piz_description,  byte[] piz_path) {
        this.pizzaName = piz_name;
        this.pizzaPrice = piz_price;
        this.pizzaDescription = piz_description;
        this.pizzaImage = piz_path;
        this.pizzaQuantity = 1;

    }

    public model_Pizza(String newPizzaName, int newPizzaPrice, String newPizzaDescription, String newPizzaImage) {
        this.pizzaName = newPizzaName;
        this.pizzaPrice = newPizzaPrice;
        this.pizzaDescription = newPizzaDescription;
        this.pizzaPath = newPizzaImage;
        this.pizzaQuantity = 1;

    }

    public model_Pizza(String newPizzaName) {
        this.pizzaName = newPizzaName;
    }


    public model_Pizza() {

    }


    public int getPizzaQuantity() {
        return pizzaQuantity;
    }

    public void setPizzaQuantity(int pizzaQuantity) {
        this.pizzaQuantity = pizzaQuantity;
    }


    //Getters and Setters
    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public int getPizzaPrice() {
        return pizzaPrice;
    }

    public void setPizzaPrice(int pizzaPrice) {
        this.pizzaPrice = pizzaPrice;
    }

    public String getPizzaDescription() {
        return pizzaDescription;
    }

    public void setPizzaDescription(String pizzaDescription) {
        this.pizzaDescription = pizzaDescription;
    }

    public byte[] getPizzaImage() {
        return pizzaImage;
    }

    public void setPizzaImage(byte[] pizzaImage) {
        this.pizzaImage = pizzaImage;
    }


    public String getPizzaPath() {
        return pizzaPath;
    }

    public void setPizzaPath(String pizzaPath) {
        this.pizzaPath = pizzaPath;
    }

    public int getPizzaId() {
        return pizzaId;
    }



}
