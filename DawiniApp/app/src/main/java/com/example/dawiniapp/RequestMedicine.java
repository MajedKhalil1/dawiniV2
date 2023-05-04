package com.example.dawiniapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RequestMedicine extends AppCompatActivity {

    private ListView listViewMedicineNameSearch;
    private RequestMedicineSearchAdapter medicineSearchAdapter;
    private SearchView searchMedicine;
    private Button btnAdd, btnPlus, btnMinus, btnViewList;
    private TextView txtQty;
    ArrayList<RequestMedicineSearchModal> listMedicineSearch;
    ArrayList<RequestMedicineSearchModal> filteredMedicineList;
    ArrayList<RequestMedicineSearchModal> listMedicineAdded;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_medicine_1);

        btnAdd = findViewById(R.id.btnAddToList);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnViewList = findViewById(R.id.btnViewList);
        txtQty = (TextView) findViewById(R.id.textQty);
        listMedicineAdded = new ArrayList<RequestMedicineSearchModal>();

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.valueOf((String)txtQty.getText());
                if(qty >= 1){
                    txtQty.setText(String.valueOf(qty - 1));
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int qty = Integer.valueOf((String)txtQty.getText());
                txtQty.setText(String.valueOf(qty + 1) );
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filteredMedicineList != null ){
                    boolean exist=false;
                    for(RequestMedicineSearchModal medicineName : listMedicineAdded) {
                        if (medicineName.getMedicineId().contains(filteredMedicineList.get(0).getMedicineId())) {
                            medicineName.setMedicineQuantity(String.valueOf(Integer.valueOf(txtQty.getText().toString()) + Integer.valueOf(medicineName.getMedicineQuantity())));
                            exist = true;
                        }
                    }
                        if(!exist){
                            RequestMedicineSearchModal medicine = new RequestMedicineSearchModal();
                            medicine.setMedicineId(filteredMedicineList.get(0).getMedicineId());
                            medicine.setMedicineName(filteredMedicineList.get(0).getMedicineName());
                            medicine.setMedicineQuantity(txtQty.getText().toString());
                            listMedicineAdded.add(medicine);
                            System.out.println(listMedicineAdded.get(0).getMedicineName());

                        }

                        Toast.makeText(getBaseContext(), "Medicine added to your list", Toast.LENGTH_LONG).show();

                        medicineSearchAdapter = new RequestMedicineSearchAdapter(getApplicationContext(), listMedicineAdded);
                        listViewMedicineNameSearch.setAdapter(medicineSearchAdapter);
                        txtQty.setText("0");

                    } else{
                        Toast.makeText(getBaseContext(),"Please select a medicine to add to your list", Toast.LENGTH_LONG).show();
                    }
                }

        });

        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listMedicineAdded.isEmpty()){
                    Toast.makeText(getBaseContext(),"Your List is empty", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(RequestMedicine.this, RequestMedicineViewList.class);
                    intent.putExtra("MedicineList",listMedicineAdded);
                    startActivity(intent);
                }
            }
        });

        listViewMedicineNameSearch =(ListView) findViewById(R.id.listViewRequestMedicine);
        searchMedicine = (SearchView) findViewById(R.id.txtMedecineSearch);
        listMedicineSearch = new ArrayList<RequestMedicineSearchModal>();

        RequestMedicineSearchModal item = new RequestMedicineSearchModal();
        item.setMedicineName("Panadol");
        item.setMedicineQuantity(txtQty.getText().toString());
        item.setMedicineId("1");
        listMedicineSearch.add(item);

        RequestMedicineSearchModal item2 = new RequestMedicineSearchModal();
        item2.setMedicineName("Histamed");
        item2.setMedicineQuantity(txtQty.getText().toString());
        item2.setMedicineId("2");
        listMedicineSearch.add(item2);

        medicineSearchAdapter = new RequestMedicineSearchAdapter(getApplicationContext(),listMedicineSearch);
        listViewMedicineNameSearch.setAdapter(medicineSearchAdapter);

        searchMedicine.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

               filteredMedicineList = new ArrayList<RequestMedicineSearchModal>();
               if(searchMedicine.getQuery().equals("")){
                   listViewMedicineNameSearch.setVisibility(View.INVISIBLE);
               }else {
                   listViewMedicineNameSearch.setVisibility(View.VISIBLE);
                   for (RequestMedicineSearchModal item : listMedicineSearch) {
                       if (item.getMedicineName().toLowerCase().contains(newText.toLowerCase())) {
                           filteredMedicineList.add(item);
                       }
                   }
               }
                medicineSearchAdapter = new RequestMedicineSearchAdapter(getApplicationContext(), filteredMedicineList);
                listViewMedicineNameSearch.setAdapter(medicineSearchAdapter);

                return true;
            }
        });

        searchMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewMedicineNameSearch.setVisibility(View.VISIBLE);
            }
        });

        listViewMedicineNameSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchMedicine.setQuery(medicineSearchAdapter.getItem(i).toString(), false);
                listViewMedicineNameSearch.setVisibility(View.INVISIBLE);
            }


        });

        searchMedicine.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    listViewMedicineNameSearch.setVisibility(View.INVISIBLE);
                }
            }
        });




    }



}
