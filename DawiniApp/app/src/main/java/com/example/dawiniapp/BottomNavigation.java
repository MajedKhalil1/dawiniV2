package com.example.dawiniapp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BottomNavigation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BottomNavigationView nav = findViewById(R.id.Navigation);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bodyContainer, new HomePage())
                .commit();

        nav.setSelectedItemId(R.id.Home);


       nav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               System.out.print("tohome");
               Fragment fragment = null;
               switch (menuItem.getItemId()){

                   case R.id.Home:
                       System.out.print("tohome");
                       fragment = new HomePage();
                       System.out.print("tohome");
                       break;
               }

              getSupportFragmentManager().beginTransaction().replace(R.id.bodyContainer, fragment).commit();
               return true ;
           }
       });



    }

}
