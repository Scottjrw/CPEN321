package com.linegrillpresent.studybuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import SBRequestManager.SBRequestQueue;
import system.Course;
import user.Student;

public class ShowClassInfoActivity extends AppCompatActivity {

    private TextView tv_course_name;
    private TextView tv_course_desc;
    private Course courseObj;
    private Student student;
    private ListView listview;
    private  ArrayList<String> groupsInCourse;
    private Button myGroup;
    private Button allGroup;
    private Button otherGroup;
    public ShowClassInfoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_class_info);
        Bundle extras = getIntent().getExtras();
        String course_name = extras.getString("course_name");
        tv_course_name = (TextView) findViewById(R.id.tv_coursename);
        tv_course_desc = (TextView) findViewById(R.id.tv_coursedesc);
        listview = (ListView) findViewById(R.id.list_groups);
        myGroup = (Button) findViewById(R.id.Mygroup_btn);
        allGroup = (Button) findViewById(R.id.Allgroup_btn);
        otherGroup = (Button) findViewById(R.id.Othergroup_btn);

        student = Student.getInstance();
        courseObj = student.getCourseObj(course_name);
        tv_course_name.setText(course_name);
        tv_course_desc.setText(courseObj.getDesc());

        myGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMyGroups();
            }
        });
        allGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTheFullList();
            }
        });
        otherGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayOtherGroups();
            }
        });
        listPrepare();
    }

    private void listPrepare() {
        String staticURL = this.getResources().getString(R.string.deployURL) + "course?";
        String url = staticURL + "token=" + student.getToken() + "&courseId=" + courseObj.getID() + "&action=listGroupsUnderCourse";
       // final Activity activity = this_act;
        groupsInCourse = new ArrayList();
        Log.d("ShowClass", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //receive the student'info
                        int length = response.length();
                        Log.d("ShowClass", "length is " + length);
                        for(int i = 0; i < length;i++)
                            try {
                                //if(!groups.contains(response.getString(i)))
                                    groupsInCourse.add(response.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    createTheFullList();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ShowClass", "CANNOT GET THE REQUEST!");
                        // fail to receive the student'info based on the token
                    }
                });
        SBRequestQueue.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
    private void createTheFullList() {
        final String[] listItems;
        if(groupsInCourse.size() == 0) {
            listItems = new String[1];
            listItems[0] = "No Group Under This Course Yet!";
        } else {
            listItems = new String[groupsInCourse.size()];
            for (int i = 0; i < groupsInCourse.size(); i++)
                listItems[i] = groupsInCourse.get(i);
        }
        //ArrayAdapter adapter = new ArrayAdapter(view, list_view, listItems);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_view, listItems);
        listview.setAdapter(adapter);
    }

    private void displayMyGroups() {
        final String[] listItems;
        if(groupsInCourse.size() == 0) {
            listItems = new String[1];
            listItems[0] = "No Group Under This Course Yet!";
        } else {
            ArrayList<String> filterList = new ArrayList<>();
            for (int i = 0; i < groupsInCourse.size(); i++)
                if(student.joinCourseOrNot(groupsInCourse.get(i)))
                    //listItems[i] = groupsInCourse.get(i);
                    filterList.add(groupsInCourse.get(i));
            listItems = new String[filterList.size()];
            for(int i = 0;i < filterList.size();i++)
                listItems[i] = filterList.get(i);
            Log.d("ShowClass", "filter length is " + filterList.size());
        }
        //ArrayAdapter adapter = new ArrayAdapter(view, list_view, listItems);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_view, listItems);
        listview.setAdapter(adapter);
    }

    private void displayOtherGroups() {
        final String[] listItems;
        if(groupsInCourse.size() == 0) {
            listItems = new String[1];
            listItems[0] = "No Group Under This Course Yet!";
        } else {
            ArrayList<String> filterList = new ArrayList<>();
            for (int i = 0; i < groupsInCourse.size(); i++)
                if(!student.joinCourseOrNot(groupsInCourse.get(i)))
                    //listItems[i] = groupsInCourse.get(i);
                    filterList.add(groupsInCourse.get(i));
            listItems = new String[filterList.size()];
            for(int i = 0;i < filterList.size();i++)
                listItems[i] = filterList.get(i);
            Log.d("ShowClass", "filter length is " + filterList.size());
        }
        //ArrayAdapter adapter = new ArrayAdapter(view, list_view, listItems);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_view, listItems);
        listview.setAdapter(adapter);
    }
}
