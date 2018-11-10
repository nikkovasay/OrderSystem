package com.example.nikko.ordersystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nikko.ordersystem.R;
import com.example.nikko.ordersystem.admin_activity.AdminMainMenu;
import com.example.nikko.ordersystem.models.model_User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class controls the sign in class
 * @author Nikko Vasay
 */
public class SignIn extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button btnSignIn;
    CheckBox chkAdmin;
    model_User LogInUser;
    String uEmail,uPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // initialize components in the activity by finding ID 's of the comp
        editTextUsername = findViewById(R.id.txtSignInUsername);
        editTextPassword = findViewById(R.id.txtSignInPassword);
        btnSignIn = findViewById(R.id.btnSignInSignIn);
        chkAdmin = findViewById(R.id.chkBox_Admin);


        //On click listener for sign in button
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get string from email and password fields
                     uEmail = editTextUsername.getText().toString();
                     uPassword = editTextPassword.getText().toString();

                // validation if the admin box is checked
                if (chkAdmin.isChecked()) {
                    if (editTextUsername.getText().toString().equals("Admin") && editTextPassword.getText().toString().equals("Admin")) {
                        Toast.makeText(SignIn.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent pizzaMenuIntent = new Intent(getApplicationContext(), AdminMainMenu.class);
                        startActivity(pizzaMenuIntent);
                    }
                }else{
                        getUserLogin(uEmail,uPassword);
                }

            }
        });
    }

    /**
     * This function takes in the email and password, goes through the API and displays the response whether the email and password are the same with the database
     * See API code for more details -> getuser.php file
     *
     * @param email
     * @param pass
     * No Returns
     */
    private void getUserLogin(final String email, final String pass) {
        final String TAG = Payment.class.getSimpleName();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configure.GET_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    Log.d(TAG, "User Response: " + response.toString());
                    jsonObject = new JSONObject(response.toString());
                    boolean error = jsonObject.getBoolean("user_status");
                    // checks if the user status is true or false
                    if (error) {
                        //If true, replace error with current user status inside the api condition
                        error = jsonObject.getBoolean("user_status");
                        if(error){
                    Toast.makeText(SignIn.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent pizzaMenuIntent = new Intent(getApplicationContext(), PizzaMenu.class);
                            startActivity(pizzaMenuIntent);
                        }else{
                            Toast.makeText(SignIn.this, "Login Failed, Wrong Username / Password. Try Again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String errorMessage = jsonObject.getString("userWrongCredentials").toString();
                        displayMessage(errorMessage);
                    }
                } catch (Exception e) {
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
            //Starts here, passes the data to the api
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userEmail", email);
                params.put("userPassword",pass);
                return params;
            }

        };
        String myTag = "Request_signup";
        AppController.getAppController().addRequestToQueue(stringRequest, myTag);
    }

    private void displayMessage(String x) {
        Toast.makeText(this, x, Toast.LENGTH_LONG).show();
    }


}

