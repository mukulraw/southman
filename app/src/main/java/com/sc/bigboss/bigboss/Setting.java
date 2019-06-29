package com.sc.bigboss.bigboss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Setting extends AppCompatActivity {

    //Toolbar toolbar;

    TextView logout;

    TextView change;

    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // toolbar = findViewById(R.id.toolbar);
      /*  setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.setTitle("Setting");*/

        /*logout = findViewById(R.id.textView75);
         *//* change = findViewById(R.id.textView76);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Setting.this , ChangePAssword.class);
                startActivity(i);
            }
        });*//*
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });*/

        back = findViewById(R.id.imageButton4);

        back.setOnClickListener(v -> finish());
    }
}
