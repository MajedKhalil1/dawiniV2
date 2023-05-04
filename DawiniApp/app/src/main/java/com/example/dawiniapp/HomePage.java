package com.example.dawiniapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomePage extends Fragment {

    public HomePage(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        Button btnRequestMedicine = view.findViewById(R.id.RequestMedecine);
        Button btnDeliverMedicine = view.findViewById(R.id.DeliverMedecine);
        Button btnMyRequest = view.findViewById(R.id.MyRequest);
        Button btnActiveDelivery = view.findViewById(R.id.ActiveDelivery);

        btnRequestMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),RequestMedicine.class);
                startActivity(intent);
            }
        });

        btnDeliverMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DeliverMedicine.class);
                startActivity(intent);
            }
        });




    }


}
