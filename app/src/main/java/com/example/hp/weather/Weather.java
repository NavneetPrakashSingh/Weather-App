package com.example.hp.weather;

/*
* This is where the main logic beings
* Input: city name
* Output: updating elements on main activity according to the data recieved in the app
* */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Weather extends AppCompatActivity {
    Map<String,String> weatherMap = new HashMap<>();
    Map<String,String> icons = new HashMap<>();
    Map<String,String> backgroundColorList = new HashMap<>();
    Map<String,String> cardColor = new HashMap<>();

    public static final String MYPREFERENCE = "MyPrefs";
    SharedPreferences sharedPreferences;
    public Context context;
    public Activity activity;

    public Weather(Context _context, Activity _activity){
        this.context = _context;
        this.activity = _activity;
    }

    public void weatherContent(String city){
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=2f3363171ca813b5d43fb8fcc33c2030";
        Log.i("11111", url);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String cityName = response.getString("name");
                            JSONArray weatherNode = response.getJSONArray("weather");
                            String weatherDescription ="-";
                            String weatherMain = "-";
                            String weatherIcon ="-";
                            for (int i =0;i<weatherNode.length();i++){
                                JSONObject jsonObject = weatherNode.getJSONObject(i);
                                weatherDescription = jsonObject.getString("main");
                                weatherMain = jsonObject.getString("description");
                                weatherIcon = jsonObject.getString("icon");
                            }

                            int currentTemperature = 0;
                            int maximumTemperature = 0;
                            int minimumTemperature = 0;
                            JSONObject temperatureNode = response.getJSONObject("main");
                            currentTemperature =(int)temperatureNode.getDouble("temp");
                            maximumTemperature = (int)temperatureNode.getDouble("temp_max");
                            minimumTemperature = (int)temperatureNode.getDouble("temp_min");
                            Integer humidity = temperatureNode.getInt("humidity");

                            JSONObject clouds = response.getJSONObject("clouds");
                            Integer cloudPercentage = clouds.getInt("all");

                            ScrollView sView = activity.findViewById(R.id.outer_scroll_view);
                            Toolbar mToolbar = activity.findViewById(R.id.main_toolbar);
                            RelativeLayout mainRelativelayout = activity.findViewById(R.id.main_relative_layout);
                            TextView tvTemperature = activity.findViewById(R.id.temperature_text);
                            TextView minMax = activity.findViewById(R.id.min_max_text);
                            TextView description = activity.findViewById(R.id.description_text);
                            TextView detailDescription = activity.findViewById(R.id.detail_description_text);
                            TextView cloudTextView = activity.findViewById(R.id.cloud_text);
                            TextView humidityTextView = activity.findViewById(R.id.humidity_text);
                            ImageView iconImage = activity.findViewById(R.id.icon_image);
                            CardView temperature = activity.findViewById(R.id.temperature);




                            //TODO :insert value in database
                            tvTemperature.setText(String.valueOf(currentTemperature) + (char) 0x00B0 + "C");
                            minMax.setText("Min: "+String.valueOf(minimumTemperature)+ (char) 0x00B0 + "C" +" Max: "+String.valueOf(maximumTemperature)+ (char) 0x00B0 + "C");
                            description.setText(weatherMain);
                            detailDescription.setText(weatherDescription);
                            cloudTextView.setText("Clouds: "+cloudPercentage+" %");
                            humidityTextView.setText("Humidity: "+humidity+ " %");

                            icons.put("01d","clear_sky");
                            icons.put("01n","clear_sky");
                            icons.put("02d","few_cloud");
                            icons.put("02n","few_cloud");
                            icons.put("03d","scattered_clouds");
                            icons.put("03n","scattered_clouds");
                            icons.put("04d","broken_clouds");
                            icons.put("04n","broken_clouds");
                            icons.put("09d","shower_rain");
                            icons.put("09n","shower_rain");
                            icons.put("10d","rain");
                            icons.put("10n","rain");
                            icons.put("11d","thunderstorm");
                            icons.put("11n","thunderstorm");
                            icons.put("13d","snow");
                            icons.put("13n","snow");
                            icons.put("50d","mist");
                            icons.put("50n","mist");


                            backgroundColorList.put("01d","colorClearSkyBackground");
                            backgroundColorList.put("01n","colorClearSkyBackground");
                            backgroundColorList.put("02d","colorCloud");
                            backgroundColorList.put("02n","colorCloud");
                            backgroundColorList.put("03d","colorCloud");
                            backgroundColorList.put("03n","colorCloud");
                            backgroundColorList.put("04d","colorCloud");
                            backgroundColorList.put("04n","colorCloud");
                            backgroundColorList.put("09d","rainy");
                            backgroundColorList.put("09n","rainy");
                            backgroundColorList.put("10d","rainy");
                            backgroundColorList.put("10n","rainy");
                            backgroundColorList.put("11d","rainy");
                            backgroundColorList.put("11n","rainy");
                            backgroundColorList.put("13d","colorSnow");
                            backgroundColorList.put("13n","colorSnow");
                            backgroundColorList.put("50d","colorMistBackground");
                            backgroundColorList.put("50n","colorMistBackground");

                            cardColor.put("01d","colorClearSkyCard");
                            cardColor.put("01n","colorClearSkyCard");
                            cardColor.put("02d","colorCardCloud");
                            cardColor.put("02n","colorCardCloud");
                            cardColor.put("03d","colorCardCloud");
                            cardColor.put("03n","colorCardCloud");
                            cardColor.put("04d","colorCardCloud");
                            cardColor.put("04n","colorCardCloud");
                            cardColor.put("09d","rainyCard");
                            cardColor.put("09n","rainyCard");
                            cardColor.put("10d","rainyCard");
                            cardColor.put("10n","rainyCard");
                            cardColor.put("11d","rainyCard");
                            cardColor.put("11n","rainyCard");
                            cardColor.put("13d","colorCardSnow");
                            cardColor.put("13n","colorCardSnow");
                            cardColor.put("50d","colorMistCard");
                            cardColor.put("50n","colorMistCard");

                            String iconName = icons.get(weatherIcon);
                            String backgroundColor = backgroundColorList.get(weatherIcon);
                            String cardColorValue = cardColor.get(weatherIcon);

                            int imageId = context.getResources().getIdentifier("drawable/"+iconName,null,context.getPackageName());

                            iconImage.setImageResource(imageId);

                            int desiredBackgroundColor = context.getResources().getColor(context.getResources().getIdentifier(backgroundColor, "color", context.getPackageName()));
                            mainRelativelayout.setBackgroundColor(desiredBackgroundColor);
                            mToolbar.setTitle(cityName);
                            mToolbar.setBackgroundColor(desiredBackgroundColor);

                            int desiredCardColor = context.getResources().getColor(context.getResources().getIdentifier(cardColorValue, "color", context.getPackageName()));
                            tvTemperature.setBackgroundColor(desiredCardColor);
                            minMax.setBackgroundColor(desiredCardColor);
                            description.setBackgroundColor(desiredCardColor);
                            detailDescription.setBackgroundColor(desiredCardColor);
                            cloudTextView.setBackgroundColor(desiredCardColor);
                            humidityTextView.setBackgroundColor(desiredCardColor);
                            temperature.setCardBackgroundColor(desiredCardColor);
                            sView.setBackgroundColor(desiredBackgroundColor);

                            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("cityName",cityName);
                            editor.apply();

                            CardView temperatureCard = activity.findViewById(R.id.temperature);
                            temperatureCard.setVisibility(View.VISIBLE);

                            CardView iconCard = activity.findViewById(R.id.icon);
                            iconCard.setVisibility(View.VISIBLE);

                            CardView minMaxCard = activity.findViewById(R.id.min_max);
                            minMaxCard.setVisibility(View.VISIBLE);

                            CardView descriptionCard = activity.findViewById(R.id.description);
                            descriptionCard.setVisibility(View.VISIBLE);

                            CardView detailDescriptionCard = activity.findViewById(R.id.detail_description);
                            detailDescriptionCard.setVisibility(View.VISIBLE);

                            CardView cloudCard = activity.findViewById(R.id.cloud);
                            cloudCard.setVisibility(View.VISIBLE);

                            CardView humidityCard = activity.findViewById(R.id.humidity);
                            humidityCard.setVisibility(View.VISIBLE);

                            CardView cardView = activity.findViewById(R.id.error_message);
                            cardView.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            Dialog errorMessage = new Dialog(activity);
                            errorMessage.showDialog(activity,"error","Something Isn't Right...","Unfortunately, something went wrong, please try again after some time...");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Dialog errorMessage = new Dialog(activity);
                errorMessage.showDialog(activity,"error","Something Isn't Right...","Unfortunately, we don't have any location for this city, please try again with some other city...");
            }
        }
        );
        RequestQueueSingleton.getmInstance(context).addToRequestQueue(request);
    }
}
