package com.example.hp.weather;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
* Covering corner cases in a sperate file
* Input : context
* Output: check if city is empty, check if internet connection is active or not
* */

public class TestCases extends MainActivity {

    public Context context;

    public TestCases(Context _context){
        context = _context;
    }

    public boolean checkCityNameIsNotNull(String input){
        if (input.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
