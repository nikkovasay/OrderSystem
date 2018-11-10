package com.example.nikko.ordersystem.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.nikko.ordersystem.Idb.dbImplementation.IUserImplementation;
import com.example.nikko.ordersystem.R;
import com.example.nikko.ordersystem.admin_activity.AddAdminPizza;
import com.example.nikko.ordersystem.admin_activity.AdminMainMenu;
import com.example.nikko.ordersystem.admin_activity.ManageVoucher;
import com.example.nikko.ordersystem.models.model_User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This class controls the Sign Up activity
 * @author Nikko Vasay
 */
public class SignUp extends AppCompatActivity {

    public EditText username, password, address;
    Button btnSignUp;
    SharedPreferences sp;
    File personalInfo = new File("/data/data/com.example.nikko.ordersystem/shared_prefs");
    String Default;

    private String TAG = ManageVoucher.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initialize Shared Preferences
        sp = getSharedPreferences("Personal_Information",MODE_PRIVATE);

        //Initalize Views
        setupViews();



        //On click listener for the sign up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Text from email,password and address field
                String uEmail = username.getText().toString();
                String uPassword = password.getText().toString();
                String uAddress = address.getText().toString();

                //Validation, if 1 field is empty -> must fill in all fields
                if(uEmail.isEmpty() || uPassword.isEmpty() || uAddress.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Fill In All Fields. Thank You",Toast.LENGTH_LONG).show();
                }else{
                    addUser(uEmail,uPassword,uAddress);
                }

            }
        });


    }


    /**
     *Life cycle control on stop and shared preferences
     *
     */

    @Override
    protected void onStop() {
        super.onStop();
        //Create shared preference file
        sp = getSharedPreferences("personalInformation",MODE_PRIVATE);
        //allow SP edit
        SharedPreferences.Editor editor = sp.edit();

        //put strings inside the shared preferences
        editor.putString("GetUsername",username.getText().toString());
        editor.putString("GetPassword",password.getText().toString());
        editor.putString("GetAddress", address.getText().toString());

        //commit the strings into the SP file
        editor.commit();

        //Clear fields
        username.setText("");
        password.setText("");
        address.setText("");

    }

    /**
     * Loads personal information SP file
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(personalInfo.exists()){
//            Toast.makeText(this, "On Post Resume", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     *Function which initializes views
     */
    public void setupViews() {
        username = findViewById(R.id.txtSignUpUsername);
        password = findViewById(R.id.txtSignUpPassword);
        address = findViewById(R.id.txtSignUpAddress);
        btnSignUp = findViewById(R.id.btnSignUpSignUp);
    }

    /**
     * This function allows the user to register and add to the database
     * please see the Sign In or ManageVoucher class for more details on how this works
     * @param userEmail
     * @param userPass
     * @param userAddress
     */
    private void addUser (final String userEmail, final String userPass, final String userAddress){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configure.ADD_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;

                try {
                    Log.d(TAG, "User Response: " + response.toString());
                    jsonObject = new JSONObject(response.toString());

                    boolean error = jsonObject.getBoolean("error");

                    if (!error) {
                        JSONObject user = jsonObject.getJSONObject("user");
                        String userEmail = user.getString("userEmail");
                        String userPassword = user.getString("userPassword");
                        String userAddress = user.getString("userAddress");

                        displayMessage("Successfully Aded User : " + userEmail );

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

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userEmail", userEmail);
                params.put("userPassword", userPass);
                params.put("userAddress",userAddress);

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
