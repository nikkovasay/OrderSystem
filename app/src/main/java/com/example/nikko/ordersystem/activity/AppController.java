package com.example.nikko.ordersystem.activity;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static AppController appController;
    public static RequestQueue requestQueue;
    public static final String TAG= AppController.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        appController  = this;
        Toast.makeText(this,"df", Toast.LENGTH_SHORT).show();
    }

    //RequestQueue - A queue containing the network / http requests that need to be made
    public static synchronized AppController getAppController(){
        appController=appController;
        return appController;
    }
    //carrier of request
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }
    //managing request by adding tag to each
    public <T> void addRequestToQueue(Request<T> request, String tag){
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }
}
