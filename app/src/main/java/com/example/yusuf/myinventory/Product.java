package com.example.yusuf.myinventory;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Product extends AppCompatActivity {


    public static final String Product_Name ="productname";
    public static final String Product_ID ="productid";

    EditText txtproductname;
    EditText txtproductprice;
    EditText txtproductamount;
    Button btnadd;
    Spinner spinnercategory;

    DatabaseReference databaseProductList;

    ListView listViewproducts;

    List<ProductFields> ProductItemLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        databaseProductList = FirebaseDatabase.getInstance().getReference("products");

        txtproductname = (EditText) findViewById(R.id.txtproductname);
        txtproductprice = (EditText) findViewById(R.id.txtproductprice);
        txtproductamount = (EditText) findViewById(R.id.txtproductamount);
        btnadd = (Button) findViewById(R.id.btnadd);
        listViewproducts = (ListView) findViewById(R.id.listViewproducts);

        ProductItemLists = new ArrayList<>();
        spinnercategory = (Spinner) findViewById(R.id.spinnercategory);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                products();

            }
        });

        listViewproducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ProductFields ProductItemList = ProductItemLists.get(i);

                Intent intent = new Intent(getApplicationContext(), AddProductInfo.class);

                intent.putExtra(Product_ID, ProductItemList.getProductId());
                intent.putExtra(Product_Name, ProductItemList.getProductName());

                startActivity(intent);
            }
        });

        listViewproducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                ProductFields ProductItemList = ProductItemLists.get(i);

                showUpdateDialog(ProductItemList.getProductId(), ProductItemList.getProductName());

                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseProductList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ProductItemLists.clear();

                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){

                    ProductFields productfields = productSnapshot.getValue(ProductFields.class);

                    ProductItemLists.add(productfields);

                }

                ProductItemLists adapter = new ProductItemLists(Product.this, ProductItemLists);
                listViewproducts.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void showUpdateDialog(final String product_Id, String product_Name){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText txtproductname = (EditText) dialogView.findViewById(R.id.txtproductname);
        final EditText txtproductprice = (EditText) dialogView.findViewById(R.id.txtproductprice);
        final EditText txtproductamount = (EditText) dialogView.findViewById(R.id.txtproductamount);
        final Spinner spinnercategory = (Spinner) dialogView.findViewById(R.id.spinnercategory);
        final Button btnupdate = (Button) dialogView.findViewById(R.id.btnupdate);
        final Button btndelete = (Button) dialogView.findViewById(R.id.btndelete);

        dialogBuilder.setTitle("Updating Product Details"+product_Name);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String productName = txtproductname.getText().toString().trim();
                String productPrice = txtproductprice.getText().toString().trim();
                String productCategory = spinnercategory.getSelectedItem().toString().trim();
                String productAmount = txtproductamount.getText().toString().trim();

                if(TextUtils.isEmpty(productName)){

                    txtproductname.setError("New Product Name Required");
                    return;
                }

                updateProduct(product_Id, productName, productPrice, productCategory, productAmount);

                alertDialog.dismiss();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteProduct(product_Id);
            }
        });

    }

    private void deleteProduct(String product_Id) {


        DatabaseReference drInfo = FirebaseDatabase.getInstance().getReference("info").child(product_Id);
        DatabaseReference drProducts = FirebaseDatabase.getInstance().getReference("products").child(product_Id);


        drProducts.removeValue();
        drInfo.removeValue();

        Toast.makeText(this, "Products Details Has Been Deleted", Toast.LENGTH_LONG).show();

    }

    private boolean updateProduct(String productId, String productName, String productCategory, String productPrice,  String productAmount){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("products").child(productId);

        ProductFields productFields = new ProductFields (productId, productName, productCategory, productPrice, productAmount);

        databaseReference.setValue(productFields);

        Toast.makeText(this, "Product Has Been Updated", Toast.LENGTH_LONG).show();
        return true;

    }


    private void products(){
        String name = txtproductname.getText().toString().trim();
        String category = spinnercategory.getSelectedItem().toString();
        String price = txtproductprice.getText().toString().trim();
        String amount = txtproductamount.getText().toString().trim();


        if (!TextUtils.isEmpty(name)) {

            String id = databaseProductList.push().getKey();

            ProductFields productfields = new ProductFields(id, name, category , price, amount);

            databaseProductList.child(id).setValue(productfields);

            Toast.makeText(this, "Product Has Been Added To The Database!", Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(this, "Please Enter Product Name", Toast.LENGTH_LONG).show();

        }

    }
}
