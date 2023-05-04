package com.example.dawiniapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestMedicinePlaceRequest extends AppCompatActivity {

    private ArrayList<RequestMedicineSearchModal> yourList;
    private ArrayList<AddressModal> addressList;
    private AddressAdapter addressAdapter;
    private AddressModal selectedAddress;
    private Button btnPlaceRequest;
    private TextView txtDescription;
    private Spinner spinnerAddress;
    private TextView txtAddAddress;
    private String userId;
    private int i = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_medecine_addresse);

        userId = SaveSharedPreference.getUserId(getApplicationContext());
        yourList = (ArrayList<RequestMedicineSearchModal>) getIntent().getSerializableExtra("MedicineList");
        txtAddAddress = (TextView) findViewById(R.id.txtAddAddress);
        spinnerAddress = (Spinner) findViewById(R.id.spinnerAddresse);
        btnPlaceRequest = (Button) findViewById(R.id.btnPlaceRequest);
        txtDescription = (TextView) findViewById(R.id.txtDescrption);

        txtAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestMedicinePlaceRequest.this, RequestMedicineAddAddress.class);
                intent.putExtra("MedicineList", yourList);
                startActivity(intent);
            }
        });

        //fill spinner with list of user address
        String URL = "http://192.168.26.2:80/api/GetAddressByUserId.php?userId=" + userId;
        JSONObject jsonobject = new JSONObject();

        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, jsonobject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    spinnerAddress.setEnabled(true);
                    spinnerAddress.setClickable(true);

                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray rows = jsonObject.getJSONArray("address");;

                    addressList = new ArrayList<AddressModal>();
                    AddressModal addressModal;

                    for(int i = 0 ; i < rows.length(); i++) {

                        addressModal = new AddressModal();
                        JSONObject results = rows.getJSONObject(i);

                        addressModal.setAddressId(results.getString("Adress_Id"));
                        addressModal.setUserId(results.getString("User_id"));
                        addressModal.setBuilding(results.getString("Bldg"));
                        addressModal.setCity(results.getString("City"));
                        addressModal.setStreet(results.getString("Street"));
                        addressModal.setFloor(results.getString("Floor"));

                        addressList.add(addressModal);
                    }
                    addressAdapter = new AddressAdapter(getApplicationContext(),addressList);
                    spinnerAddress.setAdapter(addressAdapter);

                    spinnerAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                selectedAddress = (AddressModal) addressAdapter.getItem(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                                selectedAddress = (AddressModal) addressAdapter.getItem(0);
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

        }, new Response.ErrorListener() {
            // If fail api
            @Override
            public void onErrorResponse(VolleyError err) {
                if (err.networkResponse != null) {
                    int statusCode = err.networkResponse.statusCode;
                    if (statusCode == 400) {
                        // Handle 400 Unauthorized error
                        Toast.makeText(getBaseContext(), "400 Invalid JSON Data", Toast.LENGTH_LONG).show();
                    } else if (statusCode == 404) {
                        // Handle 404 Not Found error
                        Toast.makeText(getBaseContext(), "404 Not Found", Toast.LENGTH_LONG).show();
                    } else {
                        // Handle other network errors
                        Toast.makeText(getBaseContext(), "Network Error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Handle other types of errors, such as timeouts or no network connectivity
                    Toast.makeText(getBaseContext(), "Connection timeout or no Network Error: " + err.getMessage(), Toast.LENGTH_LONG).show();
                }
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
        Volley.newRequestQueue(RequestMedicinePlaceRequest.this).add(jor);

        //place requestApi call
        btnPlaceRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedAddress == null){
                    Toast.makeText(getBaseContext(),"Please add an address",Toast.LENGTH_LONG).show();
                }else {
                    //api request to place request

                    String URL = "http://192.168.26.2:80/api/PlaceUserMedicineRequest.php";
                    JSONObject jsonobject = new JSONObject();

                    try {
                        jsonobject.put("userId",userId);
                        jsonobject.put("addressId",selectedAddress.getAddressId());
                        jsonobject.put("status","1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, URL, jsonobject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                           //get requestid from api
                            try {
                                String requestId = response.getString("requestId");

                                //call request medicine item api
                                for (RequestMedicineSearchModal item : yourList){
                                    String URL = "http://192.168.26.2:80/api/PlaceUserMedicineRequestItem.php";
                                    JSONObject jsonobject = new JSONObject();

                                    try {
                                        jsonobject.put("requestMedicineId",requestId);
                                        jsonobject.put("medicineId",item.getMedicineId());
                                        jsonobject.put("medicineQty",item.getMedicineQuantity());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    final JsonObjectRequest jor2 = new JsonObjectRequest(Request.Method.POST, URL, jsonobject, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                                if (i++ == yourList.size() - 1){
                                                    Toast.makeText(getBaseContext(), "Your medicine request has been placed", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(RequestMedicinePlaceRequest.this,BottomNavigation.class);
                                                    startActivity(intent);
                                                }
                                        }

                                    }, new Response.ErrorListener() {
                                        // If fail api
                                        @Override
                                        public void onErrorResponse(VolleyError err) {
                                            Toast.makeText(RequestMedicinePlaceRequest.this, "Failed to place request", Toast.LENGTH_SHORT).show();
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


                                    Volley.newRequestQueue(RequestMedicinePlaceRequest.this).add(jor2);


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                        // If fail api
                        @Override
                        public void onErrorResponse(VolleyError err) {
                            ApiErrorHandler.handleError(getApplicationContext(), err,"There was a problem processing your request");
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
                    Volley.newRequestQueue(RequestMedicinePlaceRequest.this).add(jor);


                }
            }
        });


    }
}