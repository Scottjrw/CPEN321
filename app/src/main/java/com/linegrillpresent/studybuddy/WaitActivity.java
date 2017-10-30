package com.linegrillpresent.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import user.Student;

public class WaitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        Bundle tokenBundle = getIntent().getExtras();
        String token = tokenBundle.getString("token");
        Student user = Student.getInstance();
        user.setToken(token);
        user.updateInfo(this);
        user.updateCourseInfo(this);
        user.updateGroupInfo(this);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                    Intent i = new Intent(getApplicationContext(), MainPage.class);
                    startActivity(i);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
