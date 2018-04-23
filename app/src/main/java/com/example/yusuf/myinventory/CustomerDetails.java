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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetails extends AppCompatActivity {


    public static final String Customer_Name ="customername";
    public static final String Customer_ID ="customerid";

    EditText txtcustomername;
    EditText txtcustomeraddress;
    EditText txtcustomeremail;
    EditText txtcustomertel;
    EditText txtcustomerdate;
    Button btnadd;

    DatabaseReference databaseCustomerLists;

    ListView listViewcustomer;

    List<CustomerFields> CustomerLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        databaseCustomerLists = FirebaseDatabase.getInstance().getReference("customers");

        txtcustomername = (EditText) findViewById(R.id.txtcustomername);
        txtcustomeraddress = (EditText) findViewById(R.id.txtcustomeraddress);
        txtcustomeremail = (EditText) findViewById(R.id.txtcustomeremail);
        txtcustomertel = (EditText) findViewById(R.id.txtcustomertel);
        txtcustomerdate = (EditText) findViewById(R.id.txtcustomerdate);
        btnadd = (Button) findViewById(R.id.btnadd);
        listViewcustomer = (ListView) findViewById(R.id.listViewcustomer);

        CustomerLists = new ArrayList<>();

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customers();

            }
        });

        listViewcustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CustomerFields CustomerList = CustomerLists.get(i);

                Intent intent = new Intent(getApplicationContext(), AddSalesOrder.class);

                intent.putExtra(Customer_ID, CustomerList.getCustomerId());
                intent.putExtra(Customer_Name, CustomerList.getCustomerName());

                startActivity(intent);
            }
        });

        listViewcustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                CustomerFields CustomerList = CustomerLists.get(i);

                showUpdateDialog(CustomerList.getCustomerId(), CustomerList.getCustomerName());

                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseCustomerLists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CustomerLists.clear();

                for(DataSnapshot customerSnapshot: dataSnapshot.getChildren()){

                    CustomerFields customerfields = customerSnapshot.getValue(CustomerFields.class);

                    CustomerLists.add(customerfields);

                }

                CustomerLists adapter = new  CustomerLists(CustomerDetails.this,  CustomerLists);
                listViewcustomer.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void showUpdateDialog(final String customer_Id, String customer_Name){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update2_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText txtcustomername = (EditText) dialogView.findViewById(R.id.txtcustomername);
        final EditText txtcustomeraddress = (EditText) dialogView.findViewById(R.id.txtcustomeraddress);
        final EditText txtcustomeremail = (EditText) dialogView.findViewById(R.id.txtcustomeremail);
        final EditText txtcustomertel = (EditText) dialogView.findViewById(R.id.txtcustomertel);
        final EditText txtcustomerdate = (EditText) dialogView.findViewById(R.id.txtcustomerdate);
        final Button btnupdate = (Button) dialogView.findViewById(R.id.btnupdate);
        final Button btndelete = (Button) dialogView.findViewById(R.id.btndelete);

        dialogBuilder.setTitle("Updating Customer Details" +customer_Name);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String customerName = txtcustomername.getText().toString().trim();
                String customerAddress = txtcustomeraddress.getText().toString().trim();
                String customerEmail = txtcustomeremail.getText().toString().trim();
                String customerTel = txtcustomertel.getText().toString().trim();
                String customerDate = txtcustomerdate.getText().toString().trim();

                if(TextUtils.isEmpty(customerName)){

                    txtcustomername.setError("New Customer Name Required");
                    return;
                }

                updateCustomer(customer_Id, customerName, customerAddress, customerEmail, customerTel, customerDate);

                alertDialog.dismiss();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteCustomer(customer_Id);
            }
        });

    }

    private void deleteCustomer(String customer_Id) {


        DatabaseReference drOrders = FirebaseDatabase.getInstance().getReference("orders").child(customer_Id);
        DatabaseReference drCustomers = FirebaseDatabase.getInstance().getReference("customers").child(customer_Id);


        drCustomers.removeValue();
        drOrders.removeValue();

        Toast.makeText(this, "Customer Details Has Been Deleted", Toast.LENGTH_LONG).show();

    }

    private boolean updateCustomer(String customerId, String customerName, String customerAddress, String customerEmail,  String customerTel, String customerDate){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers").child(customerId);

        CustomerFields customerFields = new CustomerFields (customerId, customerName, customerAddress, customerEmail, customerTel, customerDate);

        databaseReference.setValue(customerFields);

        Toast.makeText(this, "Customer Has Been Updated", Toast.LENGTH_LONG).show();
        return true;

    }



    private void customers(){
        String name = txtcustomername.getText().toString().trim();
        String address = txtcustomeraddress.getText().toString().trim();
        String email = txtcustomeremail.getText().toString().trim();
        String tel = txtcustomertel.getText().toString().trim();
        String date = txtcustomerdate.getText().toString().trim();


        if (!TextUtils.isEmpty(name)) {

            String id = databaseCustomerLists.push().getKey();

            CustomerFields customerfields = new CustomerFields(id, name, address , email, tel, date);

            databaseCustomerLists.child(id).setValue(customerfields);

            Toast.makeText(this, "Customer Has Been Added To The Database!", Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(this, "Please Enter Customer Name", Toast.LENGTH_LONG).show();

        }

    }
}
