package com.example.nikko.ordersystem.Idb.dbImplementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.nikko.ordersystem.Idb.IUsers;
import com.example.nikko.ordersystem.models.model_User;

public class IUserImplementation extends SQLiteOpenHelper implements IUsers {


    private SQLiteDatabase db;
    private Context context;

    private static String table1 = "tbl_Users";

    static dbConfig dbConfig = new dbConfig();

    private static String dbName = dbConfig.getDbName();
    private static int dbEdition = dbConfig.getDbEdition();

    public IUserImplementation(Context context) {
        super(context, dbName, null, dbEdition);
        this.context = context;
       // Toast.makeText(context, "Pass through IUser Constructor", Toast.LENGTH_SHORT).show();

    }

   @Override
    public void onCreate(SQLiteDatabase db) {
       try {
           db.execSQL("create table " + table1 + "(ID integer primary key AUTOINCREMENT," +
                   "user_name varchar(50)," +
                   "user_password varchar(50), " +
                   "user_address text);");


           Toast.makeText(context, "User Table Created", Toast.LENGTH_SHORT).show();

       } catch (SQLException e) {
           e.printStackTrace();
       }
    }

    public void OpenConnection() {
        db = this.getWritableDatabase();
    }

    public void CloseConnection() {
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table1);
        Toast.makeText(context, "User Table Upgraded", Toast.LENGTH_SHORT).show();
        onCreate(db);
    }

    @Override
    public Cursor getAllUsers() {
        Cursor cursor = db.rawQuery("select * from " + table1, null);
        return cursor;
    }

    @Override
    public int delete_User(int id) {
        return db.delete(table1, "ID=?", new String[]{String.valueOf(id)});
    }

    @Override
    public int update_User(int id) {
        return 0;
    }

    @Override
    public boolean insert_User(model_User user) {

        ContentValues cv = new ContentValues();
        cv.put("user_name", user.getUsername());
        cv.put("user_password", user.getPassword());
        cv.put("user_address", user.getAddress());


        long status = db.insert(table1, null, cv);

        if (status > 0) {
            return true;
        } else {
            return false;
        }
    }
}
