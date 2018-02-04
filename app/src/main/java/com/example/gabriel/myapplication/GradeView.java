package com.example.gabriel.myapplication;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by Gabriel on 28/11/2017.
 */

public class GradeView extends ConstraintLayout{
    private TextView grade, subject, date, type;

    public GradeView(Context context){
        super(context);
        initializaViews(context);
    }

    public GradeView(Context context, AttributeSet attrs){
        super(context, attrs);
        initializaViews(context);
    }

    public GradeView(Context context,AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initializaViews(context);
    }

    public void initializaViews(Context context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.grade_view, this);
        grade = this.findViewById(R.id.grade);
        subject = this.findViewById(R.id.subject);
        date = this.findViewById(R.id.date);
        type = this.findViewById(R.id.type);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setDate(String dateText){
        date.setText(dateText);
    }

    public void setType(String typeText){
        type.setText(typeText);
    }

    public void setSubject(String subjectText){
        subject.setText(subjectText);
    }

    public String getSubject(){return subject.getText().toString();}

    public void setGrade(String gradeText){
        grade.setText(gradeText);
    }

    public void setAllGrade(Grade g){
        date.setText(g.getDate());
        type.setText(g.getType());
        subject.setText(g.getSubject().getName());
        grade.setText(g.getGradeString());
    }

    public void setColor(int i){ // 1 --> Marks   2 --> Average
        if(i == 1)
            this.findViewById(R.id.grade).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.capsule_marks, null));
        else if(i == 2)
            this.findViewById(R.id.grade).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.cupsule_average, null));
    }
}
