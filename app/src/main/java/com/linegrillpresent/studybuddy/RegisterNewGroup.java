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
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import SBRequestManager.SBRequestQueue;
import system.Course;
import system.UISystem;
import user.Student;

public class RegisterNewGroup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner courseSpinner;
    private Spinner numSpinner;
    private ArrayList<String> courseNames;
    private ArrayList<Course> course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_group);
        final SBRequestQueue SBQueue = SBRequestQueue.getInstance(this);
        final EditText et_name = (EditText) findViewById(R.id.et_GroupName);
        //final EditText et_desc = (EditText) findViewById(R.id.et_desc);
        Button btm = (Button) findViewById(R.id.btm_RegisterGroup);




        courseSpinner = (Spinner) findViewById(R.id.sp_courseNames);
        numSpinner = (Spinner) findViewById(R.id.sp_num);
        course = UISystem.getInstance().getCourseNames(this);
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

                Bundle bundle = getIntent().getExtras();

                final Student student = (Student) bundle.getSerializable("student");
                String url ="http://206.87.128.138:8080/Servlet/group?token=" + student.getToken() + "&action=createGroup&groupName=" + name;




                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String resText = response.toString();
                                //test.setText("Response is: "+ resText);
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
                // Add the request to the RequestQueue.
                //queue.add(stringRequest);
                SBQueue.addToRequestQueue(stringRequest);
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
//  设置第二个控件给定数据源绑定适配器
        String courseName = courseNames.get(position).trim();
        List<String> numbers = UISystem.getInstance().getAllCourseNum(courseName);
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
