package com.sc.bigboss.bigboss;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Forgot extends AppCompatActivity {

    private Button send;

    //private ImageView back;

    private EditText email;

    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        send = findViewById(R.id.send);

        //back = findViewById(R.id.imageButton3);

        email = findViewById(R.id.email);

        bar = findViewById(R.id.progress);


        send.setOnClickListener(v -> {


            Intent i = new Intent(Forgot.this , Login.class);
            startActivity(i);

        });
    }
}
