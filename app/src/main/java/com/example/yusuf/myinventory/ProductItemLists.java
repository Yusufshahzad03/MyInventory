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
 * Created by Yusuf on 21/02/2018.
 */

public class ProductItemLists extends ArrayAdapter<ProductFields> {

    private Activity context;
    private List<ProductFields> productitemlist;

    public ProductItemLists(Activity context, List<ProductFields> productitemlist) {

        super(context, R.layout.list_layout, productitemlist);
        this.context = context;
        this.productitemlist = productitemlist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewname = (TextView) listViewItem.findViewById(R.id.textViewname);
        TextView textViewcategory = (TextView) listViewItem.findViewById(R.id.textViewcategory);
        TextView textViewprice = (TextView) listViewItem.findViewById(R.id.textViewprice);
        TextView textViewamount = (TextView) listViewItem.findViewById(R.id.textViewamount);

        ProductFields productlist = productitemlist.get(position);

        textViewname.setText(productlist.getProductName());
        textViewcategory.setText(productlist.getProductCategory());
        textViewprice.setText(productlist.getProductPrice());
        textViewamount.setText(productlist.getProductAmount());

        return listViewItem;

    }

}
