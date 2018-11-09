package com.example.hp.weather;

/*
* The flow of the program is: MainActivity -> Dialog -> Weather -> TestCases. For more details related to program architecture see the sitemap for the application
* Input :  Input city entered by the user
* Output: Weather related to the city
* Icons pack for this app is taken from flaticons.com/pack
* */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.main_toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(mToolbar);

        try{
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String name = sharedPreferences.getString("cityName", "");
            if(name.trim().equals("")){
                savedCase();
            }else{
                unsavedCase();
            }
        }catch (Exception ex){
            Dialog newDialog = new Dialog(this);
            newDialog.showDialog(this,"error","Something went wrong","Please give access to this app to use internal storage.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TestCases cases = new TestCases(getApplicationContext());
        switch (item.getItemId()) {
            case R.id.action_favorite:
                Boolean checkInternetConnection = cases.checkNetworkConnection();
                if (checkInternetConnection){
                    Dialog newDialog = new Dialog(this);
                    newDialog.showDialog(this,"create","Location","Enter your location here");
                }else{
                    Dialog newDialog = new Dialog(this);
                    newDialog.showDialog(this,"internet","Something isn't right...","You are not connected to the internet, please switch on your internet and try again!");
                }
                return true;

            case R.id.action_refresh:
                savedCase();
                return true;

            case R.id.action_feedback:
                Dialog newDialog = new Dialog(this);
                newDialog.showDialog(this,"error","Feedback","This app is a part of assignment for Mobile Computing course. For any queries you can contact me at navneet.singh@dal.ca");
                return true;

            case R.id.action_help:
                Dialog helpDialog = new Dialog(this);
                helpDialog.showDialog(this,"error","Help","This is to justify that it follows Heuristic princliple by adding help and documentation. Just enter the name of the city and you can get the weather associated to that city");

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void searchLocationClick(View view){
        TestCases cases = new TestCases(getApplicationContext());
        Boolean checkInternetConnectionAvaliablility = cases.checkNetworkConnection();
        if(checkInternetConnectionAvaliablility) {
            Dialog newDialog = new Dialog(this);
            newDialog.showDialog(this, "create", "Location", "Enter your location here");
        }else{
            Dialog newDialog = new Dialog(this);
            newDialog.showDialog(this,"internet","Something isn't right...","You not connected to the internet.");
        }
    }

    public void loadUI(){
        try {
            TestCases cases = new TestCases(getApplicationContext());
            Boolean checkInternetConnectionAvaliablility = cases.checkNetworkConnection();
            if(checkInternetConnectionAvaliablility){
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String name = sharedPreferences.getString("cityName", "");
                Dialog newDialog = new Dialog(this);
                newDialog.refershDialog(this, name);
            }else{
                Dialog newDialog = new Dialog(this);
                newDialog.showDialog(this,"error","Something isn't right...","You are not connected to the internet, please switch on your internet and try again!");
            }
        }catch (Exception ex){
            Dialog newDialog = new Dialog(this);
            newDialog.showDialog(this,"error","Something isn't right...","You need to Select a location before pressing refresh.");
        }
    }

    public void unsavedCase(){
        CardView temperatureCard = findViewById(R.id.temperature);
        temperatureCard.setVisibility(View.GONE);

        CardView iconCard = findViewById(R.id.icon);
        iconCard.setVisibility(View.GONE);

        CardView minMaxCard = findViewById(R.id.min_max);
        minMaxCard.setVisibility(View.GONE);

        CardView descriptionCard = findViewById(R.id.description);
        descriptionCard.setVisibility(View.GONE);

        CardView detailDescriptionCard = findViewById(R.id.detail_description);
        detailDescriptionCard.setVisibility(View.GONE);

        CardView cloudCard = findViewById(R.id.cloud);
        cloudCard.setVisibility(View.GONE);

        CardView humidityCard = findViewById(R.id.humidity);
        humidityCard.setVisibility(View.GONE);

        CardView cardView = findViewById(R.id.error_message);
        cardView.setVisibility(View.GONE);

        this.loadUI();
    }

    public void savedCase(){
        this.loadUI();
        CardView progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        CardView cardView = findViewById(R.id.error_message);
        cardView.setVisibility(View.GONE);

        CardView temperatureCard = findViewById(R.id.temperature);
        temperatureCard.setVisibility(View.GONE);

        CardView iconCard = findViewById(R.id.icon);
        iconCard.setVisibility(View.GONE);

        CardView minMaxCard = findViewById(R.id.min_max);
        minMaxCard.setVisibility(View.GONE);

        CardView descriptionCard = findViewById(R.id.description);
        descriptionCard.setVisibility(View.GONE);

        CardView detailDescriptionCard = findViewById(R.id.detail_description);
        detailDescriptionCard.setVisibility(View.GONE);

        CardView cloudCard = findViewById(R.id.cloud);
        cloudCard.setVisibility(View.GONE);

        CardView humidityCard = findViewById(R.id.humidity);
        humidityCard.setVisibility(View.GONE);
    }
}
