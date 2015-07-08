package com.example.zeballos.weatherapplication;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Zeballos on 7/7/15.
 */
public class HandleJSON {
    private String mainWeather;
    private String descriptionWeather;
    private String temperature;
    private String humidity;
    private String pressure;
    private String urlString = null;

    public ArrayList<String> listMainWeather;
    public ArrayList<String> listDescriptionWeather;
    public ArrayList<String> listTemperature;
    public ArrayList<String> listHumidity;
    public ArrayList<String> listPressure;


    public volatile boolean parsingComplete = false;
    /**
     *   Constructor
     */
    public HandleJSON(String url){
        this.urlString = url;

        listMainWeather = new ArrayList<String>();
        listDescriptionWeather = new ArrayList<String>();
        listTemperature = new ArrayList<String>();
        listHumidity = new ArrayList<String>();
        listPressure = new ArrayList<String>();

    }

    /**
     * Getters
     */
    public String getMainWeather() {
        return mainWeather;
    }
    public String getDescriptionWeather(){
        return descriptionWeather;
    }
    public String getTemperature(){
        return temperature;
    }
    public String getHumidity(){
        return humidity;
    }
    public String getPressure(){
        return pressure;
    }


    /**
     * Methods
     */
    public void fetchJSON(final int indexFrag) {
        Thread thread = new Thread (new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream stream = conn.getInputStream();
                    String data = convertStreamToString(stream);

                    readAndParseJSONCurrent(data, indexFrag);
                    stream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @SuppressLint("NewApi")
    public void readAndParseJSONCurrent(String in, int index) {
        parsingComplete = false;
        try{
            JSONObject reader = new JSONObject(in);
            if(index == 1){
                JSONArray jArr = reader.getJSONArray("weather");
                JSONObject jWeather = jArr.getJSONObject(0);
                mainWeather = jWeather.getString("main");
                descriptionWeather = jWeather.getString("description");

                JSONObject main  = reader.getJSONObject("main");
                temperature = main.getString("temp");
                temperature = ((Float.parseFloat(temperature))-273.15)+"°C";
                humidity = main.getString("humidity")+"%";
                pressure = main.getString("pressure")+"hPa";
            }else if(index == 2){
                JSONArray jList = reader.getJSONArray("list");
                JSONObject jDay;
                for(int i=0; i<5;i++){
                    jDay = jList.getJSONObject(i);

                    JSONObject jtemp = jDay.getJSONObject("temp");
                    listTemperature.add(jtemp.getString("day")+"K");

                    listPressure.add(jDay.getString("pressure")+"hPa");
                    listHumidity.add(jDay.getString("humidity")+"%");//
                    JSONArray jArr = jDay.getJSONArray("weather");
                    JSONObject jWeather = jArr.getJSONObject(0);
                    listMainWeather.add(jWeather.getString("main"));
                    listDescriptionWeather.add(jWeather.getString("description"));
                }
            }


            parsingComplete = true;

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
