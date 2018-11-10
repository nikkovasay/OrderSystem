package com.example.nikko.ordersystem.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikko.ordersystem.R;
import com.example.nikko.ordersystem.models.model_Pizza;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private ArrayList<model_Pizza> pizzaInCart;
    private int quantity;

    /**
     * Constructor for this class
     * @param pizzaList
     */
    public CartAdapter(ArrayList<model_Pizza> pizzaList) {
        this.pizzaInCart = pizzaList;
    }

    /**
     * This function allows the creation of a custom view holder to add a layout as a view
     * @param viewGroup
     * @param i
     * @return myViewHolder
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Creates a view type object
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item, null);

        //creates a view holder object
        MyViewHolder myViewHolder = new MyViewHolder(viewItem);

        //returns view holder
        return myViewHolder;
    }

    /**
     * Binds the viewholder
     * @param myViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final int position = i;

        //Creates an object pizza from the model_pizza class
        model_Pizza pizza = pizzaInCart.get(position);

        //Sets the text for the cart items
        myViewHolder.pizzaName.setText(pizza.getPizzaName());
        myViewHolder.pizzaQuantity.setText(Integer.toString(pizza.getPizzaQuantity()));

        //Delete Item Per List Item
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemFromList(v, position);
            }
        });

        //onclick listener for the add button
        myViewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PizzaMenuAdapter pizzaMenuAdapter = new PizzaMenuAdapter(pizzaInCart);
                model_Pizza thisPizza = pizzaInCart.get(position);

                thisPizza.setPizzaQuantity(thisPizza.getPizzaQuantity() + 1);

                pizzaMenuAdapter.setNewPizzaQuantity(i, pizzaMenuAdapter.getPizzasInCart().get(i).getPizzaQuantity());
                //Convert The Pizaa Quantity
                String convertedQuantity = Integer.toString(thisPizza.getPizzaQuantity());

                myViewHolder.pizzaQuantity.setText(convertedQuantity);
                notifyDataSetChanged();

            }
        });

        //onclick listener for the minus button
        myViewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model_Pizza thisPizza = pizzaInCart.get(position);
                PizzaMenuAdapter pizzaMenuAdapter = new PizzaMenuAdapter(pizzaInCart);


                if (thisPizza.getPizzaQuantity() != 1) {
                    thisPizza.setPizzaQuantity(thisPizza.getPizzaQuantity() - 1);
                    pizzaMenuAdapter.setNewPizzaQuantity(i, pizzaMenuAdapter.getPizzasInCart().get(i).getPizzaQuantity());
                } else {
                    Toast.makeText(v.getContext(), "Cannot Have less Than One Pizza, Please Delete This Item From Your Cart To Remove.", Toast.LENGTH_SHORT).show();
                }
                //Convert The Pizaa Quantity
                String convertedQuantity = Integer.toString(thisPizza.getPizzaQuantity());

                myViewHolder.pizzaQuantity.setText(convertedQuantity);
                notifyDataSetChanged();

            }
        });
    }

    /**
     * function that allows Delete per item
     * @param v
     * @param i
     */
    private void deleteItemFromList(View v, final int i) {
        //create a Alert Dialouge
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        //show message on builder
        builder.setMessage("Delete Item? ").setCancelable(false).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PizzaMenuAdapter pizzaMenuAdapter = new PizzaMenuAdapter(pizzaInCart);

                pizzaMenuAdapter.deleteFromArrayList(i);
                notifyDataSetChanged();
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // DO NOTHING
                    }
                });

        builder.show();
    }

    /**
     * Gets the size of the array list PizzasInCart
     * @return pizzaInCart Size
     */
    @Override
    public int getItemCount() {
        return pizzaInCart.size();
    }

    /**
     * My custom view holder
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView pizzaName, pizzaQuantity;
        public ImageButton btnAdd, btnMinus, btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(R.id.cart_lblPizzaName);
            pizzaQuantity = itemView.findViewById(R.id.cart_lblQuantity);
            btnDelete = itemView.findViewById(R.id.cart_imgBtnRemove);
            btnAdd = itemView.findViewById(R.id.cart_imgBtnAdd);
            btnMinus = itemView.findViewById(R.id.cart_imgBtnMinus);
        }
    }
}
