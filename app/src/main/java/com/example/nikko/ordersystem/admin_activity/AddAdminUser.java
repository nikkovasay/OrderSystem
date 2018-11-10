//package com.example.nikko.ordersystem.admin_activity;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import com.example.nikko.ordersystem.R;
//
///**
// * This class is for the admin Users
// * @author Nikko Vasay
// */
//public class AddAdminUser extends AppCompatActivity {
//
//    private EditText uName, password, email;
//    private Button addButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_admin_user);
//        setUpViews();
//
//    }
//
//    /**
//     * Initializes components for the class
//     */
//    public void setUpViews() {
//        uName = findViewById(R.id.txt_adminUsername);
//        password = findViewById(R.id.txt_adminPassword);
//        email = findViewById(R.id.txt_adminEmail);
//        addButton = findViewById(R.id.btn_adminAddAdminUser);
//
//
//    }
//
//    /**
//     * Validates the username password and email if its empty
//     * @return result
//     */
//    public Boolean validate() {
//        Boolean result = false;
//
//        String uname = uName.getText().toString();
//        String pword = password.getText().toString();
//        String eMail = email.getText().toString();
//
//        if (uname.isEmpty() && pword.isEmpty() && eMail.isEmpty()) {
//            result = false;
//        } else {
//            result = true;
//        }
//        return result;
//    }
//
//
//    public void createAdminUser(View view) {
//        try {
//
//            if (validate()) {
//                //Add data to database
//            } else {
//                Toast.makeText(this, "Please enter all details needed! Thanks!", Toast.LENGTH_SHORT).show();
////                Toast.makeText(getApplicationContext(), "Data not saved, Try Again.", Toast.LENGTH_SHORT).show();
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Trace Error
//        }
//    }
//
//
//}
