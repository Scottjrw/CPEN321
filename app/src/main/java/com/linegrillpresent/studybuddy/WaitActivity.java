package com.linegrillpresent.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import user.InvalidUserException;
import user.Student;

public class WaitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        Bundle tokenBundle = getIntent().getExtras();
        String token = tokenBundle.getString("token");
        Student user = Student.getInstance();
        try {
            user.setToken(token);
        } catch (InvalidUserException e) {
            e.printStackTrace();
        }
        user.updateInfo(this);
        user.updateCourseInfo(this);
        user.updateGroupInfo(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Intent i = new Intent(getApplicationContext(), WelcomePage.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
