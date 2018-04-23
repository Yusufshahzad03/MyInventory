package com.example.yusuf.myinventory;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddSalesOrder extends AppCompatActivity {

    TextView textviewcustomername;
    EditText txtsku;
    EditText txtquantity;
    EditText txtorderdate;
    ListView listVieworders;
    Button btnadd;

    DatabaseReference databaseCustomerOrders;


    List<SalesFields> SalesLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale_order);

        textviewcustomername = (TextView) findViewById(R.id.textviewcustomername);
        txtsku = (EditText) findViewById(R.id.txtsku);
        txtquantity = (EditText) findViewById(R.id.txtquantity);
        txtorderdate = (EditText) findViewById(R.id.txtorderdate);
        listVieworders = (ListView) findViewById(R.id.listVieworders);
        btnadd = (Button) findViewById(R.id.btnadd);

        Intent intent = getIntent();

        SalesLists = new ArrayList<>();

        String id = intent.getStringExtra(CustomerDetails.Customer_ID);
        String name = intent.getStringExtra(CustomerDetails.Customer_Name);

        textviewcustomername.setText(name);
        databaseCustomerOrders = FirebaseDatabase.getInstance().getReference("orders").child(id);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addorder();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseCustomerOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SalesLists.clear();

                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {

                    SalesFields salesfields = orderSnapshot.getValue(SalesFields.class);

                    SalesLists.add(salesfields);

                }


                SalesLists adapter = new SalesLists(AddSalesOrder.this, SalesLists);
                listVieworders.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addorder(){

        String orderSku = txtsku.getText().toString().trim();
        String orderQuantity = txtquantity.getText().toString().trim();
        String orderDate = txtorderdate.getText().toString().trim();

        if (!TextUtils.isEmpty(orderSku)) {
            String id = databaseCustomerOrders.push().getKey();

            SalesFields salesfields = new SalesFields(id, orderSku, orderQuantity, orderDate);
            databaseCustomerOrders.child(id).setValue(salesfields);

            Toast.makeText(this, "Order Has Been Placed", Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(this, "Please Enter SKU Number", Toast.LENGTH_LONG).show();
        }


    }

}
