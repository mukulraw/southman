package com.sc.bigboss.bigboss;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ChangePAssword extends AppCompatActivity {

    private Button submit;

    private ImageView back;

    private EditText np;
    private EditText cp;

    private ProgressBar bar;

    private ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        cd = new ConnectionDetector(ChangePAssword.this);

        submit = findViewById(R.id.submit);

        back = findViewById(R.id.back);

        np = findViewById(R.id.np);

        cp = findViewById(R.id.cp);
        bar = findViewById(R.id.progress);

        back.setOnClickListener(v -> finish());

        submit.setOnClickListener(v -> {

            Intent i = new Intent(ChangePAssword.this , Login.class);
            startActivity(i);
        });
    }
}
