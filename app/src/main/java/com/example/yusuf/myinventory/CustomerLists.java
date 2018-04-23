package com.example.yusuf.myinventory;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yusuf on 18/03/2018.
 */

public class CustomerLists extends ArrayAdapter<CustomerFields> {

    private Activity context;
    private List<CustomerFields> customerlist;

    public CustomerLists(Activity context, List<CustomerFields> customerlist) {

        super(context, R.layout.listcust_layout, customerlist);
        this.context = context;
        this.customerlist = customerlist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listcust_layout, null, true);

        TextView textViewname = (TextView) listViewItem.findViewById(R.id.textViewname);
        TextView textViewaddress = (TextView) listViewItem.findViewById(R.id.textViewaddress);
        TextView textViewemail = (TextView) listViewItem.findViewById(R.id.textViewemail);
        TextView textViewtel = (TextView) listViewItem.findViewById(R.id.textViewtel);
        TextView textViewdate = (TextView) listViewItem.findViewById(R.id.textViewdate);

        CustomerFields customerdetails = customerlist.get(position);

        textViewname.setText(customerdetails.getCustomerName());
        textViewaddress.setText(customerdetails.getCustomerAddress());
        textViewemail.setText(customerdetails.getCustomerEmail());
        textViewtel.setText(customerdetails.getCustomerTel());
        textViewdate.setText(customerdetails.getCustomerDate());

        return listViewItem;

    }

}
