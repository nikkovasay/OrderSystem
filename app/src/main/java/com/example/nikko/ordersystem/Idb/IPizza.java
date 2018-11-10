package com.example.nikko.ordersystem.Idb;

import android.database.Cursor;

import com.example.nikko.ordersystem.models.model_Pizza;

public interface IPizza {

    public Cursor getAllPizzas();

    public Cursor getPizzaByItemName(String name);

    public boolean delete_Pizza(model_Pizza pizza);

    public int update_Pizza(model_Pizza pizza, String oldPizName);

    //    public boolean insert_Pizza(String pizzaName, int pizzaPrice, String pizzaDesc, String pizzaPath);
    public boolean insert_Pizza(model_Pizza pizza);

    public void OpenConnection();

    public void CloseConnection();

}
