package com.linegrillpresent.studybuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import SBRequestManager.SBRequestQueue;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etEmail = (EditText) findViewById(R.id.textEmail);
        final EditText etUsername = (EditText) findViewById(R.id.textUsername);
        final EditText etPassword = (EditText) findViewById(R.id.textPassword);
        final EditText etName = (EditText) findViewById(R.id.textStudentName);

        final Button bRegister = (Button) findViewById(R.id.buttonRegister);
        final SBRequestQueue SBQueue = SBRequestQueue.getInstance(this);


        final TextView test1 = (TextView) findViewById(R.id.test1);
        final TextView test2 = (TextView) findViewById(R.id.test2);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String email = etEmail.getText().toString();
                final String name = etName.getText().toString();



                String url ="http://206.87.128.30:8080/Servlet/register?username=" + username + "&password=" + password
                                            +"&email="+ email + "&studentName=" + name;

                test1.setText(url);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String resText = response.toString();
                                test2.setText(resText);
                                //test.setText("Response is: "+ resText);
                                if(resText.equals("succeed") ) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Register Succeed, Plase Login")
                                            .setNegativeButton("CLOSE", null)
                                            .setPositiveButton("LOGIN",  new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        //do things
                                                        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                        RegisterActivity.this.startActivity(loginIntent);
                                                    }
                                                })
                                            .create()
                                            .show();
                                } else if(resText.equals("usernameExists")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("The username has been used")
                                            .setNegativeButton("CLOSE", null)
                                            .create()
                                            .show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("System is busy, Plase try later")
                                            .setNegativeButton("OK I Understand", null)
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

               // queue.add(registerRequest);
            }
        });

    }
}
