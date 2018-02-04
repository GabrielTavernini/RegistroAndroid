package com.example.gabriel.myapplication;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static com.example.gabriel.myapplication.LoginActivity.SAVE_NAME;
import static com.example.gabriel.myapplication.LoginActivity.SAVE_PASSWORD;
import static com.example.gabriel.myapplication.LoginActivity.SAVE_SCHOOL;
import static com.example.gabriel.myapplication.LoginActivity.SAVE_USERNAME;

/**
 * Created by Gabriel on 28/11/2017.
 */

public class ActivitySchool extends AppCompatActivity {

    HashMap<String, Integer> schools = new HashMap<>();
    static int school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        schools.put("Val di Ledro", 29);
        schools.put("Dro", 27);
        schools.put("Cavedine", 28);

        Spinner school = findViewById(R.id.spinner);
        String[] keys = schools.keySet().toArray(new String[schools.keySet().size()]);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, keys);
        school.setAdapter(spinnerArrayAdapter);

        SharedPreferences preferences = getSharedPreferences(SAVE_NAME, MODE_PRIVATE);
        String username = preferences.getString(SAVE_USERNAME, null);
        String password = preferences.getString(SAVE_PASSWORD, null);
        String schoolPref = preferences.getString(SAVE_SCHOOL, null);
        if(username != null && password != null && schoolPref != null){
            try {
                new myTask().execute(new User(username, password, Integer.parseInt(schoolPref))).get();

                Intent myIntent = new Intent(getApplicationContext(), Menu.class);
                startActivityForResult(myIntent, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void schoolSelected(View view){
        Spinner schoolSelector = findViewById(R.id.spinner);
        school = schools.get(schoolSelector.getSelectedItem());

        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
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
