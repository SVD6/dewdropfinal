package com.example.saivikranthdesu.dewdrop;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saivikranthdesu.dewdrop.objects.plantobject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addbutton = findViewById(R.id.activity_main_fab);
        LinearLayout mainlayout = findViewById(R.id.activity_main_layout);
        TextView welcome = findViewById(R.id.welcome_login);

        ArrayList<CardView> viewList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        welcome.setText("Welcome Sai Vikranth Desu");

        addbutton.setOnClickListener(view -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LinearLayout mView = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_add_plant, null);

            EditText plantnameinput = mView.findViewById(R.id.plant_name_input);
            EditText planttypeinput = mView.findViewById(R.id.plant_type_input);
            EditText targetmoisture = mView.findViewById(R.id.moisture_target_level);

            builder.setView(mView);
            builder.setCancelable(true);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setNegativeButton("CANCEL", (dialog, which) -> {
            });

            AlertDialog alert = builder.create();
            alert.setCanceledOnTouchOutside(true);
            alert.show();

            Button submit = alert.getButton(AlertDialog.BUTTON_POSITIVE);
            submit.setOnClickListener(v -> {
                if (plantnameinput.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter a valid plant name", Toast.LENGTH_LONG).show();
                } else if (planttypeinput.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter a valid plant type", Toast.LENGTH_LONG).show();
                } else {
                    LinearLayout otherone = (LinearLayout) getLayoutInflater().inflate(R.layout.cardview_plant, null);
                    TextView plantname = otherone.findViewById(R.id.plant_name_cardview);
                    TextView planttype = otherone.findViewById(R.id.plant_type_cardview);
                    TextView moisturelevel = otherone.findViewById(R.id.current_moisture);
                    plantname.setText(plantnameinput.getText().toString());
                    planttype.setText(planttypeinput.getText().toString());
                    moisturelevel.setText(targetmoisture.getText().toString());

                    database.getReference().child("Soil moisture").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            moisturelevel.setText(dataSnapshot.getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    plantobject plant = new plantobject();
                    plant.setNickname(plantnameinput.getText().toString());
                    plant.setPlanttype(planttypeinput.getText().toString());
                    plant.setMoisturelevel(23);

                    database.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("plants").child(plantnameinput.getText().toString()).setValue(plant);

                    mainlayout.addView(otherone);
                    alert.hide();

                    otherone.setOnLongClickListener(view1 -> {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                        builder1.setMessage("Are you sure you want to delete this plant?");
                        builder1.setPositiveButton("DELETE", null);
                        builder1.setNegativeButton("CANCEL", (dialog, which) -> {
                        });

                        AlertDialog alert1 = builder1.create();
                        alert1.setCanceledOnTouchOutside(true);
                        alert1.show();

                        Button delete = alert1.getButton(AlertDialog.BUTTON_POSITIVE);
                        delete.setOnClickListener(view2 -> {
                            mainlayout.removeView(otherone);
                            alert1.hide();
                        });

                        return false;
                    });
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
