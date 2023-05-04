package com.example.dawiniapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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

public class DeliverMedicineDetails extends AppCompatActivity {

    private TextView lblClientName,lblRequestId,lblAddress,lblMedicineList,lblDescription;
    private String requestId, medicineList,userId,addressId,description;
    private Button btnDeliver;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliver_medicine_details);

        requestId = getIntent().getStringExtra("requestId");
        lblMedicineList = findViewById(R.id.txtMedicineList);
        lblClientName =  findViewById(R.id.txtClientName);
        lblDescription=findViewById(R.id.txtDescription);
        lblRequestId =  findViewById(R.id.txtRequestId);
        lblAddress = findViewById(R.id.txtAddress);

        getMedicineRequestDetailsByRequestId(requestId,lblMedicineList, lblClientName,
                                                lblDescription,  lblRequestId, lblAddress);

    }

    private void getMedicineRequestDetailsByRequestId(String requestId,TextView lblMedicineList,  TextView lblClientName,
                                                        TextView  lblDescription, TextView  lblRequestId, TextView  lblAddress){
        String URL="http://192.168.26.2:80/api/GetUserRequestMedicineByRequestId.php?request_Id=" + requestId;
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, jsonObject,
                response -> {
                    try {
                        JSONArray pendingRequests = response.getJSONArray("pendingRequests");
                        if (!pendingRequests.isNull(0)) {
                            JSONObject requestObj = pendingRequests.getJSONObject(0);
                            lblRequestId.setText("#" + requestObj.getString("Request_Id"));
                            lblClientName.setText(requestObj.getString("To"));
                            lblMedicineList.setText(requestObj.getString("Requested Medicine"));
                            lblAddress.setText(requestObj.getString("Addresse"));
                            //lblDescription.setText(requestObj.getString("Desription"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    ApiErrorHandler.handleError(getApplicationContext(), error,"There was an error processing your request");
                }) {
            @NonNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        Volley.newRequestQueue(DeliverMedicineDetails.this).add(request);

    }

}
