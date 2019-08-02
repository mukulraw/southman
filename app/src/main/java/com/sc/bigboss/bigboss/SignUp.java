package com.sc.bigboss.bigboss;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    private Button sign;

    private ImageView back;

    private Button male;
    private Button female;
    private Button whitemale;
    private Button whitefemale;

    private TextView login;

    private EditText name;
    private EditText email;
    private EditText pass;
    private EditText age;
    private EditText mobile;

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


        whitefemale = findViewById(R.id.whitefemale);
        whitemale = findViewById(R.id.whitemale);


        male.setOnClickListener(v -> {

            whitemale.setVisibility(View.VISIBLE);
            male.setVisibility(View.GONE);
            whitefemale.setVisibility(View.VISIBLE);

        });

        female.setOnClickListener(v -> {


            female.setVisibility(View.GONE);
            whitefemale.setVisibility(View.VISIBLE);
            male.setVisibility(View.VISIBLE);
        });

        login.setOnClickListener(v -> {
            Intent i = new Intent(SignUp.this, Login.class);
            startActivity(i);


        });

        back.setOnClickListener(v -> finish());
        sign.setOnClickListener(v -> {

            Intent i = new Intent(SignUp.this, Login.class);
            startActivity(i);


        });

/*


*/

    }
}
