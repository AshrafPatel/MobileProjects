package com.example.android.projectweather;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class LogActivity extends AppCompatActivity {
    AssetManager manger;
    String line = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listvertical);
        File externalDir = Environment.getExternalStorageDirectory();
        ArrayList<Logs> logs = new ArrayList<Logs>();
        try {
            BufferedReader input = new BufferedReader(new FileReader(externalDir.getAbsolutePath() + File.separator + "getWeatherLog.txt"));
            if (!input.ready()) {
                throw new IOException();
            }
            while ((line = input.readLine()) != null) {
                String rowData[] = line.split(",");
                logs.add(new Logs(rowData[0], rowData[1], rowData[2], rowData[3], rowData[4]));
            }
            input.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        LogAdapter logAdapterObject = new LogAdapter(LogActivity.this, logs);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(logAdapterObject);
    }
}
