package com.example.nikko.ordersystem.Idb.dbImplementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.nikko.ordersystem.Idb.IPizza;
import com.example.nikko.ordersystem.models.model_Pizza;

/**
 * Pizza implementation class
 * @author Nikko
 */
public class IPizzaImplementation extends SQLiteOpenHelper implements IPizza {

    private SQLiteDatabase db;
    private Context context;

    private static String table1 = "tbl_pizza";

    static dbConfig dbConfig = new dbConfig();

    private static String dbName = dbConfig.getDbName();
    private static int dbEdition = dbConfig.getDbEdition();

    //Constructor
    public IPizzaImplementation(Context context) {
        super(context, dbName, null, dbEdition);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //Try catch wich creates the table in SQLite
        try {
            db.execSQL("create table " + table1 + "(ID integer primary key AUTOINCREMENT," +
                    "pizza_name varchar(55)," +
                    "pizza_price integer, " +
                    "pizza_desc text," +
                    "pizza_img blob);");


            Toast.makeText(context, "Pizza Table Created", Toast.LENGTH_SHORT).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *Open SQLite connection
     */
    public void OpenConnection() {
        db = this.getWritableDatabase();
    }

    /**
     *Close SQLite connection
     */
    public void CloseConnection() {
        db.close();
    }

    /**
     *When something is changed in the database, the db version in the configuration filem ust be changed for this to trigger
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table1);
        Toast.makeText(context, "Pizza Table Upgraded", Toast.LENGTH_SHORT).show();
        onCreate(db);
    }

    /**
     * Gets all pizzas from sqlite DB
     * @return cursor
     */
    @Override
    public Cursor getAllPizzas() {
        String query = "Select * From " + table1;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    /**
     * deletes a pizza from SQLite DB
     * @param pizza
     * @return true
     */
    public boolean delete_Pizza(model_Pizza pizza) {
        db.delete(table1,"pizza_name=?", new String[]{pizza.getPizzaName()});

        return true;
    }

    /**
     * Update Query in SQLite DB
     * @param pizza
     * @param oldPizName
     * @return updatequery results
     */
    public int update_Pizza(model_Pizza pizza, String oldPizName) {

        ContentValues cv = new ContentValues();
        cv.put("pizza_name", pizza.getPizzaName());
        cv.put("pizza_price", pizza.getPizzaPrice());
        cv.put("pizza_desc", pizza.getPizzaDescription());
        cv.put("pizza_img", pizza.getPizzaImage());


        return db.update(table1, cv, "pizza_name=?", new String[]{oldPizName});

    }

    /**
     * Insert data in SQLite DB
     * @param pizza
     * @return booleanValue
     *
     */
    public boolean insert_Pizza(model_Pizza pizza) {

        ContentValues cv = new ContentValues();
        cv.put("pizza_name", pizza.getPizzaName());
        cv.put("pizza_price", pizza.getPizzaPrice());
        cv.put("pizza_desc", pizza.getPizzaDescription());
        cv.put("pizza_img", pizza.getPizzaImage());

        long status = db.insert(table1, null, cv);

        if (status > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function gets pizza by PizzaName
     * @param name
     * @return cursor
     */
    public Cursor getPizzaByItemName(String name){
        String query = "Select * From tbl_pizza Where pizza_name  =  '" + name + "'";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }



}
