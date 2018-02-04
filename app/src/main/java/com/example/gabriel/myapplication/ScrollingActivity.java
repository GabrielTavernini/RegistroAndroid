package com.example.gabriel.myapplication;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.example.gabriel.myapplication.LoginActivity.grades;
import static com.example.gabriel.myapplication.LoginActivity.subjectMap;

public class ScrollingActivity extends AppCompatActivity {

    static CharSequence prevPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String selected = intent.getStringExtra("Selected");
        String subject = intent.getStringExtra("Subject");
        LinearLayout ll = findViewById(R.id.linearlayout);
        ll.removeAllViews();

        ActionBar Bar = getSupportActionBar();
        if("Medie".equals(selected)){

            if(Build.VERSION.SDK_INT >= 21){
                setTheme(R.style.AppTheme_AverageTheme);
                Bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2E5267")));
                getWindow().setStatusBarColor(getResources().getColor(R.color.AverageDark));
                setContentView(R.layout.activity_scrolling);
            }

            media();
            System.out.println("Media");

        }else if("Voti".equals(selected)){
            if(Build.VERSION.SDK_INT >= 21){
                setTheme(R.style.AppTheme_MarksTheme);
                Bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d1f2e")));
                getWindow().setStatusBarColor(getResources().getColor(R.color.MarksDark));
                setContentView(R.layout.activity_scrolling);
            }
            voti();
        }else if("Argomenti".equals(selected)){

        }

        if(subject != null){
            subject(subject);
        }


    }

    public void subject(String subjectName){
        Subject sub = Subject.getSubjectByString(subjectName);
        if(sub == null) return;
        setTitle(sub.getName());

        TextView tv = findViewById(R.id.title);
        tv.setText("\n\n" + sub.getName() + "\n\n");

        LinearLayout ll = findViewById(R.id.linearlayout);
        ll.removeAllViews();

        Map<Date, Grade> map = new TreeMap<>();
        for (Grade g : sub.getGrades()){
            map.put(g.getDateTime(), g);
        }

        Object[] orderedGrades = map.values().toArray();

        for(int i = map.values().size() - 1; i >= 0 ; i--){
            Grade grade = (Grade) orderedGrades[i];
            final GradeView gv = new GradeView(this);
            gv.setAllGrade(grade);
            ll.addView(gv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    public void media(){
        setTitle("Medie");
        TextView tv = findViewById(R.id.title);
        tv.setText("\n\nMedie\n\n");
        LinearLayout ll = findViewById(R.id.linearlayout);
        ll.removeAllViews();

        int i = 0;
        Float mediaGlobal = 0.0f;
        for(Grade g : grades){
            mediaGlobal += g.getGrade();
            i++;
        }

        mediaGlobal = mediaGlobal/i;
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        df.setDecimalSeparatorAlwaysShown(false);
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        GradeView mediaGloblView = new GradeView(this);
        mediaGloblView.setAllGrade(new Grade("","",df.format(mediaGlobal), "", new Subject("MEDIA GLOBALE", false), false));
        mediaGloblView.setColor(2);
        ll.addView(mediaGloblView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        for (Subject subject : subjectMap.values()){
            final GradeView gv = new GradeView(this);
            gv.setAllGrade(subject.getMedia());
            gv.setColor(2);
            ll.addView(gv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    public void voti(){
        setTitle("Voti");
        TextView tv = findViewById(R.id.title);
        tv.setText("\n\nVoti\n\n");
        LinearLayout ll = findViewById(R.id.linearlayout);
        ll.removeAllViews();


        Map<Date, Grade> map = new TreeMap<>();
        for (Grade g : grades){
            Date d = g.getDateTime();
            if(map.containsKey(g.getDateTime())){ d.setTime(g.getDateTime().getTime() + map.size() -1);} //I need to be sure that there aren't keys that are equals
            map.put(d, g);
            System.out.print(g.getGradeString() + ", ");
        }

        Object[] orderedGrades = map.values().toArray();

        System.out.println("BEFOR FOR");

        for(int i = map.values().size() - 1; i >= 0 ; i--){
            Grade grade = (Grade) orderedGrades[i];
            System.out.print(grade.getGradeString() + ", ");
            final GradeView gv = new GradeView(this);
            gv.setAllGrade(grade);
            gv.setColor(1);
            ll.addView(gv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        System.out.println("AFTER FOR");
    }

    public void onSubjectClick(View view){
        if(!(getTitle() == "Voti" ||  getTitle() == "Medie")) return;
        Intent myIntent = new Intent(getApplicationContext(), ScrollingActivity.class);
        TextView tv = view.findViewById(R.id.subject);
        Subject sub = Subject.getSubjectByString(tv.getText().toString());
        if(sub == null) return;
        myIntent.putExtra("Subject", tv.getText().toString());
        startActivityForResult(myIntent, 0);
        prevPage = getTitle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(getTitle() == "Voti" || getTitle() == "Medie") {
            Intent myIntent = new Intent(getApplicationContext(), Menu.class);
            startActivityForResult(myIntent, 0);
            return true;
        }else{
            Intent myIntent = new Intent(getApplicationContext(), ScrollingActivity.class);
            myIntent.putExtra("Selected", prevPage);
            startActivityForResult(myIntent, 0);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        if(getTitle() == "Voti" || getTitle() == "Medie") {
            Intent myIntent = new Intent(getApplicationContext(), Menu.class);
            startActivityForResult(myIntent, 0);
        }else{
            Intent myIntent = new Intent(getApplicationContext(), ScrollingActivity.class);
            myIntent.putExtra("Selected", prevPage);
            startActivityForResult(myIntent, 0);
        }
    }
}
