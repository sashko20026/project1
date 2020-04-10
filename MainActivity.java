package com.example.kursova;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private EditText editTextCity;
    private  EditText editTextNumber;
    private Spinner spinnerCounties;
    private static int NumberOfDays;
    private static String city;
    private static int id;
    private static String number;

    private static ArrayList<WeatherDay> weatherDays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextCity = findViewById(R.id.editTextCity);
        editTextNumber=findViewById(R.id.editTextNumber);
        spinnerCounties=findViewById(R.id.spinner);
    }

    public void onClickCheckWeather(View view) throws ExecutionException, InterruptedException {
        city = editTextCity.getText().toString().trim();
        id =spinnerCounties.getSelectedItemPosition();
        number=editTextNumber.getText().toString();

        try {
            if (!city.isEmpty()&& !number.isEmpty()&&(Integer.parseInt(number)>0 && Integer.parseInt(number)<17)) {
                NumberOfDays=Integer.parseInt(editTextNumber.getText().toString());
                String[] countries={"None","AT","AR","BR","CA","CN","HR","CZ","EG","FR","DE","GR","IT","JP","MX","ME","NL","PL","PT","RU","ES","UA","UK","US"};

                String country=countries[id];

                String query="";
                if (country=="None") {
                    query = String.format("https://api.weatherbit.io/v2.0/forecast/daily?city=%s&key=4e2d6343c40d4652b80a70190ff5ac87", city);
                } else {
                    query = String.format("https://api.weatherbit.io/v2.0/forecast/daily?city=%s&country=%s&key=4e2d6343c40d4652b80a70190ff5ac87", city,country);
                }
                weatherDays=new ArrayList<>();
                DownloadTask task = new DownloadTask(this);
                task.execute(query);
            } else {
                Toast.makeText(MainActivity.this, "All fields should be filled correctly", Toast.LENGTH_SHORT).show();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "All fields should be filled correctly", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "All fields should be filled correctly", Toast.LENGTH_SHORT).show();
        }
    }
    public static class DownloadTask extends AsyncTask<String, Void, String> {
        private Context context;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader breader = new BufferedReader(reader);
                String line = breader.readLine();
                while (line != null) {
                    result.append(line);
                    line = breader.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.isEmpty()) {
                Toast.makeText(context, "This city does not exist", Toast.LENGTH_SHORT).show();
            } else {
                super.onPostExecute(s);
                try {
                    StringBuilder builder = new StringBuilder();
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < NumberOfDays; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String date = object.getString("datetime");
                        String mintemp = object.getString("min_temp");
                        String maxtemp = object.getString("max_temp");
                        String descr = object.getJSONObject("weather").getString("description");
                        String result = date + ",." + mintemp + ",." + maxtemp + ",." + descr;
                        WeatherDay weatherDay = new WeatherDay(result);
                        weatherDays.add(weatherDay);
                    }
                        Intent intent = new Intent(context, Main2Activity.class);
                        intent.putExtra("city", city);
                        intent.putExtra("array", (Serializable)weatherDays);
                        context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


