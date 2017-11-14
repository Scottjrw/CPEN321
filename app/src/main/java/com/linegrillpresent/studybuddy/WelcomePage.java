package com.linegrillpresent.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import user.Student;

public class WelcomePage extends AppCompatActivity {
    private String token;
    private Student student_user;
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        ctx = this;

        Button btn1 = (Button) findViewById(R.id.myprofile);
        Button btn2 = (Button) findViewById(R.id.mycourses);
        Button btn3 = (Button) findViewById(R.id.mygroups);
        Button btn4 = (Button) findViewById(R.id.button4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(ctx, MyprofileActivity.class);
                ctx.startActivity(userMainIntent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(ctx, MycourseActivity.class);
                ctx.startActivity(userMainIntent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(ctx, MygroupActivity.class);
                ctx.startActivity(userMainIntent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Log.d("cpen", "oncreate");

        //get Token passed in
        student_user = Student.getInstance();
        student_user.updateInfo(this);
        student_user.updateCourseInfo(this);
        student_user.updateGroupInfo(this);

        //student_user
    }
}
