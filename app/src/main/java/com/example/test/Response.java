package com.example.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Response{
    private JSONObject jsonObject;
    private ArrayList<HashMap<String, String>> info = new ArrayList<>();
    private JSONArray array;

    public Response(String jsonResponse) throws JSONException {
        jsonObject = new JSONObject(jsonResponse);
        array = jsonObject.getJSONArray("list");
    }

    public ArrayList<HashMap<String, String>> getInfo() throws JSONException {
        for (int i = 0; i < array.length(); i++) {
            JSONObject jo = array.getJSONObject(i);

            int temp = (int) jo.getJSONObject("main").getDouble("temp");
            int possibility = (int) (jo.getDouble("pop") * 100);
            String wind = String.valueOf(jo.getJSONObject("wind").getLong("speed"));
            Date date = new Date(jo.getInt("dt") * 1000L);
            String time = new SimpleDateFormat("HH:mm").format(date);
            String day = new SimpleDateFormat("E").format(date);
            String description = jo.getJSONArray("weather").getJSONObject(0).getString("description");

            HashMap<String, String> item = new HashMap<>();
            item.put("temp", "Температура: " + temp + " °C");
            item.put("possibility", "Вероятность осадков: " + possibility + "%");
            item.put("wind", "Ветер: " + wind + " м/с");
            item.put("time", time);
            item.put("day", day);
            item.put("description", description);

            info.add(item);
        }
        return info;
    }
}
