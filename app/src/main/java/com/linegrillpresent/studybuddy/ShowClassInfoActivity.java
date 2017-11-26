package com.linegrillpresent.studybuddy;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import SBRequestManager.SBRequestQueue;
import system.Course;
import user.Student;

public class ShowClassInfoActivity extends AppCompatActivity {

    private TextView tv_course_name;
    private TextView tv_course_desc;
    private Course courseObj;
    private Student student;
    private ListView listview;
    private ArrayList<String> groupsInCourse;
    private Button myGroup;
    private Button allGroup;
    private Button otherGroup;
    private Button createGroup;
    private Button dropCourse;
    private Activity ctx;
    private String[] listItems;

    public ShowClassInfoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_class_info);
        Bundle extras = getIntent().getExtras();
        String course_name = extras.getString("course_name");
        ctx = this;

        tv_course_name = (TextView) findViewById(R.id.tv_coursename);
        tv_course_desc = (TextView) findViewById(R.id.tv_coursedesc);
        listview = (ListView) findViewById(R.id.list_groups);
        myGroup = (Button) findViewById(R.id.Mygroup_btn);
        allGroup = (Button) findViewById(R.id.Allgroup_btn);
        otherGroup = (Button) findViewById(R.id.Othergroup_btn);
        createGroup = (Button) findViewById(R.id.btn_createnewgroup);
        dropCourse = (Button) findViewById(R.id.btn_dropcourse);

        student = Student.getInstance();
        courseObj = student.getCourseObj(course_name);
        tv_course_name.setText(course_name);
        tv_course_desc.setText(courseObj.getDesc());
        tv_course_desc.setMovementMethod(new ScrollingMovementMethod());

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
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroupJump();
            }
        });
        dropCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropCourseWarn();
            }
        });
        listPrepare();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectGroupName = listItems[position];
                List<String> groupsOfStudents = student.getGroups();
                if (groupsOfStudents.contains(selectGroupName)) {
                    goToGroup(selectGroupName);

                } else {
                    //trying to join the group
                    Log.d("ShowClass", "NO!");
                    joinNewGroup(selectGroupName);
                }

            }
        });
    }

    private void createGroupJump() {
        Intent registerNewGroupIntent = new Intent(ctx, RegisterNewGroup.class);
        ctx.startActivity(registerNewGroupIntent);
    }

    private void goToGroup(String selectGroupName) {
        Log.d("ShowClass", "YES!");
        Intent showGroupInfoIntent = new Intent(ctx, ShowGroupInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("group_name", selectGroupName);
        bundle.putString("current_course_name", (String) tv_course_name.getText());
        bundle.putString("context", "ShowClassInfoActivity");
        showGroupInfoIntent.putExtras(bundle);
        ctx.startActivity(showGroupInfoIntent);
    }

    private void joinNewGroup(final String selectGroupName) {
        if (selectGroupName.equals("No Group Under This Course Yet!"))
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Join Group");


        //final TextView message = new TextView(this);
        String message = "You have not joined this group yet, Do you want to join the group Now? Please Enter the invite code if this it a private group";
        builder.setMessage(message);

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setLines(1);
        input.setHint("Invite Code(optional)");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String invite_code = input.getText().toString();
                Log.d("ShowClass", invite_code);
                sendJoinGroupRequest(selectGroupName, invite_code);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void dropCourseWarn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowClassInfoActivity.this);
        builder.setMessage("ARE YOU SURE")
                .setMessage("Are you sure you want to drop this course? you may join this course later if you like")
                .setNegativeButton("I changed my Mind", null)
                .setPositiveButton("Yeah I want to drop it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dropTheCourseAction();
                    }
                })
                .create()
                .show();
    }

    private void dropTheCourseAction() {
        String staticURL = this.getResources().getString(R.string.deployURL) + "course?";
        String url = staticURL + "token=" + student.getToken() + "&courseId=" + courseObj.getID() + "&action=dropCourse";
        Log.d("ShowClass", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String resText = response;
                        //test.setText("Response is: "+ resText);
                        if (resText.equals("failed")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ShowClassInfoActivity.this);
                            builder.setMessage("FAILED")
                                    .setNegativeButton("RETRY", null)
                                    .create()
                                    .show();
                        } else {
                                    /* success
                                     */
                            student.leaveCourse(courseObj.getFullName());
                            Intent userMainIntent = new Intent(ShowClassInfoActivity.this, MycourseActivity.class);
                            userMainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ShowClassInfoActivity.this.startActivity(userMainIntent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // access the server fail
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowClassInfoActivity.this);
                builder.setMessage("ACCESS SERVER FAILED")
                        .setNegativeButton("RETRY LATER", null)
                        .create()
                        .show();
            }
        });
        // Add the request to the RequestQueue
        SBRequestQueue.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void sendJoinGroupRequest(final String groupName, String inviteCode) {
        String staticURL = this.getResources().getString(R.string.deployURL) + "group?";
        String url = staticURL + "token=" + student.getToken() + "&groupName=" + groupName + "&inviteCode=" + inviteCode + "&action=joinGroup";
        Log.d("ShowClass", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String resText = response;
                        //test.setText("Response is: "+ resText);
                        if (resText.equals("failed")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ShowClassInfoActivity.this);
                            builder.setTitle("FAILED")
                                    .setMessage("The invite code you entered is not correct!")
                                    .setNegativeButton("Cancel", null)
                                    .create()
                                    .show();
                        } else {
                                    /* success Login
                                       Save the token in a Bundle object and pass it to the userMainActivity
                                     */
                            goToGroup(groupName);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // access the server fail
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowClassInfoActivity.this);
                builder.setMessage("ACCESS SERVER FAILED")
                        .setNegativeButton("RETRY LATER", null)
                        .create()
                        .show();
            }
        });
        // Add the request to the RequestQueue
        SBRequestQueue.getInstance(this).addToRequestQueue(stringRequest);
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
                        for (int i = 0; i < length; i++)
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
        //final String[] listItems;
        if (groupsInCourse.size() == 0) {
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
        //final String[] listItems;
        if (groupsInCourse.size() == 0) {
            listItems = new String[1];
            listItems[0] = "No Group Under This Course Yet!";
        } else {
            ArrayList<String> filterList = new ArrayList<>();
            for (int i = 0; i < groupsInCourse.size(); i++)
                if (student.joinCourseOrNot(groupsInCourse.get(i)))
                    //listItems[i] = groupsInCourse.get(i);
                    filterList.add(groupsInCourse.get(i));
            listItems = new String[filterList.size()];
            for (int i = 0; i < filterList.size(); i++)
                listItems[i] = filterList.get(i);
            Log.d("ShowClass", "filter length is " + filterList.size());
        }
        //ArrayAdapter adapter = new ArrayAdapter(view, list_view, listItems);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_view, listItems);
        listview.setAdapter(adapter);
    }

    private void displayOtherGroups() {
        //final String[] listItems;
        if (groupsInCourse.size() == 0) {
            listItems = new String[1];
            listItems[0] = "No Group Under This Course Yet!";
        } else {
            ArrayList<String> filterList = new ArrayList<>();
            for (int i = 0; i < groupsInCourse.size(); i++)
                if (!student.joinCourseOrNot(groupsInCourse.get(i)))
                    //listItems[i] = groupsInCourse.get(i);
                    filterList.add(groupsInCourse.get(i));
            listItems = new String[filterList.size()];
            for (int i = 0; i < filterList.size(); i++)
                listItems[i] = filterList.get(i);
            Log.d("ShowClass", "filter length is " + filterList.size());
        }
        //ArrayAdapter adapter = new ArrayAdapter(view, list_view, listItems);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_view, listItems);
        listview.setAdapter(adapter);
    }
}
