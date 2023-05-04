package com.example.dawiniapp;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CustomStringRequest extends StringRequest {

    private Map<String, String> params = null;
    private Map<String, String> headers = null;
    private  String token;

    public CustomStringRequest(int method, String url, Map<String,String> params, String token, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.setParams(params);
        this.setToken(token);

    }

    public CustomStringRequest(int method,String url,String token, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Nullable
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        if (this.params != null){
            return this.params;
        }
        return super.getParams();

    }

    public void setParams(Map<String, String> params) {
        if(params == null){
            this.params = new HashMap<String, String>();
        }
        this.params = params;
    }

    @Override
    public Map<String, String> getHeaders() {
        if(this.headers == null){
            this.headers = new HashMap<String,String>();
        }
        return this.headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }


}
