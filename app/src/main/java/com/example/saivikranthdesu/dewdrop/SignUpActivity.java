package com.example.saivikranthdesu.dewdrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saivikranthdesu.dewdrop.objects.userobject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TAG = "SignUpActivity";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Button submit = findViewById(R.id.submit_info);
        EditText email = findViewById(R.id.signup_email);
        EditText name = findViewById(R.id.signup_name);
        EditText password = findViewById(R.id.signup_password);

        submit.setOnClickListener(view -> {
            if (email.getText().toString().isEmpty() || name.getText().toString().isEmpty() ||
                    password.getText().toString().isEmpty()) {
                Toast.makeText(this, "You dumb bitch", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    userobject obj = new userobject();
                                    obj.setEmail(email.getText().toString());
                                    obj.setName(name.getText().toString());
                                    database.getReference().child("users").child(user.getUid()).setValue(obj);

                                    Toast.makeText(SignUpActivity.this, "Welcome! Please sign in with your new credentials", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}