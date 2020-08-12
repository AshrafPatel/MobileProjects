package com.example.android.projectweather;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LogAdapter extends ArrayAdapter<Logs> {

    public LogAdapter(Activity context, ArrayList<Logs> logs) {
        super(context, 0, logs);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.horizontal, parent, false);
        }
        Logs currentPos = getItem(position);
        TextView textCountry = (TextView) listItemView.findViewById(R.id.lCountry);
        TextView textCity = (TextView) listItemView.findViewById(R.id.lCity);
        TextView textDate = (TextView) listItemView.findViewById(R.id.lDate);
        TextView textTime = (TextView) listItemView.findViewById(R.id.lTime);
        TextView textTemp = (TextView) listItemView.findViewById(R.id.lTemp);

        textCountry.setText(currentPos.getCountry());
        textCity.setText(currentPos.getCity());
        textDate.setText(currentPos.getDate());
        textTime.setText(currentPos.getHour());
        textTemp.setText(currentPos.getTemp());
        return listItemView;
    }
}
