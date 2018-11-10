package com.example.nikko.ordersystem.admin_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nikko.ordersystem.R;

/**
 * Main menu class which controls the admin side
 * @author Nikko
 */
public class AdminMainMenu extends AppCompatActivity {

    public Button btnAddAdminUser, btnAddPizza, btnEditDeletePizza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);

        setUpViews();


    }


    /**
     * Initializes components for the view
     */
    public void setUpViews(){
        btnAddPizza = findViewById(R.id.btn_adminAddPizza);

    }

    /**
     * On click listener for admin users
     * @param view
     */


    /**
     * onclick listener for admin pizza
     * @param view
     */
    public void addAdminPizza(View view){
        Intent intent = new Intent(this, AddAdminPizza.class);
        startActivity(intent);
    }

    /**
     * onclick listener for manage vouchers
     * @param view
     */
    public void manageVouchers(View view){
        Intent intent = new Intent(this,ManageVoucher.class);
        startActivity(intent);
    }
}
