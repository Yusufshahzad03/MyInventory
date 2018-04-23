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

public class SalesLists extends ArrayAdapter<SalesFields> {

    private Activity context;
    private List<SalesFields> saleslists;

    public SalesLists(Activity context, List<SalesFields> saleslists) {

        super(context, R.layout.orderlist_layout, saleslists);
        this.context = context;
        this.saleslists = saleslists;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.orderlist_layout, null, true);

        TextView textViewsku = (TextView) listViewItem.findViewById(R.id.textViewsku);
        TextView textViewquantity = (TextView) listViewItem.findViewById(R.id.textViewquantity);
        TextView textVieworderdate = (TextView) listViewItem.findViewById(R.id.textVieworderdate);

        SalesFields orderlist = saleslists.get(position);

        textViewsku.setText(orderlist.getOrderSku());
        textViewquantity.setText(orderlist.getOrderQuantity());
        textVieworderdate.setText(orderlist.getOrderDate());

        return listViewItem;

    }


}
