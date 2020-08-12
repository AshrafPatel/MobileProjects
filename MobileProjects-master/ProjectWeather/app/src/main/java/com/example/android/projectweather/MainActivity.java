package com.example.android.projectweather;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    double longitude = -79.605497578;
    double latitude = 43.724330436;
    String country, city = null;
    private static String WEATHER_REQUEST_URL = null;
    private static String CUSTOM_URL_ADDRESS = null;
    public static boolean usingCustomAddress = false;
    File textFile;
    Button changeLoco;

    @RequiresApi(api = VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        savedInstanceState = getIntent().getExtras();
        if (savedInstanceState != null) {
            country = savedInstanceState.getString(Places.MY_PLACE_COUNTRY, "");
            city = savedInstanceState.getString(Places.MY_PLACE_CITY, "");
            CUSTOM_URL_ADDRESS = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&appid=08804d11e8647bee76fd823689cfb845";
            usingCustomAddress = true;
        }
        changeLoco = (Button) this.findViewById(R.id.locoButt);
        changeLoco.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent placeIntent = new Intent(getApplicationContext(), Places.class);
                startActivity(placeIntent);
            }
        });
        WeatherAsyncTask weatherTask = new WeatherAsyncTask();
        if (CUSTOM_URL_ADDRESS == null) {
            WEATHER_REQUEST_URL = "https://api.openweathermap.org/data/2.5/weather?q=Mississauga&appid=08804d11e8647bee76fd823689cfb845";
            weatherTask.execute(WEATHER_REQUEST_URL);
        }
        else {
            weatherTask.execute(CUSTOM_URL_ADDRESS);
        }
        String state = Environment.getExternalStorageState();

        // If you use Android OS version >= than 6.0,
        // you also need to require above two external storage permissions at run time in Java:
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(permissions, 1); // 1 = WRITE_REQUEST_CODE


        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(MainActivity.this, "No external storage mounted", Toast.LENGTH_SHORT).show();;
        }
        else {
            File externalDir = Environment.getExternalStorageDirectory();
            textFile = new File(externalDir.getAbsolutePath()
                    + File.separator + "getWeatherLog.txt");
        }
    }

    public void updateUi(Weather weather) throws IOException {
        ImageView myImage = (ImageView) this.findViewById(R.id.pic);
        myImage.setImageResource(weather.getPicture());
        TextView wLocation = this.findViewById(R.id.wPlace);
        TextView wCountry = this.findViewById(R.id.wCountry);
        TextView wDescription = this.findViewById(R.id.weatherDesc);
        TextView wCondition = this.findViewById(R.id.weatherCondition);
        TextView wTemp = this.findViewById(R.id.weatherTemp);
        if (!textFile.exists()) {
            textFile.createNewFile();
        }
        FileWriter fw = new FileWriter(textFile,true);
        BufferedWriter bw = new BufferedWriter(fw);
        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String content = weather.getPlaceName() + ", " + weather.getCountry() + ", " + date + ", " + time + ", " + weather.getTemp() + "\n";
        bw.write(content);
        bw.close();
        wLocation.setText(weather.getPlaceName());
        wCountry.setText(weather.getCountry());
        wDescription.setText(weather.getDesc());
        wCondition.setText(weather.getMain());
        wTemp.setText(String.format("%.1f", weather.getTemp()) + "°C");

        Toast.makeText(MainActivity.this, R.string.weather_updated, Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_log){
            Intent intentLog = new Intent(this, LogActivity.class);
            startActivity(intentLog);
        }
        else if (id==R.id.menu_about) {
            Intent intentAbout = new Intent(this, AboutActivity.class);
            startActivity(intentAbout);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Places.MY_PLACE_COUNTRY, country);
        outState.putString(Places.MY_PLACE_CITY, city);
    }

    public class WeatherAsyncTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... strings) {
            Weather myWeather = QueryUtils.fetchWeatherData(strings[0]);
            System.out.println(myWeather);
            return myWeather;
        }


        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            try {
                updateUi(weather);
            }
            catch (Exception e) {
                Log.e("WeatherFragment", "Couldn't get data URL was invalid");
                Toast.makeText(MainActivity.this, R.string.please_enter_info, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    // For new devices, Android recommended to handle permissions
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            // 1 == WRITE_REQUEST_CODE
            case 1:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Granted.
                }
                else{
                    Toast.makeText(MainActivity.this, "Permissions denied \n Could not write to file", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}