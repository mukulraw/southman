package com.example.bigboss.bigboss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bigboss.bigboss.locationPOJO.locationBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Location extends AppCompatActivity {


    Spinner spinner;

    List<String> list = new ArrayList<>();
    List<String> lid = new ArrayList<>();


    String li;

    Button submit;


    String sel = "";

    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        list = new ArrayList<>();
        lid = new ArrayList<>();


        spinner = findViewById(R.id.spinner);
        progress = findViewById(R.id.progress);


        Bean b = (Bean) getApplicationContext();

        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<locationBean> call = cr.getLocations();

        call.enqueue(new Callback<locationBean>() {
            @Override
            public void onResponse(Call<locationBean> call, Response<locationBean> response) {


                if (response.body().getStatus().equals("1")) {

                    list.clear();
                    lid.clear();

                    list.add("Choose Location");

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        list.add(response.body().getData().get(i).getName());
                        lid.add(response.body().getData().get(i).getId());
                    }


                    ArrayAdapter dataAdapter = new ArrayAdapter(Location.this, android.R.layout.simple_spinner_item, list);

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(dataAdapter);


                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<locationBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    sel = lid.get(position - 1);
                    submit.setClickable(true);
                    submit.setEnabled(true);
                }
                else
                {
                    submit.setClickable(false);
                    submit.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sel.length() > 0) {
                    SharePreferenceUtils.getInstance().saveString("location" , sel);
                    Intent i = new Intent(Location.this, MainActivity.class);
                    i.putExtra("lname" , spinner.getSelectedItem().toString());
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Location.this, "Please choose a location", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
