package com.sc.bigboss.bigboss;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private Button submit;

    private TextView signup;
    private TextView forgot;

    private EditText email;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        signup = findViewById(R.id.signup);

        submit = findViewById(R.id.submit);

        forgot = findViewById(R.id.forgot);

        email = findViewById(R.id.email);

        pass = findViewById(R.id.password);

        signup.setOnClickListener(v -> {

            Intent i =new Intent(Login.this , SignUp.class);
            startActivity(i);
        });


        submit.setOnClickListener(v -> {


            Intent i =new Intent(Login.this , MainActivity.class);
            startActivity(i);
        });

        forgot.setOnClickListener(v -> {


            Intent i =new Intent(Login.this , Forgot.class);
            startActivity(i);
        });
    }
}
