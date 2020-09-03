package com.example.taxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DiverLoginRegisterActivity extends AppCompatActivity {
    private TextView CreateDriverAccount;
    private TextView TitleDriver;
    private Button LoginDriverButton;
    private Button RegisterDriverButton;
    private EditText DriverEmail;
    private EditText DriverPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference driverDatabaseRef;
    private FirebaseUser currentUser;
    String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_login_register);
        CreateDriverAccount = (TextView) findViewById(R.id.create_driver_account);
        TitleDriver = (TextView) findViewById(R.id.titlr_driver);
        LoginDriverButton = (Button) findViewById(R.id.login_driver_btn);
        RegisterDriverButton = (Button) findViewById(R.id.register_driver_btn);
        DriverEmail = (EditText) findViewById(R.id.driver_email);
        DriverPassword = (EditText) findViewById(R.id.driver_password);
        loadingBar=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        currentUser =FirebaseAuth.getInstance().getCurrentUser();



        CreateDriverAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateDriverAccount.setVisibility(View.INVISIBLE);
                LoginDriverButton.setVisibility(View.INVISIBLE);
                TitleDriver.setText("Driver Registration");

                RegisterDriverButton.setVisibility(View.VISIBLE);
                RegisterDriverButton.setEnabled(true);
            }
        });
RegisterDriverButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String email = DriverEmail.getText().toString();
        String password = DriverPassword.getText().toString();
        RegisterDriver(email,password);

    }
});
        LoginDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = DriverEmail.getText().toString();
                String password = DriverPassword.getText().toString();
                LoginDriver(email,password);

            }
        });

    }

    private void LoginDriver(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "please write ur mail", Toast.LENGTH_SHORT).show();

        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "please write ur pass", Toast.LENGTH_SHORT).show();

        }else {
            loadingBar.setTitle("Driver Register");
            loadingBar.setMessage("Please Wait,While we register the Data.......");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(DiverLoginRegisterActivity.this, "Driver Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DiverLoginRegisterActivity.this, CustomerMapsActivity.class);
                        startActivity(intent);



                        loadingBar.dismiss();
                        Toast.makeText(DiverLoginRegisterActivity.this, "Driver Login", Toast.LENGTH_SHORT).show();

                    }else{
                        loadingBar.dismiss();
                        Toast.makeText(DiverLoginRegisterActivity.this, "Driver does not Login ", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    private void RegisterDriver(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "please write ur mail", Toast.LENGTH_SHORT).show();

        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "please write ur pass", Toast.LENGTH_SHORT).show();

        }else {
            loadingBar.setTitle("Driver Register");
            loadingBar.setMessage("Please Wait,While we register the Data.......");
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        currentUserId=mAuth.getCurrentUser().getUid();
                        driverDatabaseRef= FirebaseDatabase.getInstance().getReference()
                                .child("Users").
                                        child("Drivers")
                                .child(currentUserId);
                        driverDatabaseRef.setValue(true);
                        Intent intent = new Intent(DiverLoginRegisterActivity.this, DriverMapsActivity.class);
                        startActivity(intent);

                        driverDatabaseRef.setValue(true);

                        loadingBar.dismiss();
                        Toast.makeText(DiverLoginRegisterActivity.this, "Driver Register", Toast.LENGTH_SHORT).show();
                    }else{
                        loadingBar.dismiss();
                        Toast.makeText(DiverLoginRegisterActivity.this, "Driver does not Register", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

}