package com.linegrillpresent.studybuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import SBRequestManager.SBRequestQueue;
import system.Course;
import system.UISystem;

public class RegisterNewCourse extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner courseSpinner;
    private Spinner numSpinner;
    private ArrayList<String> courseNames;
    private ArrayList<Course> course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_course);
        final SBRequestQueue SBQueue = SBRequestQueue.getInstance(this);
        final EditText et_name = (EditText) findViewById(R.id.et_GroupName);
        //final EditText et_desc = (EditText) findViewById(R.id.et_desc);
        Button btm = (Button) findViewById(R.id.btm_RegisterGroup);
        courseSpinner = (Spinner) findViewById(R.id.sp_courseNames);
        numSpinner = (Spinner) findViewById(R.id.sp_num);
        course = UISystem.getInstance().getCourseNames(this);
        courseNames = new ArrayList<String>();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
