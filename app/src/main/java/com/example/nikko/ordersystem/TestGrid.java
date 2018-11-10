//package com.example.nikko.ordersystem;
//
//import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.widget.Button;
//import android.widget.GridView;
//
//import com.example.nikko.ordersystem.models.model_Pizza;
//
//import java.util.ArrayList;
//
//public class TestGrid extends AppCompatActivity {
//
//    TestGridAdapter pizzaMenuAdapter;
//    ArrayList<String> pizzaIcon;
//    ArrayList<model_Pizza> pizza;
//    GridView gridView;
//    Toolbar mToolbar;
//    public static Activity activity;
//    Button btnProceedToCart;
//
//    ArrayList<model_Pizza> pizzaList;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_grid);
//
//        activity = this;
//      //  pizzaList = new ArrayList<>();
//
//        //Initialize all the components
//        initViews();
//
//
//
//
//        //Add the data
////        pizza.add(new model_Pizza("BBQ Sausage", "15.70", "BBQ Sausage Pizza", R.drawable.pizza1));
////        pizzaList.add(new model_Pizza("Pepperoni", "17.50", "Pepperoni Pizza ", R.drawable.pizza2));
////        pizza.add(new model_Pizza("Vegetarian", "15.50", "Vegetarian Pizza", R.drawable.pizza3));
//////        pizza.add(new model_Pizza("Garlic Shrimp", "22.50", "Garlic Shrimp Pizza", R.drawable.pizza4));
//
//        pizzaList = (ArrayList<model_Pizza>) getIntent().getSerializableExtra("pizzaList");
//
//
//
//
//
//
////        pizzaMenuAdapter = new PizzaMenuAdapter(activity,price,pizzaIcon);
//     //   pizzaMenuAdapter = new TestGridAdapter(activity, pizzaList);
//      //  gridView.setAdapter(pizzaMenuAdapter);
//
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater mMenuInflater = getMenuInflater();
//        mMenuInflater.inflate(R.menu.ordermenu, menu);
//        return true;
//    }
//
//    public void initViews() {
//        pizza = new ArrayList<>();
//        pizzaIcon = new ArrayList<>();
//        gridView = findViewById(R.id.testgridViewPizzaMenu);
//        mToolbar = findViewById(R.id.Ttoolbar);
//        btnProceedToCart = findViewById(R.id.testbtnProceedToCart);
//    }
//
//
//
//}
