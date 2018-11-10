package com.example.nikko.ordersystem.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikko.ordersystem.activity.PizzaMenu;
import com.example.nikko.ordersystem.R;
import com.example.nikko.ordersystem.models.model_Pizza;

import java.util.ArrayList;


public class PizzaMenuAdapter extends BaseAdapter {

    public static Activity activity;
    //    ArrayList pizzaNames;
    ArrayList<model_Pizza> model_pizzas;
    static ArrayList<model_Pizza> pizzasInCart;
    model_Pizza pizzaModel;


    //blank constructor
    public PizzaMenuAdapter(ArrayList<model_Pizza> arrayList) {
        this.pizzasInCart = arrayList;
    }

    public PizzaMenuAdapter(Activity activity, ArrayList<model_Pizza> pizzas) {
        this.model_pizzas = pizzas;
        this.activity = activity;
        pizzasInCart = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return model_pizzas.size();
    }

    @Override
    public Object getItem(int position) {
        return model_pizzas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(activity);
            convertView = inflator.inflate(R.layout.pizza_menu_template, null);

        }

        final ImageView imageViewPizza = convertView.findViewById(R.id.imgViewPizza);
        TextView txtPizzaPrice = convertView.findViewById(R.id.txtTemplatePizzaPrice);
        TextView txtPizzaName = convertView.findViewById(R.id.txtTemplatePizzaName);
        Button btnAddToCart = convertView.findViewById(R.id.btnTemplateAddToCart);

        for (final model_Pizza pizza : model_pizzas) {
            Bitmap pizImageBitmap = BitmapFactory.decodeByteArray(model_pizzas.get(position).getPizzaImage(),0,model_pizzas.get(position).getPizzaImage().length);
            imageViewPizza.setImageBitmap(pizImageBitmap);
            txtPizzaName.setText(model_pizzas.get(position).getPizzaName());
            txtPizzaPrice.setText(Integer.toString(model_pizzas.get(position).getPizzaPrice()) + " NZD");
        }

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 0;

                if (pizzasInCart.size() != 0) {
                    for (model_Pizza piz : pizzasInCart) {
                        counter++;

                        if (!(piz.getPizzaName().equals(model_pizzas.get(position).getPizzaName()))) {
                            if (counter == pizzasInCart.size()) {
                                pizzasInCart.add(model_pizzas.get(position));
                                Toast.makeText(v.getContext(), model_pizzas.get(position).getPizzaName() + "Added To Cart", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        } else if (piz.getPizzaName().equals(model_pizzas.get(position).getPizzaName())) {
                            piz.setPizzaQuantity(piz.getPizzaQuantity() + 1);
//                            System.out.println(piz.getPizzaName() + piz.getPizzaQuantity());
                            Toast.makeText(v.getContext(), model_pizzas.get(position).getPizzaName() + "Added Quantity", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } else {
                    pizzasInCart.add(model_pizzas.get(position));
                    Toast.makeText(v.getContext(), model_pizzas.get(position).getPizzaName() + "Added To Cart", Toast.LENGTH_SHORT).show();
                }

                for (model_Pizza pizz : pizzasInCart) {
                    System.out.println(pizz.getPizzaName() + " " + pizz.getPizzaQuantity());
                    System.out.println("Cart Size :" + pizzasInCart.size());
                }

            }
        });

        imageViewPizza.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PizzaMenu pizzaMenu = new PizzaMenu();
                pizzaMenu.onCreateDialog(position, activity,model_pizzas);
                return true;
            }
        });

        return convertView;
    }


    public static ArrayList<model_Pizza> getPizzasInCart() {
        return pizzasInCart;
    }


    public void deleteFromArrayList(int position) {
        pizzasInCart.remove(position);
    }

    public void deleteAllFromList() {
        pizzasInCart.clear();
    }

    public void setNewPizzaQuantity(int position, int newQuantity) {

        pizzasInCart.get(position).setPizzaQuantity(newQuantity);

    }


}
