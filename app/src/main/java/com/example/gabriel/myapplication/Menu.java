package com.example.gabriel.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static com.example.gabriel.myapplication.LoginActivity.SAVE_NAME;


public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        System.out.println("menu");
    }

    public void onVoti(View view){
        Intent myIntent = new Intent(getApplicationContext(), ScrollingActivity.class);
        myIntent.putExtra("Selected", "Voti");
        startActivityForResult(myIntent, 0);
    }


    public void onMedie(View view){
        Intent myIntent = new Intent(getApplicationContext(), ScrollingActivity.class);
        myIntent.putExtra("Selected", "Medie");
        startActivityForResult(myIntent, 0);
    }

    public void onEsci(View view){
        getSharedPreferences(SAVE_NAME, MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
        Intent myIntent = new Intent(getApplicationContext(), ActivitySchool.class);
        startActivityForResult(myIntent, 0);
    }


    public void  onArgomenti(View view){
        Intent myIntent = new Intent(getApplicationContext(), ScrollingActivity.class);
        myIntent.putExtra("Selected", "Argomenti");
        startActivityForResult(myIntent, 0);
    }

    @Override
    public void onBackPressed(){
        Intent  intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.out.println("exit");
    }
}
