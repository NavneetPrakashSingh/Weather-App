package com.example.hp.weather;

/*
* Shows dialog for the activity, on a different layer so that it can be accessable anywhere
* Input: context, source, title, main message
* Output: Displays alert with the input content
* */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Dialog extends AppCompatActivity {

    public TextView view;
    public Activity activity;

    public Dialog(Activity _activity){
        this.activity = _activity;
    }

    public void refershDialog(final Context context, String cityName){
        try{
            ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("Loading Content Please Wait...");
            pd.show();


            Weather currentWeather = new Weather(context,activity);
            currentWeather.weatherContent(cityName);

            pd.dismiss();
        }catch (Exception ex){

        }
    }

    public void showDialog(final Context context, final String source, String title, String mainMessage){
        AlertDialog.Builder builder;

        final EditText todoInput = new EditText(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }

        if(source == "create"){

            LinearLayout container = new LinearLayout(context);
            container.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(80, 0, 80, 0);


            todoInput.setId(R.id.popup_text);
            todoInput.setLayoutParams(lp);

            todoInput.setInputType(InputType.TYPE_CLASS_TEXT);
            container.addView(todoInput);

            builder.setView(container);
        }
        builder.setTitle(title)
                .setMessage(mainMessage)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if(source =="create"){
                            ProgressDialog pd = new ProgressDialog(context);
                            pd.setMessage("Loading Content Please Wait...");
                            pd.show();

                            Weather currentWeather = new Weather(context,activity);
                            String cityName = String.valueOf(todoInput.getText());

                            TestCases checkCityName = new TestCases(context);
                            if(checkCityName.checkCityNameIsNotNull(cityName.trim())){
                                currentWeather.weatherContent(cityName);
                            }else{
                                Dialog newDialog = new Dialog(activity);
                                newDialog.showDialog(activity,"error","Something Isn't Right","City name entered cannot be empty...");
                            }
                            pd.dismiss();
                        }else if(source =="error"){
                            Dialog newDialog = new Dialog(activity);
                            newDialog.showDialog(activity,"create","Location","Enter your location here");
                        }

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setIcon(android.R.drawable.ic_menu_mylocation)
                .show();
    }
}
