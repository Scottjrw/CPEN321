package com.linegrillpresent.studybuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import SBRequestManager.SBRequestQueue;
import user.Student;

public class ShowGroupInfoActivity extends AppCompatActivity {

    private String group_name;
    private final List<String> groupMembers =  new ArrayList<String>();
    private ListView groupMemList;
    private ListView disBoardList;
    private Button   postButton;
    private EditText postEditText;
    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_group_info);

        Bundle tokenBundle = getIntent().getExtras();
        group_name = tokenBundle.getString("group_name");

        //-----get view fragment---------------
        groupMemList = (ListView) findViewById(R.id.listview_GroupMemList);
        disBoardList = (ListView) findViewById(R.id.listview_disBoard);
        postButton = (Button) findViewById(R.id.btn_Post);
        postEditText = (EditText) findViewById(R.id.et_typeinfield);
        //---------------------

        student = Student.getInstance();
        requestGroupInfo();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postString = postEditText.getText().toString();
                String token = student.getToken();

                String staticURL = getResources().getString(R.string.deployURL) + "group?action=post&";
            }
        });


    }

    private void requestGroupInfo() {

        String token = student.getToken();
        String staticURL = getResources().getString(R.string.deployURL) + "group?action=listUser&";
        String url =  staticURL + "token=" + token + "&groupName=" + group_name;
        Log.d("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //receive ALL THE GROUP MEMBER'info
                        int length = response.length();
                        for(int i = 0; i < length;i++)
                            try {
                                if(!groupMembers.contains(response.getString(i)))
                                    groupMembers.add(response.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        Log.d("hello", "length is " + length);
                        initializeMemberList();
                        initializeDisBoardList();
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
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groupMembers);
        Log.d("group", "Group member list has length " + groupMembers.size());
        groupMemList.setAdapter(itemsAdapter);
    }

    private void initializeDisBoardList() {
        HashMap<String, String> messages = new HashMap<>();
        HashMap<String, Integer> timePriority = new HashMap<>();
        messages.put("tangao","hello");
        messages.put("chenyi","caonixuema");
        messages.put("kanglong","it should work");

        timePriority.put("tangao"+"hello", 1);
        timePriority.put("chenyi"+"caonixuema",2);
        timePriority.put("kanglong"+"it should work",3);

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(
                this, listItems, R.layout.two_line_list_view,
                new String[]{"First line", "Second line"},
                new int[]{R.id.text1, R.id.text2}
        );

        Iterator it = messages.entrySet().iterator();
        while(it.hasNext()) {
            HashMap<String, String> resultMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultMap.put("First line", pair.getKey().toString());
            resultMap.put("Second line", pair.getValue().toString());
            listItems.add(resultMap);
        }

        disBoardList.setAdapter(adapter);
    }
}
