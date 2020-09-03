package com.example.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WellComeActivity extends AppCompatActivity {
    private Button DriverWelcomeButton;
    private Button CustomerWelcomeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come);
        DriverWelcomeButton=(Button) findViewById(R.id.driver_welcome_btn);
        CustomerWelcomeButton=(Button)findViewById(R.id.customer_welcome_btn);
        CustomerWelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WellComeActivity.this, CustomerLoginRegisterActivity.class);
                   startActivity(intent);

            }
        });
        DriverWelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WellComeActivity.this, DiverLoginRegisterActivity.class);
                startActivity(intent);

            }
        });

    }
}