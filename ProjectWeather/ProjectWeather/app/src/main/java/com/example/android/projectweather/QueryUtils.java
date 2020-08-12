package com.example.android.projectweather;

import android.Manifest;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    public static Weather fetchWeatherData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        Weather myWeather = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return myWeather;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    /*After URL is created make a request to get info from online*/
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200 not 400),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the weather JSON results.", e);
        } finally {
            return jsonResponse;
        }
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server using buffered reader.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static Weather extractFeatureFromJson(String weatherJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(weatherJSON)) {
            return null;
        }
        Weather myWeather = null;
        try {
            JSONObject baseJsonResponse = new JSONObject(weatherJSON);
            String weatherPlaceName = baseJsonResponse.optString("name");
            double weatherTemp = baseJsonResponse.optJSONObject("main").optDouble("temp");
            String weatherCondition = baseJsonResponse.optJSONArray("weather").optJSONObject(0).optString("main");
            String weatherDesc = baseJsonResponse.optJSONArray("weather").optJSONObject(0).optString("description");
            String weatherCountry = baseJsonResponse.optJSONObject("sys").optString("country");
            myWeather = new Weather(weatherCountry, weatherPlaceName, weatherCondition, weatherDesc, weatherTemp);
            myWeather.setPicture(weatherDesc);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the weather JSON results", e);
        }
        return myWeather;
    }
}
