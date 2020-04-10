package com.example.kursova;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;

import android.widget.TextView;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {

    private static ArrayList<WeatherDay> weatherDays;
    private static RecyclerView recyclerView;
    private WeatherDayAdapter weatherDayAdapter;
    private static TextView textView;
    private static int numberOfDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView=findViewById(R.id.recyclerView);
        textView=findViewById(R.id.textView);
        Intent intent = getIntent();
        String city=intent.getStringExtra("city");
        weatherDays=(ArrayList<WeatherDay>)intent.getSerializableExtra("array");
        String text=getString(R.string.forecast)+" "+city;
        textView.setText(text);
        weatherDayAdapter=new WeatherDayAdapter(weatherDays);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(weatherDayAdapter);
    }
}