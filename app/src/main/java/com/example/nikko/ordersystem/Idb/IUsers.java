package com.example.nikko.ordersystem.Idb;

import android.database.Cursor;


import com.example.nikko.ordersystem.models.model_User;

import java.sql.SQLException;

public interface IUsers {

    public Cursor getAllUsers();

    public int delete_User(int id);

    public int update_User(int id);


    public boolean insert_User(model_User user);

}
