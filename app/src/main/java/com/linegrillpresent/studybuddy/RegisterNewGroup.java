package com.linegrillpresent.studybuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import SBRequestManager.SBRequestQueue;
import Users.Student;

public class RegisterNewGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_group);
        final SBRequestQueue SBQueue = SBRequestQueue.getInstance(this);
        final EditText et_name = (EditText) findViewById(R.id.et_GroupName);
        //final EditText et_desc = (EditText) findViewById(R.id.et_desc);
        Button btm = (Button) findViewById(R.id.btm_RegisterGroup);


        btm.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = et_name.getText().toString();
                //String et_desc = et_desc.getText().toString;

                Bundle bundle = getIntent().getExtras();

                final Student student = (Student) bundle.getSerializable("student");
                String url ="http://206.87.128.30:8080/Servlet/group?token=" + student.getToken() + "&action=createGroup&groupName=" + name;

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
}
