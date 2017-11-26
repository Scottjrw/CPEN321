package com.linegrillpresent.studybuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import user.Student;

public class MyprofileActivity extends AppCompatActivity {
    private String username;
    private String name;
    private String email;
    private int numGroups;
    private int numCourses;

    private MyprofileFragment.OnFragmentInteractionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        Student student = Student.getInstance();
        name = student.getName();
        username = student.getUsername();
        email = student.getEmail();
        numGroups = student.getNumberOfGroups();
        numCourses = student.getNumberOfCourses();
        Button r = (Button) findViewById(R.id.btn_refreshProfile);

        TextView tvName = (TextView) findViewById(R.id.tv_name);
        TextView tvUsername = (TextView) findViewById(R.id.tv_username);
        TextView tvEmail = (TextView) findViewById(R.id.tv_email);
        TextView tvnumGroups = (TextView) findViewById(R.id.tvnumGroup);
        TextView tvnumCourses = (TextView) findViewById(R.id.tv_num_courses);

        tvName.setText("Hello " + name + ",");
        tvUsername.setText("Your username is: " + username);
        tvEmail.setText("Your email is: " + email);
        tvnumGroups.setText("You are currently in " + numGroups + " groups!");
        tvnumCourses.setText("You are currently in " + numCourses + " courses!");
    }
}
