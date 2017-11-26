package com.linegrillpresent.studybuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import SBRequestManager.SBRequestQueue;
import system.Course;
import system.Utility;
import user.Student;

public class RegisterNewCourse extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner courseSpinner;
    private Spinner codeSpinner;
    private ArrayList<String> courseNames;
    private ArrayList<Course> availableCourses;
    private Course Course_to_be_registered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_course);
        final SBRequestQueue SBQueue = SBRequestQueue.getInstance(this);

        Button btm = (Button) findViewById(R.id.btm_RegisterCourse);

        courseSpinner = (Spinner) findViewById(R.id.sp_courseName);
        codeSpinner = (Spinner) findViewById(R.id.sp_code);
        availableCourses = (ArrayList<Course>) Utility.getInstance().getAllAvailableCourses();
        Log.d("coursesize", Integer.toString(availableCourses.size()));
        courseNames = new ArrayList<String>();
        Set<String> tempnames = new HashSet<String>();

        for (int i = 0; i < availableCourses.size(); i++) {
            tempnames.add(availableCourses.get(i).getName());
            Log.d("coursenames", availableCourses.get(i).getName());
        }

        courseNames.addAll(tempnames);
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courseNames);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(arr_adapter);
        courseSpinner.setOnItemSelectedListener(this);
        btm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Student student = Student.getInstance();
                String course_name = courseSpinner.getSelectedItem().toString();
                int course_code = Integer.parseInt(codeSpinner.getSelectedItem().toString());
                int course_id = -1;
                Course tobeRegisterd = null;

                for (int i = 0; i < availableCourses.size(); i++) {
                    tobeRegisterd = availableCourses.get(i);
                    if (tobeRegisterd.getName().equals(course_name) && tobeRegisterd.getCode() == course_code) {
                        course_id = tobeRegisterd.getID();
                        Course_to_be_registered = tobeRegisterd;
                        if (student.getCourses().contains(tobeRegisterd)) {
                            Toast.makeText(RegisterNewCourse.this, "You are registered this course already", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                String staticURL = getResources().getString(R.string.deployURL) + "course?";
                String url = staticURL + "token=" + student.getToken() +
                        "&courseId=" + course_id +
                        "&action=registerCourse";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String resText = response.toString();

                                if (resText.equals("failed")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterNewCourse.this);
                                    builder.setMessage("Fail to create the group")
                                            .setNegativeButton("RETRY", null)
                                            .create()
                                            .show();
                                } else {
                                    /* success create
                                       Save the token in a Bundle object and pass it to the userMainActivity
                                     */
                                    student.joinCourse(Course_to_be_registered);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterNewCourse.this);
                                    String temp = "You have joined " + Course_to_be_registered.getFullName();
                                    builder.setMessage(temp)
                                            .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    Intent MainIntent = new Intent(RegisterNewCourse.this, MycourseActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("token", student.getToken());
                                                    MainIntent.putExtras(bundle);
                                                    MainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    RegisterNewCourse.this.startActivity(MainIntent);
                                                }
                                            })
                                            .create()
                                            .show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //test.setText("That didn't work!");
                    }
                });
                // Add the request to the RequestQueue
                SBQueue.addToRequestQueue(stringRequest);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String courseName = courseNames.get(position).trim();
        List<String> numbers = new ArrayList<>();
        for (int i = 0; i < availableCourses.size(); i++)
            if (availableCourses.get(i).getName().equals(courseName))
                numbers.add(Integer.toString(availableCourses.get(i).getCode()));
        Log.d("newgroup", Integer.toString(numbers.size()));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                numbers);
        codeSpinner.setAdapter(adapter2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
