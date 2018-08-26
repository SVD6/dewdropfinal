package com.example.saivikranthdesu.dewdrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView signup = findViewById(R.id.signup_button);
        Button signin = findViewById(R.id.signin_button);

        signin.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));
        signup.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

    }
}
