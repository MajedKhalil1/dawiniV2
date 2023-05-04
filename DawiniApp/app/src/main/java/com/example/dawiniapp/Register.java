package com.example.dawiniapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText txtEmail , txtPassword  , txtFirstName,txtLastName;
    private Button btnSignIn;


    private TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
         txtFirstName=findViewById(R.id.txtFirstName);
         txtLastName=findViewById(R.id.txtLastName);
         txtEmail =findViewById(R.id.txtEmail);
         txtPassword=findViewById(R.id.txtPassword);
        btnSignIn=findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    UserRegister();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void UserRegister() throws JSONException {
        String firstName =txtFirstName.getText().toString();
        String lastName =txtLastName.getText().toString();
        String email =txtEmail.getText().toString();
        String password=txtPassword.getText().toString();

        String url = "http://172.20.10.3:80/api/CreateUser.php";
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("FirstName", firstName);
        jsonobject.put("LastName", lastName);
        jsonobject.put("Email", email);
        jsonobject.put("Password", password);

        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, jsonobject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String token = response.getString("token");
                    if (token.equals("success")) {
                        Toast.makeText(getBaseContext(),"Succesfully Created" , Toast.LENGTH_LONG).show();
                     Intent intent = new Intent(Register.this,HomePage.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

        }, new Response.ErrorListener() {
            // If fail api
            @Override
            public void onErrorResponse(VolleyError err) {
                Toast.makeText(getBaseContext(),"Register Failed",Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(Register.this).add(jor);
    }
}

