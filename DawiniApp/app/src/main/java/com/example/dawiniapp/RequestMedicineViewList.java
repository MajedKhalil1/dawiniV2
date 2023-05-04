package com.example.dawiniapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RequestMedicineViewList extends AppCompatActivity {

    private ArrayList<RequestMedicineSearchModal> yourList;
    private ListView listView;
    private Button btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_medicine_view_list);

        btnNext = (Button) findViewById(R.id.btnNext);

        yourList = (ArrayList<RequestMedicineSearchModal>) getIntent().getSerializableExtra("MedicineList");
        listView = (ListView) findViewById(R.id.listViewMedicineList);

        RequestMedicineViewListAdapter viewListAdapter = new RequestMedicineViewListAdapter(getApplicationContext(),yourList);
        listView.setAdapter(viewListAdapter);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestMedicineViewList.this, RequestMedicinePlaceRequest.class);
                intent.putExtra("MedicineList",yourList);
                startActivity(intent);
            }
        });

    }
}
