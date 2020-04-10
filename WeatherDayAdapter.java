package com.example.kursova;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherDayAdapter extends  RecyclerView.Adapter<WeatherDayAdapter.WeatherDayViewHolder> {

    private ArrayList<WeatherDay> weatherDays;
    public WeatherDayAdapter(ArrayList<WeatherDay> weatherDays) { this.weatherDays=weatherDays;}


    @NonNull
    @Override
    public WeatherDayAdapter.WeatherDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weatherday, parent, false);
        return new WeatherDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDayAdapter.WeatherDayViewHolder holder, int position) {
        WeatherDay weatherDay = weatherDays.get(position);
        holder.textViewDate.setText("Date: "+ weatherDay.getDate());
        holder.textViewMinTemp.setText(weatherDay.getMinTemp());
        holder.textViewMaxTemp.setText(weatherDay.getMaxTemp());
        holder.textViewDescr.setText("Description: "+weatherDay.getDescr());
    }

    @Override
    public int getItemCount() {
        return weatherDays.size();
    }

    class WeatherDayViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDate;
        private TextView textViewMinTemp;
        private TextView textViewMaxTemp;
        private TextView textViewDescr;

        public WeatherDayViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate=itemView.findViewById(R.id.textViewDate);
            textViewMinTemp=itemView.findViewById(R.id.textViewMinTemp);
            textViewMaxTemp=itemView.findViewById(R.id.textViewMaxTemp);
            textViewDescr=itemView.findViewById(R.id.textViewDescr);
        }
    }
}
