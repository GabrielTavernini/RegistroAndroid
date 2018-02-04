package com.example.gabriel.myapplication;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.gabriel.myapplication.LoginActivity.grades;

/**
 * Created by Gabriel on 26/11/2017.
 */

public class Grade {
    String date;
    String Description;
    String type;
    Float grade;
    Subject subject;
    String gradeString;
    Date dateTime;

    public Grade(String date, String type, String grade, String Description, Subject subject, Boolean save){
        this.date = date;
        this.Description = Description;
        this.type = type;
        this.grade = convertGrade(grade);
        this.gradeString = grade;
        this.subject = subject;
        this.dateTime = convertDate(date);
        if(save) grades.add(this);
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = convertGrade(grade);
        this.gradeString = grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getGradeString() {
        return gradeString;
    }

    public void setGradeString(String gradeString) {
        this.gradeString = gradeString;
    }

    public Date getDateTime(){
        return dateTime;
    }
    private float convertGrade(String g){
        String s;

        if(g.contains("+")) s = g.replace("+", ".25");
        else if(g.contains("-")){
            int i = Integer.parseInt(g.replaceAll("-", "")) -1;
            s = String.valueOf(i) + ".75";
        }
        else if(g.contains("½")) s = g.replaceAll("½",".5");
        else if("".equals(g)) return (float) 0;
        else s = g;

        float Grade = Float.parseFloat(s);
        return  Grade;
    }

    private Date convertDate(String date){
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = dateFormat.parse(date);
            return d;
        }
        catch(Exception e) {
            return null;
        }
    }

}
