package com.example.dawiniapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DeliverMedicineAdapter extends BaseAdapter {

    Context context;
    ArrayList<DeliverMedicineModal> arraylist;

    public DeliverMedicineAdapter(Context context, ArrayList<DeliverMedicineModal> arraylist) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_deliver_medicine, viewGroup , false);
        }

        TextView clientName = (TextView) view.findViewById(R.id.txtClientName);
        TextView requestId = (TextView) view.findViewById(R.id.txtRequestId);
        TextView medicineList = (TextView) view.findViewById(R.id.txtMedicineList);

        clientName.setText(arraylist.get(i).getClientName());
        requestId.setText("#"+ arraylist.get(i).getRequestId());
        medicineList.setText(arraylist.get(i).getMedicineList()); // i can fix this to only show some of the medicines and not the entire list
        return view;
    }
}
