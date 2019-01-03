package com.example.bigboss.bigboss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    Button sign;

    ImageView back;

    Button male , female;

    TextView login;

    EditText name , email , pass , age , mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signing);

        sign = findViewById(R.id.signup);

        back = findViewById(R.id.back);

        male = findViewById(R.id.male);

        female = findViewById(R.id.famale);

        name = findViewById(R.id.name);

        email = findViewById(R.id.email);

        pass = findViewById(R.id.password);

        age = findViewById(R.id.age);

        mobile = findViewById(R.id.mobile);

        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this , Login.class);
                startActivity(i);



            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignUp.this , Login.class);
                startActivity(i);



            }
        });
    }
}
