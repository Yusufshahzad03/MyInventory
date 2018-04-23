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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordDelete extends AppCompatActivity {

    EditText txtpassword;
    ProgressDialog dialog;
    FirebaseAuth auth;
    Button btnback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_delete);

        dialog = new ProgressDialog(this);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        btnback = (Button) findViewById(R.id.btnback);
        auth = FirebaseAuth.getInstance();
    }

    public void change(View v)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)


        {

            String password  = txtpassword.getText().toString().trim();

            //checking if change passwords are empty
            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Please enter passsword to change",Toast.LENGTH_LONG).show();
                return;
            }

            dialog.setMessage("Changing Password, Please Wait!");
            dialog.show();

            user.updatePassword(txtpassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Your Password Has Been Changed", Toast.LENGTH_SHORT).show();
                                auth.signOut();
                                finish();
                                Intent i = new Intent(ChangePasswordDelete.this, login.class);
                                startActivity(i);
                            }
                            else
                            {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Your Password Has Not Been Changed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }

    }

    public void btnback (View v)

    {

        finish();
        Intent i = new Intent(ChangePasswordDelete.this, Menu.class);
        startActivity(i);

    }


    //** here write the deactivation code**//


    public void btndeactivate (View v)

    {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            dialog.setMessage("Deactivating.....Please Wait!!");
            dialog.show();
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Account is Deactivated", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ChangePasswordDelete.this, login.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Account can not be deactivated right now", Toast.LENGTH_SHORT).show();

                    }
                }
            });


        }


    }




}
