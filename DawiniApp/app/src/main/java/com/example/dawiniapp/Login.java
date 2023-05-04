package com.example.dawiniapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText txtEmail , txtPassword ;
    private TextView error , txtCreate ;
    private Button btnSignIn;


    //int maxRetries = 3;
    // Define the initial timeout for the request (in milliseconds)
    //int initialTimeout = 5000;
    // Define the backoff multiplier
   //float backoffMultiplier = 1f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnSignIn=findViewById(R.id.btnSignIn);
        txtCreate=findViewById(R.id.txtCreate);


        String email =txtEmail.getText().toString();
        String password=txtPassword.getText().toString();

        txtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                credentialsCheck();

              try {
                  System.out.println("here");
                   UserLogin();
               } catch (JSONException e) {
                 throw new RuntimeException(e);
                }


            }
        });

        }
        private void credentialsCheck(){
            String email =txtEmail.getText().toString();
            String password=txtPassword.getText().toString();

        error=findViewById(R.id.txtError);
            if(email.equals("")  || password.equals("")){

              error.setVisibility(View.VISIBLE);
              error.setText("Email or Password can't be empty");

            }

            else{
                error.setVisibility(View.INVISIBLE);
                error.setText("");
            }
    }
    private void UserLogin() throws JSONException {
        String email =txtEmail.getText().toString();
        String password=txtPassword.getText().toString();

        String url = "http://192.168.26.2:80/api/Login.php";
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("Email", email);
        jsonobject.put("Password", password);
        System.out.println(email);
        System.out.println(password);

        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, jsonobject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String token = response.getString("token");
                    String user_id = response.getString("User_Id");
                    if (token.equals("success")) {
                        SaveSharedPreference.setUserId(Login.this,user_id);
                        Intent intent = new Intent(Login.this,BottomNavigation.class);
                        startActivity(intent);
                    }else {
                        System.out.println("invalid");
                    }
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
                    } else if (statusCode == 401) {
                        // Handle 401 Not Found error
                        error.setVisibility(View.VISIBLE);
                        error.setText("Invalid Email or Password");
                    }else {
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
        Volley.newRequestQueue(Login.this).add(jor);



    }
       }



