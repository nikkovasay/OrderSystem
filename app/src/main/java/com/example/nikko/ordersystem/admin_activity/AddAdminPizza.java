package com.example.nikko.ordersystem.admin_activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikko.ordersystem.Idb.IPizza;
import com.example.nikko.ordersystem.Idb.dbImplementation.IPizzaImplementation;
import com.example.nikko.ordersystem.R;
import com.example.nikko.ordersystem.models.model_Pizza;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This is the admin pizza side, where you can manage the list of pizzas in the menu
 * @author Nikko Vasay
 */
public class AddAdminPizza extends AppCompatActivity {

    final int REQUEST_CODE_FOR_GALLERY = 999;
    private static Context context;
    ImageView pizzaImage;
    EditText pizzaName, pizzaPrice, pizzaDesc;
    TextView pizzaPath;
    Button btnUploadPicture, btnAddPizza;
    IPizza pizzaImp;
    model_Pizza piz;
    ListView pizzaListView;
    ArrayList<String> pizzaArrayList;
    ListAdapter pizzaListAdapter;
    String oldPizzaName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin_pizza);

        setUpViews();
        pizzaImp = new IPizzaImplementation(this);
        loadPizzaInfo();

        //PizzaListView Item Click Listener
        pizzaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Open Pizza Implementation Connection
                pizzaImp.OpenConnection();

                //Get the pizza name based on the position in the list
                String ItemName = pizzaListAdapter.getItem(position).toString();

                // data holds the data coming from the pizza implementation function get pizza by item name
                Cursor data = pizzaImp.getPizzaByItemName(ItemName);

                //If no data found then database is empty
                if(data.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Database was empty", Toast.LENGTH_SHORT).show();
                }else{
                    //If data is found, traverse through the data by using the moveToNext function
                    while(data.moveToNext()){

                        //add decode image here
                        byte[] pizzaBlobConvert = data.getBlob(4);

                        //Create new model_Pizza object called piz
                        piz = new model_Pizza(data.getString(1),data.getInt(2),data.getString(3),pizzaBlobConvert);

                        //Converting the image
                        Bitmap pizImageBitmap = BitmapFactory .decodeByteArray(piz.getPizzaImage(),0,piz.getPizzaImage().length);

                        //Set the text and image of the fields from here
                        pizzaName.setText(piz.getPizzaName());
                        pizzaPrice.setText(String.valueOf(piz.getPizzaPrice()));
                        pizzaDesc.setText(piz.getPizzaDescription());
                        pizzaImage.setImageBitmap(pizImageBitmap);
                    }
                }

                //Close pizza connection to DB
                pizzaImp.CloseConnection();
                oldPizzaName = pizzaName.getText().toString();
                Toast.makeText(getApplicationContext(), oldPizzaName, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Initializes the components and listeners in the class
     */
    public void setUpViews() {
        pizzaImage = findViewById(R.id.imgView_imgPizza);
        pizzaName = findViewById(R.id.txt_adminPizzaName);
        pizzaPrice = findViewById(R.id.txt_adminPizzaPrice);
        pizzaDesc = findViewById(R.id.txt_adminPizzaDesc);
        btnUploadPicture = findViewById(R.id.btn_uploadPizzaPicture);
        btnAddPizza = findViewById(R.id.btn_adminAddPizza);
        pizzaListView = findViewById(R.id.listViewPizza);
        pizzaArrayList = new ArrayList<>();
        pizzaListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,pizzaArrayList);


        //Onclick listener for upload picture
        btnUploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Request permission to access the gallery of the phone
                ActivityCompat.requestPermissions(AddAdminPizza.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_FOR_GALLERY);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Check if my application when requested image is = the code given
        if(requestCode ==REQUEST_CODE_FOR_GALLERY){
            //check whether user granted permission
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOR_GALLERY);
            }else{
                Toast.makeText(this,"Permission is needed first",Toast.LENGTH_LONG).show();
            }

