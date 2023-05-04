package com.example.dawiniapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

public class ApiErrorHandler {

    public static void handleError(Context context, VolleyError error, String customError) {
        if (error.networkResponse != null) {
            int statusCode = error.networkResponse.statusCode;
            if (statusCode == 400) {
                Toast.makeText(context, "400 Invalid JSON Data", Toast.LENGTH_LONG).show();
            } else if (statusCode == 404) {
                Toast.makeText(context, "404 Not Found", Toast.LENGTH_LONG).show();
            } else if (statusCode == 401) {
                handleUnauthorizedError(context, error, customError);
            } else {
                Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Connection timeout or no Network Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private static void handleUnauthorizedError(Context context, VolleyError error, String customError) {
        // Handle 401 Unauthorized error
        // Update the error view visibility and text
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            TextView errorView = activity.findViewById(R.id.txtError);
            if (errorView != null) {
                errorView.setVisibility(View.VISIBLE);
                errorView.setText(customError);
            }
        }
    }

}
