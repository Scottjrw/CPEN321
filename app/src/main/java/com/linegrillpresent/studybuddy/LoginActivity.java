package com.linegrillpresent.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import SBRequestManager.SBRequestQueue;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etPassword = (EditText) findViewById(R.id.textPassword);
        final EditText etName = (EditText) findViewById(R.id.textUsername);
        final Button bLogin = (Button) findViewById(R.id.buttonLogin);
        final TextView registerLink = (TextView) findViewById(R.id.textRN);


        final SBRequestQueue SBQueue = SBRequestQueue.getInstance(this);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                final String username = etName.getText().toString().toLowerCase();
                final String password = etPassword.getText().toString();

                String staticURL = getResources().getString(R.string.deployURL) + "login?";
                String url = staticURL + "username=" + username + "&password=" + password;
                Log.d("login", url);
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String resText = response;
                                //test.setText("Response is: "+ resText);
                                if (resText.equals("failed")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("LOGIN FAILED")
                                            .setNegativeButton("RETRY", null)
                                            .create()
                                            .show();
                                } else {
                                    /* success Login
                                       Save the token in a Bundle object and pass it to the userMainActivity
                                     */
                                    Intent userMainIntent = new Intent(LoginActivity.this, WaitActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("token", resText);
                                    userMainIntent.putExtras(bundle);
                                    LoginActivity.this.startActivity(userMainIntent);
                                    finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // access the server fail
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("ACCESS SERVER FAILED")
                                .setNegativeButton("RETRY LATER", null)
                                .create()
                                .show();
                    }
                });
                // Add the request to the RequestQueue
                SBQueue.addToRequestQueue(stringRequest);
            }
        });

    }
}
