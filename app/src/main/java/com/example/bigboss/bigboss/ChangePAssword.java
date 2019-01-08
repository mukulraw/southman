package com.example.bigboss.bigboss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ChangePAssword extends AppCompatActivity {

    Button submit;

    ImageView back;

    EditText np , cp;

    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        submit = findViewById(R.id.submit);

        back = findViewById(R.id.back);

        np = findViewById(R.id.np);

        cp = findViewById(R.id.cp);
        bar = findViewById(R.id.progress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ChangePAssword.this , Login.class);
                startActivity(i);
            }
        });
    }
}
