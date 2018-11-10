package com.example.nikko.ordersystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nikko.ordersystem.R;

/*
    This Class is the entry point of the application
 */

public class MainActivity extends AppCompatActivity {

    public static Activity activity;
    Button btnSignUp , btnSignIn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btn_signIn);
        btnSignUp = findViewById(R.id.btn_signUp);
        activity = this;

    }


    /**
     * This function is used for the onClick attribute of the SignIn Button
     *
     * @author Nikko Vasay
     * @param view
     */
    public void SignIn(View view){
        Intent signIn = new Intent(activity,SignIn.class);
        startActivity(signIn);

    }

    /**
     * This function is used for the onClick attribute of the SignUp Button
     * @author Nikko Vasay
     * @param view
     */
    public void SignUp(View view){
        Intent signUp = new Intent(activity,SignUp.class);
        startActivity(signUp);
    }
}
