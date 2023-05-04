package com.example.dawiniapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    private static final String PREF_USER_ID="user_id";

     static SharedPreferences getSharedPreferences(Context context) {
         return PreferenceManager.getDefaultSharedPreferences(context);
     }

     public static void setUserId(Context context, String user_id){
         SharedPreferences.Editor editor = getSharedPreferences(context).edit();
         editor.putString(PREF_USER_ID, user_id);
         editor.commit();
     }

     public static String getUserId(Context context){
         return getSharedPreferences(context).getString(PREF_USER_ID,"");
     }


}
