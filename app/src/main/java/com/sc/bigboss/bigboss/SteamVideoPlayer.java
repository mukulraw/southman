package com.sc.bigboss.bigboss;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;

import java.util.Objects;

public class SteamVideoPlayer extends AppCompatActivity {


    private Toolbar toolbar;

    private Button order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steam_video_player);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(v -> finish());

        order = findViewById(R.id.order);
        order.setOnClickListener(v -> {



        });
    }
}
