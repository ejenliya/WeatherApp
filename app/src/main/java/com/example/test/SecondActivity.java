package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {

    ListView listView;
    ListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.weather);
        Bundle arguments = getIntent().getExtras();

        String city = arguments.getString("city");
        setTitle(city);

        String key = "547a038b25c2ef1cbc7768ce924ff907";
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&lang=ru&units=metric&appid=" + key;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this::parseJSON, error -> {});

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void parseJSON(String jsonResponse){
        Response res = null;
        ArrayList<HashMap<String, String>> info = null;
        try {
            res = new Response(jsonResponse);
            info = res.getInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new MyAdapter(this, info, R.layout.list_item_row,
                new String[]{"description","day","time", "temp", "possibility", "wind"},
                new int[]{R.id.description, R.id.day, R.id.time, R.id.temp, R.id.possibility, R.id.wind});

        listView.setAdapter(adapter);
    }
}