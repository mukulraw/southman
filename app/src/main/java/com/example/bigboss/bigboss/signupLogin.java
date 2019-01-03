package com.example.bigboss.bigboss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signupLogin extends AppCompatActivity {

    Button signup , login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        signup = findViewById(R.id.signup);

        login = findViewById(R.id.login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(signupLogin.this , SignUp.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(signupLogin.this , Login.class);
                startActivity(i);
            }
        });
    }
}
