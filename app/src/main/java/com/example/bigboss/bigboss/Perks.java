package com.example.bigboss.bigboss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cooltechworks.views.ScratchTextView;
import com.example.bigboss.bigboss.winnersPOJO.winnersBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Perks extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;

    Button submit;
    ScratchTextView scratch;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perks);

        toolbar = findViewById(R.id.toolbar);
        submit = findViewById(R.id.submit);
        scratch = findViewById(R.id.scratch);
        phone = findViewById(R.id.phone);

        progress = findViewById(R.id.progress);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.setTitle("Perks");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ph = phone.getText().toString();

                if (ph.length() == 10) {
                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                    Call<winnersBean> call = cr.getPerks(ph);
                    call.enqueue(new Callback<winnersBean>() {
                        @Override
                        public void onResponse(Call<winnersBean> call, Response<winnersBean> response) {

                            scratch.setText(response.body().getMessage());

                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<winnersBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });
                }

            }
        });


    }
}
