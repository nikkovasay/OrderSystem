package com.example.nikko.ordersystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nikko.ordersystem.R;
import com.example.nikko.ordersystem.models.model_Pizza;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This Class controls the payment activity
 * @author Nikko Vasay
 */
public class Payment extends AppCompatActivity {

    TextView lblTotalAmount;
    EditText txtVoucherCode;
    Button btnVoucher, btnOrder;
    ArrayList<model_Pizza> pizzasFromCart;
    int totalAmount;
    String stringTotalAmount;

    public Payment() {

    }

    public Payment(ArrayList<model_Pizza> newPizzasFromCart) {
        this.pizzasFromCart = newPizzasFromCart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        System.out.println("Test Payment");

        initView();

        txtVoucherCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVoucherCode.setText("");

            }
        });


    }

    /**
     *Initizializes the components and listeners of the class
     *
     */
    public void initView() {

        lblTotalAmount = findViewById(R.id.lblAmountPayment);
        txtVoucherCode = findViewById(R.id.txtVoucherCode);
        btnVoucher = findViewById(R.id.btnVoucher);
        btnOrder = findViewById(R.id.btnOrderNow);


        Intent intent = getIntent();
        totalAmount = intent.getIntExtra("TotalValue", 0);

        stringTotalAmount = Integer.toString(totalAmount);

        lblTotalAmount.setText(stringTotalAmount + ".00 NZD");

        /**
         * function that is used for resetting the voucher code text field to blank when clicked
         * @param view
         */
        txtVoucherCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVoucherCode.setText("");
            }
        });

        /**
         * function that is used for the voucher onClick Attribute
         * @param view
         */
        btnVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyVoucher(txtVoucherCode.getText().toString());
            }
        });
    }


    /**
     * function that is used for the pay onClick Attribute
     * @param view
     */
    public void payNow(View view) {
        Intent intent = new Intent(getApplicationContext(), PizzaMenu.class);
        Toast.makeText(this, "Order Placed, Please Wait for your Pizza", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    /**
     * This function applies the voucher code by using a web API to call the database
     * @param vouchCode
     */
    private void applyVoucher(final String vouchCode) {

        final String TAG = Payment.class.getSimpleName();

        //Create a new string requrest object, parameters are the (Request method type , URL , new Response.Listener<Type>)
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configure.GET_VOUCHER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                int newTotalAmount;

                //Try Catch for JSON Exceptions
                try {
                    Log.d(TAG, "Voucher Response: " + response.toString());

                    //Create a new jsonObject
                    jsonObject = new JSONObject(response.toString());

                    //Boolean which stores the voucher status from the JSON file being passed
                    boolean error = jsonObject.getBoolean("voucher_status");

                    //If there are no errors
                    if (error) {
                        //Create JSON Object called voucher and get the voucher attributes by using JSONObjectname.getType, in this case voucher.getString("JSON Attribute Name")
                         JSONObject voucher = jsonObject.getJSONObject("voucher");
                           String voucherCode = voucher.getString("voucherCode");
                           String voucherValue = voucher.getString("voucherValue");
                           String voucherMessage = voucher.getString("voucherMessage");

                    displayMessage("CODE : " + voucherCode + ", " + voucherMessage + " ," + voucherValue + "% discount applied");

                        // Computation for voucher discount
                        double voucherVal = (int)Integer.parseInt(voucherValue) * .01;
                        newTotalAmount = (int) (totalAmount - (totalAmount * voucherVal));
                        lblTotalAmount.setText(Integer.toString(newTotalAmount) + ".00 NZD");


                    } else {
                    String errorMessage = jsonObject.getString("vouchernotExist").toString();
                    displayMessage(errorMessage);
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                    displayMessage("JSON Error reading" + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                displayMessage("Unknown Error " + error.getMessage());
            }
        }) {
            //First to be executed, these are the values passed to the WEB API, this/These values are the $_POST['attribute name here'] in the API file
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("voucherCode", vouchCode);
                return params;
            }

        };

        //Adds to the request queue
        AppController.getAppController().addRequestToQueue(stringRequest,TAG);


    }

    /**
     * This function is a simple toast message, supply the message
     * @param x
     */
    private void displayMessage(String x) {
        Toast.makeText(this, x, Toast.LENGTH_LONG).show();
    }
}
