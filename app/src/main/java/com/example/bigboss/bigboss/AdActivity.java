package com.example.bigboss.bigboss;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.net.MalformedURLException;
import java.net.URL;

public class AdActivity extends YouTubeBaseActivity  implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer player;

    String url , diff;
    TextView timerr , nnn;

Button quit;

    String userId , playId , name , phone , image , title , price , brand , color , size , negotiable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad2);

        url = getIntent().getStringExtra("url");
        diff = getIntent().getStringExtra("diff");
        userId = getIntent().getStringExtra("userid");
        playId = getIntent().getStringExtra("playId");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");
        price = getIntent().getStringExtra("price");
        brand = getIntent().getStringExtra("brand");
        color = getIntent().getStringExtra("color");
        size = getIntent().getStringExtra("size");
        negotiable = getIntent().getStringExtra("negotiable");

        youTubePlayerView = findViewById(R.id.youTubePlayerView);
        quit = findViewById(R.id.button);
        nnn = findViewById(R.id.textView21);
        timerr = findViewById(R.id.textView18);

        youTubePlayerView.initialize("AIzaSyBJuWOg3svNvIVR4qt0q1GDsETF6SrUExQ", this);

        nnn.setText(name);

        startImageTimer();


        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(AdActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.quit_dialog_layout);
                dialog.show();

                Button ookk = dialog.findViewById(R.id.button2);
                Button canc = dialog.findViewById(R.id.button4);

                canc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                ookk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();

                    }
                });


            }
        });

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {

            this.player = youTubePlayer;

            try {

                //youTubePlayer.loadVideo("GqbNL4XGT4U");

                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

                youTubePlayer.loadVideo(extractYoutubeId(url));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    public String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }

    void startImageTimer() {
        CountDownTimer timer = new CountDownTimer((long) Float.parseFloat(diff) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timerr.setText("Play begins in " + getDurationString(Integer.parseInt(String.valueOf(millisUntilFinished / 1000))));

            }

            @Override
            public void onFinish() {

                Intent i = new Intent(AdActivity.this , Playitem.class);
                //Intent i = new Intent(getContext(), Playitem.class);

                i.putExtra("userid", userId);
                i.putExtra("name", name);
                i.putExtra("phone", phone);
                i.putExtra("playId", playId);
                i.putExtra("image", image);
                i.putExtra("title", title);
                i.putExtra("price", price);
                i.putExtra("brand", brand);
                i.putExtra("color", color);
                i.putExtra("size", size);
                i.putExtra("negotiable", negotiable);

                startActivity(i);
                finish();

            }
        };
        timer.start();
    }


    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    @Override
    public void onBackPressed() {
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(AdActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.quit_dialog_layout);
                dialog.show();

                Button ookk = dialog.findViewById(R.id.button2);
                Button canc = dialog.findViewById(R.id.button4);

                canc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                ookk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();

                    }
                });


            }
        });
    }
}
