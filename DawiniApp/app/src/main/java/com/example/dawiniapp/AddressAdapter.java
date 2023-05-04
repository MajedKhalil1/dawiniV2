package com.example.dawiniapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AddressAdapter extends BaseAdapter {

    Context context;
    ArrayList<AddressModal> list;


    public AddressAdapter(Context context, ArrayList<AddressModal> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view= LayoutInflater.from(context).inflate(R.layout.address_spinner,viewGroup, false);
        }
        TextView txtAddress =(TextView) view.findViewById(R.id.txtAddressSpinner);
        txtAddress.setText(list.get(i).getCity() + " " + list.get(i).getStreet() + " " + list.get(i).getBuilding() + " " + list.get(i).getFloor());
        return view;
    }
}
