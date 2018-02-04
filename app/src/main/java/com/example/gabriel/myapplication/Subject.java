package com.example.gabriel.myapplication;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import static com.example.gabriel.myapplication.LoginActivity.subjectMap;

/**
 * Created by Gabriel on 26/11/2017.
 */

public class Subject {
    String name;
    ArrayList<Grade> grades = new ArrayList<>();



    public Subject(String name, ArrayList<Grade> grades, Boolean save){
        this.name = name;
        this.grades = grades;
        if(save) subjectMap.put(this.name, this);

    }

    public Subject(String name, Boolean save){
        this.name = name;
        if(save) subjectMap.put(this.name, this);
    }

    public Grade getMedia(){
        Grade media = new Grade("", "","","Media", this, false);
        Float sum = (float) 0.0;
        int i = 0;
        for (Grade grade: this.grades){
            sum += grade.getGrade();
            i++;
        }

        Float mediaFloat = sum/i;
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        df.setDecimalSeparatorAlwaysShown(false);
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        String s = df.format(mediaFloat);
        media.setGrade(s);
        return media;
    }

    public void addGrade(Grade g){
        grades.add(g);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    static Subject getSubjectByString(String s){
        return subjectMap.get(s);
    }
}
