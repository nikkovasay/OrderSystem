package com.example.nikko.ordersystem.Idb.dbImplementation;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Configuration for SQLITE DB
 * @author Nikko
 */
public class dbConfig  {

    private String dbName = "PizzaShoppingCart.db";
    private static int dbEdition =8;


    //Getters and Setters

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public static void setDbEdition(int dbEdition) {
        dbConfig.dbEdition = dbEdition;
    }

    public String getDbName() {

        return dbName;
    }

    public static int getDbEdition() {
        return dbEdition;
    }
}
