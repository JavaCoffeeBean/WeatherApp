package com.example.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView weatherTemp;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherTemp = findViewById(R.id.temperature);
        Button buttonParse = findViewById(R.id.engage);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });
    }

    private void jsonParse(){


        String url = "http://api.openweathermap.org/data/2.5/weather?zip=77099,us&APPID=79143c2f91de098f1ab2ca576814fc8d";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("main");

                            for (int i = 0; i < jsonObject.length(); i++) {
//                                JSONObject main_Array = jsonObject.getJSONObject();

                                int temp = jsonObject.getInt("temp");
                                int temp_min = jsonObject.getInt("temp_min");
                                int temp_max = jsonObject.getInt("temp_max");

                                double tempF = (temp - 273.15) * 9/5 + 32;
                                double temp_minF = (temp_min - 273.15) * 9/5 + 32;
                                double temp_maxF = (temp_max - 273.15) * 9/5 + 32;

                                int tempF2 = (int) tempF;
                                int temp_minF2 = (int) temp_minF;
                                int temp_maxF2 = (int) temp_maxF;



                                weatherTemp.append(String.valueOf(tempF2));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

}
