package com.linegrillpresent.studybuddy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import system.Utility;
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
        Button btn4 = (Button) findViewById(R.id.btn_booking);
        Button logoutbtn = (Button) findViewById(R.id.btn_logout);

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
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(ctx, BookingActivity.class);
                ctx.startActivity(userMainIntent);
            }
        });
        Log.d("cpen", "oncreate");

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAction();
            }
        });

        //get Token passed in
        student_user = Student.getInstance();
        student_user.updateInfo(this);
        student_user.updateCourseInfo(this);
        student_user.updateGroupInfo(this);
        Utility.getInstance().updateAllAvailableCourses(this);
        //student_user
    }

    private void logoutAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomePage.this);
        builder.setMessage("Log Out")
                .setMessage("Are you sure you want to Log out now?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cleanCache();
                        Intent LoginIntent = new Intent(WelcomePage.this, LoginActivity.class);
                        WelcomePage.this.startActivity(LoginIntent);
                    }
                })
                .create()
                .show();
    }

    private void cleanCache() {
        Student.getInstance().destroy();
    }
}
