//package com.example.nikko.ordersystem;
//
//import android.app.Activity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.nikko.ordersystem.models.model_Pizza;
//
//import java.util.ArrayList;
//
//public class TestGridAdapter extends BaseAdapter {
//
//    public static Activity activity;
//    //    ArrayList pizzaNames;
//   // ArrayList<model_Pizza> model_pizzas;
//    ArrayList<model_Pizza> pizzasInCart;
//    model_Pizza pizzaModel;
//
//    public TestGridAdapter(Activity activity, ArrayList<model_Pizza> pizzas) {
//        this.pizzasInCart = pizzas;
//        this.activity = activity;
////        pizzasInCart = new ArrayList<>();
//    }
//
//    @Override
//    public int getCount() {
//        return pizzasInCart.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return pizzasInCart.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//
//        if (convertView == null) {
//            LayoutInflater inflator = LayoutInflater.from(activity);
//            convertView = inflator.inflate(R.layout.test_gridmenu, null);
//
//        }
//
//        ImageView imageViewPizza = convertView.findViewById(R.id.testimgViewPizza);
//        TextView txtPizzaPrice = convertView.findViewById(R.id.testtxtTemplatePizzaPrice);
//        TextView txtPizzaName = convertView.findViewById(R.id.testtxtTemplatePizzaName);
//        Button btnAddToCart = convertView.findViewById(R.id.testbtnTemplateAddToCart);
//
//        for (model_Pizza piz : pizzasInCart){
//            imageViewPizza.setImageResource(pizzasInCart.get(position).getPizzaImage());
//            txtPizzaPrice.setText(pizzasInCart.get(position).getPizzaPrice());
//            txtPizzaName.setText(pizzasInCart.get(position).getPizzaName());
//
//        }
//
//
//        return convertView;
//    }
//
//    public ArrayList<model_Pizza> getPizzasInCart() {
//       // for (String pizza: pizzasInCart) {
//      //      System.out.println("Pizza Name : " + pizza.getPizzaName() +  "Pizza Quantity : " + pizza.getPizzaQuantity() + "\n" );
//    //    }
//        return pizzasInCart;
//    }
//}
