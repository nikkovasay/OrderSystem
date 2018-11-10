package com.example.nikko.ordersystem.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nikko.ordersystem.Idb.dbImplementation.IPizzaImplementation;
import com.example.nikko.ordersystem.R;
import com.example.nikko.ordersystem.adapter.PizzaMenuAdapter;
import com.example.nikko.ordersystem.models.model_Pizza;

import java.io.Serializable;
import java.util.ArrayList;

public class PizzaMenu extends AppCompatActivity {

    PizzaMenuAdapter pizzaMenuAdapter;
    ArrayList<String> pizzaIcon;
    ArrayList<model_Pizza> pizza;
    ArrayList<model_Pizza> newListOfPizzas;
    GridView gridView, dynamic;
    Toolbar mToolbar;
    public static Activity activity;
    Button btnProceedToCart;
    IPizzaImplementation pizzaImp;

    public static String FACEBOOK_URL = "https://www.facebook.com/nix.vasay";
    public static String FACEBOOK_PAGE_ID = "10207060702301456";

    public PizzaMenu() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_menu);

        activity = this;
        //Initialize all the components
        initViews();
        pizzaImp = new IPizzaImplementation(PizzaMenu.this);

        //Open DB Connection
        pizzaImp.OpenConnection();

        //Add the data
        Cursor data = pizzaImp.getAllPizzas();
        while(data.moveToNext()){
            byte[] convertByteImage = data.getBlob(4);
            pizza.add(new model_Pizza(data.getString(1),data.getInt(2),data.getString(3),convertByteImage));
        }

        //Close Connection
        pizzaImp.CloseConnection();

        pizzaMenuAdapter = new PizzaMenuAdapter(activity, pizza);
        gridView.setAdapter(pizzaMenuAdapter);


    }

    /**
     * Inflates the toolbar
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.ordermenu, menu);
        return true;


    }

    /**
     * this overriden method is used to go to the facebook page on the toolbar located on this activity
     *
     * @param item
     * @return  super.onOptionsItemSelected(item)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_about_us) {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = getFacebookPageURL(this);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize all components of the class
     */
    public void initViews() {
        pizza = new ArrayList<>();
        newListOfPizzas = new ArrayList<>();
        pizzaIcon = new ArrayList<>();
        gridView = findViewById(R.id.gridViewPizzaMenu);
        mToolbar = findViewById(R.id.toolbar);
        btnProceedToCart = findViewById(R.id.btnProceedToCart);

    }


    /**
     * This is a function that is used for the onClick attribute of the proceed to cart button
     * @param view
     */
    public void proceedToCart(View view) {

        //gets all the pizzas in cart and ads them to the newListOfPizzas ArrayList
        for (int i = 0; i < pizzaMenuAdapter.getPizzasInCart().size(); i++) {
            newListOfPizzas.add(pizzaMenuAdapter.getPizzasInCart().get(i));
            System.out.println(newListOfPizzas.get(i).getPizzaName() + " : Pizza name");
        }

        //Intent to send to the cart recycle view
        Intent intent = new Intent(getApplicationContext(), CartRecycleView.class);
        Bundle args = new Bundle();
        args.putSerializable("pizzaList", (Serializable) newListOfPizzas);
        intent.putExtra("BUNDLE", args);

        startActivity(intent);
        newListOfPizzas.clear();
    }


    /**
     * This function is used to get the facebook URL and return it to be used as a link to a facebook page
     * @param context
     * @return FACEBOOK_URL
     */
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;

            boolean activated = packageManager.getApplicationInfo("com.facebook.katana", 0).enabled;
            if (activated) {
                if ((versionCode >= 3002850)) {
                    return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                } else {
                    return "fb://page/" + FACEBOOK_PAGE_ID;
                }
            } else {
                return FACEBOOK_URL;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL;
        }
    }

    /**
     * This is a pop up used in the pizzamenu adapter, it creates an alert dialog that shows the pizza description on long click press
     *
     * @param id
     * @param activity
     * @param dialogPizzaList
     * @return alertDialog
     */
    public Dialog onCreateDialog(int id, Activity activity, ArrayList<model_Pizza> dialogPizzaList) {

        //Create alertdialog
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();

        //Inflate View
        View mView = activity.getLayoutInflater().inflate(R.layout.fragment_pizza, null);

        //Set the view of the alert dialog
        alertDialog.setView(mView);

        //find view by ID's
        TextView txtFragDesc = mView.findViewById(R.id.txtFragmentPizzaDesc);
        TextView txtFragName = mView.findViewById(R.id.txtFragmentPizzaName);
        ImageView imgFragImage = mView.findViewById(R.id.imgFragmentPizza);

        //Get data from DB and set to teh alert dialog
        Bitmap pizImageBitmap = BitmapFactory.decodeByteArray(dialogPizzaList.get(id).getPizzaImage(),0,dialogPizzaList.get(id).getPizzaImage().length);
        imgFragImage.setImageBitmap(pizImageBitmap);
        txtFragName.setText(dialogPizzaList.get(id).getPizzaName().toString());
        txtFragDesc.setText(dialogPizzaList.get(id).getPizzaDescription().toString());


        alertDialog.show();

        return alertDialog;

    }


    /**
     * Overides the back button when activity is on the pizza menu
     *
     */
    @Override
    public void onBackPressed() {
        handleBackButton();
    }

    public void handleBackButton(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Log Out??");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to Log Out?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.pizza3);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PizzaMenu.this, SignIn.class);
                startActivity(intent);
            }
        });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }



}
