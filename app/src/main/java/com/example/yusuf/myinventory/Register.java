package com.example.yusuf.myinventory;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText txtemail;
    private EditText txtpassword;
    private Button btnregister;

    private TextView textViewSignup;

    private ProgressDialog progressDialog;



    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() != null){

            finish();


            startActivity(new Intent(getApplicationContext(), Menu.class));
        }


        txtemail = (EditText) findViewById(R.id.txtemail);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        textViewSignup = (TextView) findViewById(R.id.textViewSignup);

        btnregister = (Button) findViewById(R.id.btnregister);

        progressDialog = new ProgressDialog(this);


        btnregister.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    private void registerUser(){


        String email = txtemail.getText().toString().trim();
        String password  = txtpassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Enter E-mail Address",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), Menu.class));
                        }else{

                            Toast.makeText(Register.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if(view == btnregister){
            registerUser();
        }

        if(view == textViewSignup)

            startActivity(new Intent(this, login.class));
        }

    }
