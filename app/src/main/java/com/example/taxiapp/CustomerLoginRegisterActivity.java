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

public class CustomerLoginRegisterActivity extends AppCompatActivity {
    private TextView CreateCustomerAccount;
    private TextView TitleCustomer;
    private Button LoginCustomerButton;
    private Button RegisterCustomerButton;
    private EditText CustomerEmail;
    private EditText CustomerPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference customersDatabaseRef;
    private FirebaseUser currentUser;
    String currentUserId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);
        CreateCustomerAccount = (TextView) findViewById(R.id.customer_register_link);
        TitleCustomer = (TextView) findViewById(R.id.customer_status);
        LoginCustomerButton = (Button) findViewById(R.id.customer_login_btn);
        RegisterCustomerButton = (Button) findViewById(R.id.customer_register_btn);
        CustomerEmail = (EditText) findViewById(R.id.customer_email);
        CustomerPassword = (EditText) findViewById(R.id.customer_password);
        loadingBar=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        CreateCustomerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateCustomerAccount.setVisibility(View.INVISIBLE);
                LoginCustomerButton.setVisibility(View.INVISIBLE);
                TitleCustomer.setText("Customer Registration");

                RegisterCustomerButton.setVisibility(View.VISIBLE);
                RegisterCustomerButton.setEnabled(true);
            }
        });

        RegisterCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = CustomerEmail.getText().toString().trim();
                String password = CustomerPassword.getText().toString().trim();
              RegisterCustomer(email,password);



            }
        });
        LoginCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = CustomerEmail.getText().toString().trim();
                String password = CustomerPassword.getText().toString().trim();
                LoginCustomer(email,password);

            }
        });


    }
    private void LoginCustomer(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "please write ur mail", Toast.LENGTH_SHORT).show();

        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "please write ur pass", Toast.LENGTH_SHORT).show();

        }else {
            loadingBar.setTitle("Customer Register");
            loadingBar.setMessage("Please Wait,While we register the Data.......");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        loadingBar.dismiss();

                        Toast.makeText(CustomerLoginRegisterActivity.this, "Driver Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CustomerLoginRegisterActivity.this, CustomerMapsActivity.class);
                        startActivity(intent);
                    }else{
                        loadingBar.dismiss();
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Driver does not Login ", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }


    private void RegisterCustomer(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "please write ur mail", Toast.LENGTH_SHORT).show();

        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "please write ur pass", Toast.LENGTH_SHORT).show();

        }else {
            loadingBar.setTitle("Customer Register");
            loadingBar.setMessage("Please Wait,While we register the Data.......");
            loadingBar.show();
mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){

            currentUserId = mAuth.getCurrentUser().getUid();
            customersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child("Customers")
                    .child(currentUserId);
            customersDatabaseRef.setValue(true);

            Intent intent = new Intent(CustomerLoginRegisterActivity.this, CustomerMapsActivity.class);
            startActivity(intent);

            loadingBar.dismiss();
            Toast.makeText(CustomerLoginRegisterActivity.this, "Customer Register", Toast.LENGTH_SHORT).show();
        }else{
            loadingBar.dismiss();
            Toast.makeText(CustomerLoginRegisterActivity.this, "Customer does not Register", Toast.LENGTH_SHORT).show();
        }

    }
});
        }

    }
}