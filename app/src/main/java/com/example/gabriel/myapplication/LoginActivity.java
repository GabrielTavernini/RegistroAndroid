package com.example.gabriel.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.gabriel.myapplication.ActivitySchool.school;

/**
 * Created by Gabriel on 11/01/18.
 */

public class LoginActivity extends AppCompatActivity {

    static Map<String, Subject> subjectMap = new HashMap<>();
    static ArrayList<Grade> grades = new ArrayList<>();

    public static final String SAVE_NAME = "LoginStuff";
    public static final String SAVE_USERNAME = "Username";
    public static final String SAVE_PASSWORD = "Password";
    public static final String SAVE_SCHOOL = "School";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    public void login(View view) throws Exception {
        ProgressBar load = findViewById(R.id.progressBar);
        View loginForm = findViewById(R.id.loginForm);
        loginForm.setVisibility(View.GONE);
        load.setVisibility(View.VISIBLE);
        load.setMax(1);
        load.setProgress(1);

        CheckBox rememberMe = findViewById(R.id.rememberMe);
        EditText userText = findViewById(R.id.user);
        EditText passText = findViewById(R.id.pass);
        String username = userText.getText().toString();
        String password = passText.getText().toString();

        if(rememberMe.isChecked()){
            getSharedPreferences(SAVE_NAME, MODE_PRIVATE)
                    .edit()
                    .putString(SAVE_USERNAME, username)
                    .putString(SAVE_PASSWORD, password)
                    .putString(SAVE_SCHOOL, Integer.toString(school))
                    .commit();
        }else{
            getSharedPreferences(SAVE_NAME, MODE_PRIVATE)
                    .edit()
                    .clear()
                    .commit();
        }

        System.out.println(username);
        System.out.println(password);
        System.out.println(school);

        new myTask().execute(new User(username, password, school)).get();

        loginForm.setVisibility(View.VISIBLE);
        load.setVisibility(View.GONE);

        Intent myIntent = new Intent(getApplicationContext(), Menu.class);
        startActivityForResult(myIntent, 0);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ActivitySchool.class);
        startActivity(myIntent);
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent myIntent = new Intent(getApplicationContext(), ActivitySchool.class);
        startActivity(myIntent);

        System.out.println("exit1");
    }
}
