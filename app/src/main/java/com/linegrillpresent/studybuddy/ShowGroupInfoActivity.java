package com.linegrillpresent.studybuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import SBRequestManager.SBRequestQueue;
import user.Student;

import static com.linegrillpresent.studybuddy.R.id.btn_leave;

public class ShowGroupInfoActivity extends AppCompatActivity {

    private String group_name;
    private final List<String> groupMembers = new ArrayList<String>();
    private ListView groupMemList;
    private ListView disBoardList;
    private Button postButton;
    private Button editAnnounceButton;
    private EditText postEditText;
    private TextView announcementTextView;
    private Button leaveButton;
    private Student student;
    private String calling_activity_name;
    private String calling_course_name;
    private boolean return_to_calling_activity;
    private TextView tv_coursename;
    private TextView tv_groupname;
    private Button btn_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_group_info);

        Bundle tokenBundle = getIntent().getExtras();
        group_name = tokenBundle.getString("group_name");
        calling_activity_name = tokenBundle.getString(("context"));
        if (calling_activity_name.equals("ShowClassInfoActivity")) {
            calling_course_name = tokenBundle.getString("current_course_name");
            return_to_calling_activity = true;
        }

        //-----get view fragment---------------
        groupMemList = (ListView) findViewById(R.id.listview_GroupMemList);
        disBoardList = (ListView) findViewById(R.id.listview_disBoard);
        postButton = (Button) findViewById(R.id.btn_Post);
        postEditText = (EditText) findViewById(R.id.et_typeinfield);
        announcementTextView = (TextView) findViewById(R.id.tv_anounceText);
        editAnnounceButton = (Button) findViewById(R.id.btn_editannounce);
        leaveButton = (Button) findViewById(btn_leave);
        tv_coursename = (TextView) findViewById(R.id.tv_course_name);
        tv_groupname = (TextView) findViewById(R.id.tv_group_name);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        //---------------------
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        student = Student.getInstance();
        requestGroupInfo();
        final SBRequestQueue SBQueue = SBRequestQueue.getInstance(this);
        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveMessage();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postString = postEditText.getText().toString();
                if (postString.equals(""))
                    return;
                String token = student.getToken();

                String staticURL = getResources().getString(R.string.deployURL) + "group?action=newPost";
                String url = staticURL + "&token=" + token + "&groupName=" + group_name + "&post=" + postString;
                Log.d("url", url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String resText = response;
                                //test.setText("Response is: "+ resText);
                                if (resText.equals("failed")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowGroupInfoActivity.this);
                                    builder.setMessage("POST FAILED")
                                            .setNegativeButton("RETRY", null)
                                            .create()
                                            .show();
                                } else {
                                    refreshPage();
                                    postEditText.setText("");


                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // access the server fail

                    }
                });
                // Add the request to the RequestQueue
                SBQueue.addToRequestQueue(stringRequest);
            }
        });
        editAnnounceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announceButtonOnClick();
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShowGroupInfoActivity.this, "REFRESH!", Toast.LENGTH_SHORT).show();
                refreshPage();
            }
        });
        tv_groupname.setText(group_name);
        //tv_coursename.setText("COURSE_NAME");
        requestCourseNameInfo();
    }

    private void refreshPage() {
        requestGroupInfo();
    }

    private void announceButtonOnClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Announcement");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setLines(1);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                Log.d("group", m_Text);
                if (m_Text.equals("null"))
                    m_Text = "The Announcement Has Not Set Yet!";
                announcementTextView.setText(m_Text);
                sendChangeAnnouncementRequest(m_Text);
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

    private void sendChangeAnnouncementRequest(String newAnnouncement) {
        String token = student.getToken();
        String staticURL = getResources().getString(R.string.deployURL) + "group?action=updateAnnouncement&";
        String url = staticURL + "token=" + token + "&groupName=" + group_name + "&post=" + newAnnouncement;
        Log.d("ShowGroup", url);
        final SBRequestQueue SBQueue = SBRequestQueue.getInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String resText = response;
                        //test.setText("Response is: "+ resText);
                        if (resText.equals("failed")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ShowGroupInfoActivity.this);
                            builder.setMessage("Announcement Edit Failed")
                                    .setNegativeButton("RETRY", null)
                                    .create()
                                    .show();
                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(ShowGroupInfoActivity.this);
                            builder.setMessage("Announcement Edit Success")
                                    .setNegativeButton("YEAH", null)
                                    .create()
                                    .show();
                                    /* success POST
                                       Save the token in a Bundle object and pass it to the userMainActivity
                                     */

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // access the server fail

            }
        });
        // Add the request to the RequestQueue
        SBQueue.addToRequestQueue(stringRequest);
    }
    private void requestCourseNameInfo() {
        String token = student.getToken();
        String staticURL = getResources().getString(R.string.deployURL) + "group?action=getCourseName&";
        String url = staticURL + "token=" + token + "&groupName=" + group_name;
        Log.d("url", url);
        final SBRequestQueue SBQueue = SBRequestQueue.getInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String courseIDString = response;
                        Log.d("group", "course id is " + courseIDString);
                        int courseID = Integer.parseInt(courseIDString);
                        String courseName = student.getCourseNameByID(courseID);
                        tv_coursename.setText(courseName);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // access the server fail

            }
        });
        // Add the request to the RequestQueue
        SBQueue.addToRequestQueue(stringRequest);
    }
    private void requestGroupInfo() {

        String token = student.getToken();
        String staticURL = getResources().getString(R.string.deployURL) + "group?action=listUser&";
        String url = staticURL + "token=" + token + "&groupName=" + group_name;
        Log.d("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //receive ALL THE GROUP MEMBER'info
                        Log.d("group", response.toString());

                        try {
                            JSONArray jsonArrayMember = response.getJSONArray(0);
                            int memberLength = jsonArrayMember.length();
                            for (int i = 0; i < memberLength; i++)
                                if (!groupMembers.contains(jsonArrayMember.getString(i)))
                                    groupMembers.add(jsonArrayMember.getString(i));
                            Log.d("hello", "jsonArray Member length is " + memberLength);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            String announcement = response.getString(1);
                            if (announcement.equals("null"))
                                announcement = "The announcement Has not Set Yet!";
                            announcementTextView.setText(announcement);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONArray jsonArrayMessages = response.getJSONArray(2);
                            initializeDisBoardList(jsonArrayMessages);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        initializeMemberList();
                        //initializeDisBoardList();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // fail to receive the student'info based on the token
                        Log.d("fail", "cannot get response");

                    }
                });
        SBRequestQueue.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }


    private void initializeMemberList() {
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, R.layout.list_view, groupMembers);
        Log.d("group", "Group member list has length " + groupMembers.size());
        groupMemList.setAdapter(itemsAdapter);
    }

    private void initializeDisBoardList(JSONArray array) throws JSONException {
        List<HashMap<String, String>> listItems = new ArrayList<>();
        int messageNum = array.length();

        for (int i = 0; i < messageNum; i++) {
            HashMap<String, String> oneMessage = new HashMap<>();
            JSONObject oneJsonObject = array.getJSONObject(i);
            String author = oneJsonObject.getString("author");
            String time = oneJsonObject.getString("date");
            String words = oneJsonObject.getString("post");
            String firstLine = author + "--" + time;
            Log.d("group", "first line is:" + firstLine);
            oneMessage.put("First line", firstLine);
            oneMessage.put("Second line", words);
            listItems.add(oneMessage);
        }

        /*
        HashMap<String, String> messages = new HashMap<>();

        messages.put("tangao","hello");
        messages.put("chenyi","caonixuema");
        messages.put("kanglong","it should work");
        */
        SimpleAdapter adapter = new SimpleAdapter(
                this, listItems, R.layout.two_line_list_view,
                new String[]{"First line", "Second line"},
                new int[]{R.id.text1, R.id.text2}
        );
    /*
        Iterator it = messages.entrySet().iterator();
        while(it.hasNext()) {
            HashMap<String, String> resultMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultMap.put("First line", pair.getKey().toString());
            resultMap.put("Second line", pair.getValue().toString());
            listItems.add(resultMap);
        }
    */
        disBoardList.setAdapter(adapter);
    }

    private void leaveMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowGroupInfoActivity.this);
        builder.setMessage("ARE YOU SURE")
                .setMessage("Are you sure you want to leave this group? you may join this group later if you like")
                .setNegativeButton("I changed my mind", null)
                .setPositiveButton("Yeah I want to leave", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        leaveTheGroup();
                    }
                })
                .create()
                .show();
    }

    private void leaveTheGroup() {
        String staticURL = this.getResources().getString(R.string.deployURL) + "group?";
        String url = staticURL + "token=" + student.getToken() + "&groupName=" + group_name + "&action=leaveGroup";
        Log.d("ShowGroup", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String resText = response;
                        //test.setText("Response is: "+ resText);
                        if (resText.equals("failed")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ShowGroupInfoActivity.this);
                            builder.setMessage("FAILED")
                                    .setNegativeButton("RETRY", null)
                                    .create()
                                    .show();
                        } else {
                                    /* success
                                     */
                            student.leaveGroup(group_name);
                            if (return_to_calling_activity) {
                                Intent userMainIntent = new Intent(ShowGroupInfoActivity.this, ShowClassInfoActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("course_name", calling_course_name);
                                userMainIntent.putExtras(bundle);
                                userMainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ShowGroupInfoActivity.this.startActivity(userMainIntent);
                            } else {
                                Intent userMainIntent = new Intent(ShowGroupInfoActivity.this, MygroupActivity.class);
                                userMainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ShowGroupInfoActivity.this.startActivity(userMainIntent);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // access the server fail
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowGroupInfoActivity.this);
                builder.setMessage("ACCESS SERVER FAILED")
                        .setNegativeButton("RETRY LATER", null)
                        .create()
                        .show();
            }
        });
        // Add the request to the RequestQueue
        SBRequestQueue.getInstance(this).addToRequestQueue(stringRequest);
    }
}
