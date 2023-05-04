package com.example.dawiniapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DeliverMedicine extends AppCompatActivity {

    private ListView listView;
    private DeliverMedicineAdapter deliverMedicineAdapter;
    private DeliverMedicineModal deliverMedicineModal;
    private ArrayList<DeliverMedicineModal> deliverMedicineList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliver_medicine);

        listView = (ListView) findViewById(R.id.listViewDeliverMedicine);
        deliverMedicineList = new ArrayList<DeliverMedicineModal>();

        getAllPendingMedicineRequests(deliverMedicineList,listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DeliverMedicineModal selectedRequest = (DeliverMedicineModal) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(DeliverMedicine.this,DeliverMedicineDetails.class);
                intent.putExtra("requestId", selectedRequest.getRequestId());
                startActivity(intent);
            }
        });


    }

    private void getAllPendingMedicineRequests(ArrayList<DeliverMedicineModal> deliverMedicineList , ListView listView){
        String URL="http://192.168.26.2:80/api/GetUserRequestMedicine.php";
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, jsonObject,
                response -> {
                    try {
                        JSONArray pendingRequests = response.getJSONArray("pendingRequests");
                        List<DeliverMedicineModal> modalList = IntStream.range(0, pendingRequests.length())
                                .mapToObj(i -> {
                                    try {
                                        JSONObject requestObj = pendingRequests.getJSONObject(i);
                                        DeliverMedicineModal deliverMedicineModal = new DeliverMedicineModal();
                                        deliverMedicineModal.setRequestId(requestObj.getString("Request_Id"));
                                        deliverMedicineModal.setClientName(requestObj.getString("To"));
                                        deliverMedicineModal.setMedicineList(requestObj.getString("Requested Medicine"));
                                        return deliverMedicineModal;
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }).collect(Collectors.toList());
                        deliverMedicineList.addAll(modalList);
                        deliverMedicineAdapter = new DeliverMedicineAdapter(getApplicationContext(), deliverMedicineList);
                        listView.setAdapter(deliverMedicineAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    ApiErrorHandler.handleError(getApplicationContext(), error,"No medicine requests available at the moment");
                }) {
            @NonNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
      Volley.newRequestQueue(DeliverMedicine.this).add(request);

    } // end of function




    }


