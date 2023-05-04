package com.example.dawiniapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestMedicineAddAddress extends AppCompatActivity {

    private EditText txtCity, txtStreet, txtBuildingName, txtFloor;
    private ArrayList<RequestMedicineSearchModal> yourList;
    private Button btnAddAddress;
    private String user_id;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_medcine_new_address);

        yourList = (ArrayList<RequestMedicineSearchModal>) getIntent().getSerializableExtra("MedicineList");
        user_id = SaveSharedPreference.getUserId(getApplicationContext());

        txtCity = (EditText) findViewById(R.id.txtCity);
        txtStreet =  (EditText)findViewById(R.id.txtStreet);
        txtBuildingName = (EditText) findViewById(R.id.txtBuildingName);
        txtFloor =  (EditText)findViewById(R.id.txtFloor);
        btnAddAddress = (Button) findViewById(R.id.btnAddAddress);

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtCity.getText().toString().trim().equals("")){
                    Toast.makeText(getBaseContext(), "Please enter a city",Toast.LENGTH_LONG).show();
                } else if(txtStreet.getText().toString().trim().equals("")){
                    Toast.makeText(getBaseContext(), "Please enter a street",Toast.LENGTH_LONG).show();
                }else if(txtBuildingName.getText().toString().trim().equals("")){
                    Toast.makeText(getBaseContext(), "Please enter a building Name",Toast.LENGTH_LONG).show();
                }else if(txtFloor.getText().toString().trim().equals("")){
                    Toast.makeText(getBaseContext(), "Please enter a Floor",Toast.LENGTH_LONG).show();
                } else {

                    //api call

                    String URL ="http://192.168.26.2:80/api/AddAddress.php";

                    final JSONObject jsonObject = new JSONObject();
                    try{
                        jsonObject.put("User_Id",user_id);
                        jsonObject.put("City",txtCity.getText().toString());
                        jsonObject.put("Street",txtStreet.getText().toString());
                        jsonObject.put("Bldg",txtBuildingName.getText().toString());
                        jsonObject.put("Floor",txtFloor.getText().toString());

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getBaseContext(), "new Address has been successfully added",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RequestMedicineAddAddress.this, RequestMedicinePlaceRequest.class);
                            intent.putExtra("MedicineList",yourList);
                            startActivity(intent);
                        }

                    }, new Response.ErrorListener() {
                        // If fail api
                        @Override
                        public void onErrorResponse(VolleyError err) {
                            ApiErrorHandler.handleError(getApplicationContext(), err,"There was an error processing your request");
                            err.printStackTrace();
                        }

                    }) {
                        @NonNull
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            // Build the headers
                            final Map<String, String> params = new HashMap<>();
                            params.put("Content-Type", "application/json");
                            return params;
                        }
                    };

                    // jor.setRetryPolicy(new DefaultRetryPolicy(initialTimeout, maxRetries, backoffMultiplier));
                    Volley.newRequestQueue(RequestMedicineAddAddress.this).add(jor);


                }
            }
        });

    }
}
