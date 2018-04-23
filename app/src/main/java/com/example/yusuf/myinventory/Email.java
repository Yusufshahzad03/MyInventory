package com.example.yusuf.myinventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Email extends AppCompatActivity {

    EditText txtemail2;
    EditText txtsubject;
    EditText txtmessage;
    Button btnsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        txtemail2 = (EditText) findViewById(R.id.txtemail2);
        txtsubject = (EditText) findViewById(R.id.txtsubject);
        txtmessage = (EditText) findViewById(R.id.txtmessage);
        btnsend = (Button) findViewById(R.id.btnsend);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMail();

            }
        });

    }

        private void sendMail() {

        String recipientList = txtemail2.getText().toString();
        String[]recipients = recipientList.split(",");
        String subject = txtsubject.getText().toString();
        String message = txtmessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/efc822");

        startActivity(Intent.createChooser(intent,"Select Email App"));


    }


}



