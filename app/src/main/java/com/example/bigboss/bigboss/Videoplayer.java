package com.example.bigboss.bigboss;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigboss.bigboss.TillSubCategory2.TillSubCatBean;
import com.example.bigboss.bigboss.VideoUrlPOJO.VideourlBean;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Videoplayer extends YouTubeBaseActivity {

    Toolbar toolbar;

    YouTubePlayerView youTubePlayerView;

    YouTubePlayer.OnInitializedListener onInitializedListener;

    ImageView back;

    String id;

    ProgressBar bar;

    TextView  ingr;

    Button order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);


        id = getIntent().getStringExtra("id");
        toolbar = findViewById(R.id.toolbar);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

        bar = findViewById(R.id.progress);
       // rec = findViewById(R.id.receipe);
        ingr = findViewById(R.id.ingredients);
        order = findViewById(R.id.order);

        youTubePlayerView = findViewById(R.id.videoplayer);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo("G0Hx6uN2AJE");

              //  https://www.youtube.com/watch?v=G0Hx6uN2AJE

                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youTubePlayerView.initialize(Youtube.PlayerConfig.API_KEY, onInitializedListener);


        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<VideourlBean> call = cr.video(id);

        call.enqueue(new Callback<VideourlBean>() {
            @Override
            public void onResponse(Call<VideourlBean> call, Response<VideourlBean> response) {


                if (Objects.equals(response.body().getStatus(), "1")) {

                   // rec.setText(response.body().getData().get(0).getSmallDesc());
                    ingr.setText(response.body().getData().get(0).getDescription());

                } else {

                    Toast.makeText(Videoplayer.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<VideourlBean> call, Throwable t) {
                bar.setVisibility(View.GONE);

            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Videoplayer.this);

                dialog.setContentView(R.layout.dialog);

                dialog.setCancelable(true);

                dialog.show();

                TextView code = dialog.findViewById(R.id.code);

                TextView mobile = dialog.findViewById(R.id.mobile);

                Button watshp = dialog.findViewById(R.id.whatsapp);

                Button call = dialog.findViewById(R.id.call);

                watshp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();
                    }
                });

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();
                    }
                });
            }
        });


    }

    public class PlayerConfig {

        PlayerConfig() {
        }

        public static final String API_KEY =
                "AIzaSyCB_Qx_NUNn1YL-jMoXZfG4j8xJDAOtlBo";
    }
}
