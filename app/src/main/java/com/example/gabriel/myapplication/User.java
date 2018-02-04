package com.example.gabriel.myapplication;

import java.util.List;

/**
 * Created by Gabriel on 25/11/2017.
 */

public class User {
    String username;
    String password;
    int school;
    private final String USER_AGENT = "Mozilla/5.0";
    private List<String> cookies;



    public User(String username, String password, int school){
        this.username = username;
        this.password = password;
        this.school = school;
    }

    public int getSchool() {
        return school;
    }

    public void setSchool(int school) {
        this.school = school;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------Marks-------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------

    public String extractMarks() throws Exception{
        String marks = HttpConnection.extractVoti(username, password, school);
        return marks;
    }
}
