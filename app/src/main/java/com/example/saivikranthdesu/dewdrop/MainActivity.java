package com.example.saivikranthdesu.dewdrop;

import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addbutton = findViewById(R.id.activity_main_fab);
        LinearLayout mainlayout = findViewById(R.id.activity_main_layout);

        ArrayList<CardView> viewList = new ArrayList<>();

        addbutton.setOnClickListener(view -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LinearLayout mView = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_add_plant, null);

            EditText plantnameinput = mView.findViewById(R.id.plant_name_input);
            EditText planttypeinput = mView.findViewById(R.id.plant_type_input);

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
                    plantname.setText(plantnameinput.getText().toString());
                    planttype.setText(planttypeinput.getText().toString());

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
}
