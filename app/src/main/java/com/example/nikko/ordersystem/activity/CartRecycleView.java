package com.example.nikko.ordersystem.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nikko.ordersystem.R;
import com.example.nikko.ordersystem.adapter.CartAdapter;
import com.example.nikko.ordersystem.adapter.PizzaMenuAdapter;
import com.example.nikko.ordersystem.models.model_Pizza;

import java.util.ArrayList;

public class CartRecycleView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnDeleteAll, btnPayNow;

    private model_Pizza model_pizza;
    private CartAdapter cartAdapter;
     ArrayList<model_Pizza> pizzaList;
    private model_Pizza pizza ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_recycle_view);

        ArrayList<model_Pizza> pizzaList = new ArrayList<>();

        initControl();
    }


    /**
     * InitControls acts as the initializer for all the components and listeners of this activity
     * @author Nikko Vasay
     */
    private void initControl() {
        recyclerView = findViewById(R.id.cartRecryclerView);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnPayNow = findViewById(R.id.cartBtnPayNow);



        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        pizzaList = (ArrayList<model_Pizza>) args.getSerializable("pizzaList");


            cartAdapter = new CartAdapter(pizzaList);
            recyclerView.setAdapter(cartAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //onClick listener created for the delete button

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creates an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                //show message on builder
                builder.setMessage("Delete All Items? ").setCancelable(false).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PizzaMenuAdapter pizzaMenuAdapter = new PizzaMenuAdapter(pizzaList);
                        pizzaMenuAdapter.deleteAllFromList();
                        cartAdapter.notifyDataSetChanged(); //Notifies the data set if any changes have been made and takes action
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // DO NOTHING
                            }
                        });
                builder.show();
            }
        });

    }

    /**
     * Used for the onclick method of the pay now button
     * Intent carries the total value to the next activity - Payment class/activity
     *
     * @author Nikko Vasay
     *
     */
    public void btnPayNow(View view){
        int totalAmount;

        //Initialize intent from this class to payment class
        Intent intent = new Intent(CartRecycleView.this, Payment.class);
        totalAmount = computeTotalAmount();

        //adds the total amount to extra to pass the value to payment class
        intent.putExtra("TotalValue", totalAmount);
        startActivity(intent);
    }

    /**
     * Compute's the total amount of all pizzas in the cart, returns the total
     *
     * @author Nikko Vasay
     * @return total
     *
     */
    public int computeTotalAmount(){
        int total = 0;

        //Iterate through the pizzaList array and add until you get the total
        for (model_Pizza piz : pizzaList){
            total = total + (piz.getPizzaQuantity() * piz.getPizzaPrice());
            System.out.println(piz.getPizzaName() + " - " +  piz.getPizzaPrice() + " - " + piz.getPizzaQuantity());
        }

        return total;
    }

}
