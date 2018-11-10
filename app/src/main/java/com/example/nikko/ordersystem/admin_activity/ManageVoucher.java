package com.example.nikko.ordersystem.admin_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nikko.ordersystem.R;
import com.example.nikko.ordersystem.activity.AppController;
import com.example.nikko.ordersystem.activity.Configure;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class ManageVoucher extends AppCompatActivity {


    EditText voucherCode,voucherValue;
    Button btnAddVoucher;
//    private StringRequest stringRequest;
    private String TAG = ManageVoucher.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_voucher);

        voucherCode = findViewById(R.id.txt_manageVoucher_vCode);
        voucherValue = findViewById(R.id.txt_manageVoucher_vValue);
        btnAddVoucher = findViewById(R.id.btn_voucherManager_addVoucher);


        btnAddVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vCode = voucherCode.getText().toString().trim();
                String vValue = voucherValue.getText().toString().trim();

                try{
                    int valueInt=  Integer.parseInt(vValue);

            if(valueInt >= 1 && valueInt <=95){
                addVoucher(vCode,vValue);
            }else{
                Toast.makeText(getApplicationContext(),"Invalid value", Toast.LENGTH_LONG).show();
            }

                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid value", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void addVoucher(final String vouchCode, final String vouchValue){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configure.ADD_VOUCHER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;

                try {
                    Log.d(TAG, "Voucher Response: " + response.toString());
                     jsonObject = new JSONObject(response.toString());

                    boolean error = jsonObject.getBoolean("error");

                    if (!error) {
                        JSONObject voucher = jsonObject.getJSONObject("voucher");
                        String voucherCode = voucher.getString("voucherCode");
                        String voucherValue = voucher.getString("voucherValue");

                        displayMessage("Successfully Aded Voucher " + voucherCode );

                    } else {
                        String errorMessage = jsonObject.getString("error_msg");
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
//            displayMessage("Testing connection issue");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("voucherCode", vouchCode);
                params.put("voucherValue", vouchValue);

                return params;
            }

        };
        String myTag = "Request_signup";


        AppController.getAppController().addRequestToQueue(stringRequest,myTag);

    }

    private void displayMessage(String x) {
        Toast.makeText(this, x, Toast.LENGTH_LONG).show();
    }


}
