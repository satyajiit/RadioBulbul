package com.disha.radiobulbul.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.disha.radiobulbul.fragments.NoInternet;

public class ServiceManager {

    Context context;

    public ServiceManager(Context base) {
        context = base;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public boolean isConnectionAvailable(){


        if (!isNetworkAvailable()) {
            NoInternet fragError = new NoInternet();
            fragError.show(((AppCompatActivity) context).getSupportFragmentManager(), "NO NET");
                                                        //will redirect or start the no internet connection pop up

            return false;
        }

        return true;

    }

    //akshar issh dunia mein
    //ashar issh dunia mein


}