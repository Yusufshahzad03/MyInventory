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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddProductInfo extends AppCompatActivity {

    TextView textviewproductname;
    EditText txtsupplier;
    EditText txtdeliverydate;
    EditText txtproductinfo;
    ListView listViewinfo;
    Button btnadd;

    DatabaseReference databaseProductInfo;


    List<ProductInfo> ProductInfoLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_info);

    textviewproductname = (TextView) findViewById(R.id.textviewproductname);
    txtsupplier = (EditText) findViewById(R.id.txtsupplier);
    txtdeliverydate = (EditText) findViewById(R.id.txtdeliverydate);
    txtproductinfo = (EditText) findViewById(R.id.txtproductinfo);
    listViewinfo = (ListView) findViewById(R.id.listViewinfo);
    btnadd = (Button) findViewById(R.id.btnadd);

        Intent intent = getIntent();

        ProductInfoLists = new ArrayList<>();

        String id = intent.getStringExtra(Product.Product_ID);
        String name = intent.getStringExtra(Product.Product_Name);

        textviewproductname.setText(name);
        databaseProductInfo = FirebaseDatabase.getInstance().getReference("info").child(id);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addinfo();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseProductInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ProductInfoLists.clear();

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {

                    ProductInfo productinfo = productSnapshot.getValue(ProductInfo.class);

                    ProductInfoLists.add(productinfo);

                }


               ProductInfoList adapter = new ProductInfoList(AddProductInfo.this, ProductInfoLists);
                listViewinfo.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addinfo(){

        String infoSupplier = txtsupplier.getText().toString().trim();
        String infoDelivery = txtdeliverydate.getText().toString().trim();
        String infoProduct = txtproductinfo.getText().toString().trim();

        if (!TextUtils.isEmpty(infoSupplier)) {
            String id = databaseProductInfo.push().getKey();

            ProductInfo productinfo = new ProductInfo(id, infoSupplier, infoDelivery, infoProduct);
            databaseProductInfo.child(id).setValue(productinfo);

            Toast.makeText(this, "Product Information Added To Database", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Please Enter Supplier Name", Toast.LENGTH_LONG).show();
        }


    }

}
