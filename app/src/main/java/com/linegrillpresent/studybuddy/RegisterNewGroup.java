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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import SBRequestManager.SBRequestQueue;
import system.Course;
import user.Student;

public class RegisterNewGroup extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner courseSpinner;
    private Spinner numSpinner;
    private ArrayList<String> courseNames;
    private ArrayList<Course> course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      try{
          super.onCreate(savedInstanceState);
      }catch (Exception e){
      }
        setContentView(R.layout.activity_register_new_group);
        final SBRequestQueue SBQueue = SBRequestQueue.getInstance(this);
        final EditText et_name = (EditText) findViewById(R.id.et_GroupName);
        //final EditText et_desc = (EditText) findViewById(R.id.et_desc);
        Button btm = (Button) findViewById(R.id.btm_RegisterGroup);

        final Switch isPrivate = (Switch) findViewById(R.id.sw_private);
        final EditText inviteCode = (EditText) findViewById(R.id.et_inviteC);
        inviteCode.setVisibility(View.INVISIBLE);

        isPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on)
                {
                    //Do something when Switch button is on/checked
                    //tView.setText("Switch is on.....");
                    inviteCode.setVisibility(View.VISIBLE);
                }
                else
                {
                    //Do something when Switch is off/unchecked
                    //tView.setText("Switch is off.....");
                    inviteCode.setVisibility(View.INVISIBLE);
                }
            }
        });


        courseSpinner = (Spinner) findViewById(R.id.sp_courseNames);
        numSpinner = (Spinner) findViewById(R.id.sp_num);
        course = (ArrayList<Course>) Student.getInstance().getCourses();
        Log.d("newgroup", "student has " + course.size() + "courses");
        courseNames = new ArrayList<String>();

        for(int i = 0; i < course.size();i++) {
            if(!courseNames.contains(course.get(i).getName()))
                courseNames.add(course.get(i).getName());
        }
        Log.d("newgroup", Integer.toString(courseNames.size()));
        //适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courseNames);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        courseSpinner.setAdapter(arr_adapter);
        courseSpinner.setOnItemSelectedListener(this);

        btm.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = et_name.getText().toString();
                int privateOrNot;
                if(isPrivate.isChecked())
                    privateOrNot = 1;
                else privateOrNot = 0;
                String inCode = inviteCode.getText().toString();

                String course_name = courseSpinner.getSelectedItem().toString();
                int course_num = Integer.parseInt(numSpinner.getSelectedItem().toString());
                int course_id = -1;
                for(int i = 0;i < course.size();i++) {
                    Course c = course.get(i);
                    if(c.getName().equals(course_name) && c.getCode() == course_num)
                        course_id = c.getID();
                }

                final Student student = Student.getInstance();

                String staticURL = getResources().getString(R.string.deployURL) + "group?";
                String url = staticURL + "token=" + student.getToken() + "&isPrivate=" + privateOrNot +
                             "&groupName=" + name +
                             "&inviteCode=" + inCode +
                             "&courseId=" + course_id +
                             "&action=createGroup";

                Log.d("newgroup", url);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String resText = response.toString();

                                if(resText.equals("failed") ) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterNewGroup.this);
                                    builder.setMessage("Fail to create the group")
                                            .setNegativeButton("RETRY", null)
                                            .create()
                                            .show();
                                } else {
                                    /* success create
                                       Save the token in a Bundle object and pass it to the userMainActivity
                                     */
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterNewGroup.this);
                                    builder.setMessage("Create group success!")
                                            .setPositiveButton("Back",  new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    Intent mainpageIntent = new Intent(RegisterNewGroup.this, MainPage.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("token", student.getToken());
                                                    mainpageIntent.putExtras(bundle);
                                                    RegisterNewGroup.this.startActivity(mainpageIntent);
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
        for(int i = 0;i < course.size();i++)
            if(course.get(i).getName().equals(courseName))
                numbers.add(Integer.toString(course.get(i).getCode()));

        Log.d("newgroup", Integer.toString(numbers.size()));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                numbers);
        numSpinner.setAdapter(adapter2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
