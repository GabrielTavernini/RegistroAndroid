package com.example.gabriel.myapplication;

import android.os.AsyncTask;

/**
 * Created by Gabriel on 25/11/2017.
 */

public class myTask extends AsyncTask<User, Integer, String> {

    @ Override
    protected void onPreExecute(){

    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
    @Override
    protected String doInBackground(User... user){
        try {
            return user[0].extractMarks();
        }catch(Exception e){
            return e.toString();
        }
    }
}
