package com.example.dawiniapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RequestMedicineSearchAdapter extends BaseAdapter {

    Context context;
    ArrayList<RequestMedicineSearchModal> arraylist;

    public RequestMedicineSearchAdapter(Context context, ArrayList<RequestMedicineSearchModal> arraylist) {
        this.context=context;
        this.arraylist=arraylist;

    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup ) {

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_request_medicine_search, viewGroup , false);
        }
        TextView medicineName = (TextView) view.findViewById(R.id.txtMedicineName);
        medicineName.setText(arraylist.get(i).getMedicineName());
        return view;
    }



}
