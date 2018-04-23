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
 * Created by Yusuf on 02/03/2018.
 */

public class ProductInfoList extends ArrayAdapter<ProductInfo> {

    private Activity context;
    private List<ProductInfo> productinfolist;

    public ProductInfoList(Activity context, List<ProductInfo> productinfolist) {

        super(context, R.layout.infolist_layout, productinfolist);
        this.context = context;
        this.productinfolist = productinfolist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.infolist_layout, null, true);

        TextView textViewsupplier = (TextView) listViewItem.findViewById(R.id.textViewsupplier);
        TextView textViewdelivery = (TextView) listViewItem.findViewById(R.id.textViewdelivery);
        TextView textViewproductinfo = (TextView) listViewItem.findViewById(R.id.textViewproductinfo);

        ProductInfo productlist = productinfolist.get(position);

        textViewsupplier.setText(productlist.getInfoSupplier());
        textViewdelivery.setText(productlist.getInfoDelivery());
        textViewproductinfo.setText(productlist.getInfoProduct());

        return listViewItem;

    }

}
