package com.example.android.projectweather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class Places extends AppCompatActivity {
    private Button locateButton;
    private Button submitButton;
    private EditText cText;
    private EditText citText;
    private EditText addressText;
    Context thiscontext;
    int PLACE_PICKER_REQUEST = 1;

    public static final String MY_PLACE_COUNTRY = "MY_PLACE_COUNTRY";
    public static final String MY_PLACE_CITY = "MY_PLACE_CITY";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_layout);
        cText = (EditText) this.findViewById(R.id.getCountry);
        citText = (EditText) this.findViewById(R.id.getCity);
        submitButton = (Button) this.findViewById(R.id.submitPlace);
        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String country = cText.getText().toString();
                String city = citText.getText().toString();

                if (country.equals("") || city.equals("")) {
                    Toast.makeText(Places.this, R.string.please_enter_info, Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(Places.this, MainActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString(MY_PLACE_COUNTRY, country);
                    bundle.putString(MY_PLACE_CITY, city);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            }
        });
    }
}