//            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_FOR_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();


            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                pizzaImage.setImageBitmap(bitmap);



            }catch(FileNotFoundException e){
                e.printStackTrace();
            }

        }
    }

    public Boolean validate() {
        boolean result = false;

        if (pizzaName.getText().toString().isEmpty() ||
                pizzaPrice.getText().toString().isEmpty() ||
                pizzaDesc.getText().toString().isEmpty()) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    public void addAdminPizza(View view) {
        try {
            if (validate()) {
                String piz_name = pizzaName.getText().toString();
                String piz_price = pizzaPrice.getText().toString();
                String piz_description = pizzaDesc.getText().toString();
                byte[] piz_Img = getImageBytes(pizzaImage);

                int intPizPrice = Integer.parseInt(piz_price);
//                Add Pizza to DB

                //Create a pizza
                 piz = new model_Pizza(piz_name,intPizPrice,piz_description,piz_Img);

                pizzaImp.OpenConnection();


                boolean status = pizzaImp.insert_Pizza(piz);

                if (status) {
                    Toast.makeText(this, "Record Successfully Inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAdminPizza.this, AdminMainMenu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Record Was Not Inserted", Toast.LENGTH_SHORT).show();
                }

                pizzaImp.CloseConnection();
            }else{
                Toast.makeText(this, "Enter Text In All Fields", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Enter a Valid Price Value!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void loadPizzaInfo(){
        pizzaImp.OpenConnection();
        Cursor data = pizzaImp.getAllPizzas();
        if(data.getCount() == 0) {
            Toast.makeText(this, "Database was empty", Toast.LENGTH_SHORT).show();

        }else{
            while(data.moveToNext()){
                piz = new model_Pizza(data.getString(1),data.getInt(2),data.getString(3),data.getBlob(4));

                pizzaArrayList.add(piz.getPizzaName());

                pizzaListAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,pizzaArrayList){
                @Override // Set properties for the simple list view
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);

                    //white text color
                    int textColor = R.color.textColor1;
                    //text color set
                    textView.setTextColor(AddAdminPizza.this.getResources().getColor(textColor));

                    return textView;
                }
            };
                pizzaListView.setAdapter(pizzaListAdapter);
            }
        }

        pizzaImp.CloseConnection();
    }




    public void deletePizza(View view){
        pizzaImp.OpenConnection();

        String piz_name = pizzaName.getText().toString();
        String piz_price = pizzaPrice.getText().toString();
        String piz_description = pizzaDesc.getText().toString();
        byte[] piz_Img = getImageBytes(pizzaImage);

        int intPizPrice = Integer.parseInt(piz_price);
//                Add Pizza to DB

        //Create a pizza
        piz = new model_Pizza(piz_name,intPizPrice,piz_description,piz_Img);

        if(pizzaImp.delete_Pizza(piz)){
            refresh_StudentList(null);
            Toast.makeText(getApplicationContext(), "Successfully Deleted Record", Toast.LENGTH_SHORT).show();
        }
        pizzaImp.CloseConnection();
    }

    public void updatePizza(View view){

        if (validate()) {
            String piz_name = pizzaName.getText().toString();

            String piz_price = pizzaPrice.getText().toString();
            String piz_description = pizzaDesc.getText().toString();
            byte[] piz_Img = getImageBytes(pizzaImage);

            int intPizPrice = Integer.parseInt(piz_price);
//                Add Pizza to DB

            //Create a pizza
            piz = new model_Pizza(piz_name, intPizPrice, piz_description, piz_Img);
            pizzaImp.OpenConnection();

            if(pizzaImp.update_Pizza(piz,oldPizzaName) > 0) {
                Toast.makeText(getApplicationContext(), "Successfully Updated Record", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Failed To Updated Record", Toast.LENGTH_SHORT).show();
            }
            refresh_StudentList(null);
            pizzaImp.CloseConnection();
        }else{
            Toast.makeText(getApplicationContext(), "Something Failed", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * refresh the list after every action
     * @param view
     */
    public void refresh_StudentList(View view){
        pizzaImp.OpenConnection();
        Cursor cursor = pizzaImp.getAllPizzas();
        pizzaArrayList = new ArrayList<>();
        pizzaListAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, pizzaArrayList){
            @Override // Set properties for the simple list view
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);

                //white text color
                int textColor = R.color.textColor1;
                //text color set
                textView.setTextColor(AddAdminPizza.this.getResources().getColor(textColor));

                return textView;
            }
        };


        while(cursor.moveToNext()){
            piz = new model_Pizza(cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getBlob(4));

            pizzaArrayList.add(piz.getPizzaName());
            pizzaListAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,pizzaArrayList);
            pizzaListView.setAdapter(pizzaListAdapter);
        }

        pizzaListView.setAdapter(pizzaListAdapter);
        pizzaImp.CloseConnection();
    }

    //PrepareImage to be stored in DB using bytes
    public static byte[] getImageBytes(ImageView img){
    //First we need to have image data then using image data we are going to
    //convert to byte using BitMap

        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] imageBytes = stream.toByteArray();
        return imageBytes;


    }


}
