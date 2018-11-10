package com.example.nikko.ordersystem.models;

import java.util.ArrayList;

public class model_Cart {

    private ArrayList<model_Pizza> pizzaList;
    private int pizzaQuantity;

    //Empty Constructor
    public model_Cart(){

    }

    //
    public model_Cart(ArrayList<model_Pizza> pizzas, int quantity){
        this.pizzaList = pizzas;
        this.pizzaQuantity = quantity;
    }

    public ArrayList<model_Pizza> getPizzaList() {
        return pizzaList;
    }

    public void setPizzaList(ArrayList<model_Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }

    public int getPizzaQuantity() {
        return pizzaQuantity;
    }

    public void setPizzaQuantity(int pizzaQuantity) {
        this.pizzaQuantity = pizzaQuantity;
    }
}
