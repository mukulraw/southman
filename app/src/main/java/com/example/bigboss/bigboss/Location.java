package com.example.bigboss.bigboss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Location extends AppCompatActivity {


    Spinner spinner;

    List<String> list = new ArrayList<>();

    String li;

    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        list = new ArrayList<>();

        list.add("Delhi");
        list.add("Punjab");
        list.add("Gurgaon");
        list.add("Bathinda");
        list.add("South");

        spinner = findViewById(R.id.spinner);


        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Location.this , MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
