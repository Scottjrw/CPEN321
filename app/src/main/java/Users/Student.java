package Users;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.linegrillpresent.studybuddy.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Ao on 2017-10-15.
 */

public class Student implements User {

    private final String token;
    private String name;
    private String email;
    private String username;
    private int numberOfCourses;
    private int numberOfGroups;
    private String motto;

    public Student(String t, Activity this_act){
        token = t;
        updateInfo(this_act);
    }
    private void updateInfo(Activity this_act) {
        String url ="http://206.87.128.30:8080/Servlet/info?token=" + token;

        final Activity activity = this_act;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //receive the student'info
                        try {
                            name = response.getString("name");
                            email = response.getString("email");
                            username = response.getString("username");
                            numberOfCourses = response.getInt("numberOfCourses");
                            numberOfGroups = response.getInt("numberOfGroups");
                            motto = response.getString("motto");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            name = null;
                            email = null;
                            username = null;
                            motto = null;
                            numberOfGroups = 0;
                            numberOfCourses = 0;
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // fail to receive the student'info based on the token
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Accessing Deny")
                                .setPositiveButton("Re-Login",  new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                        Intent loginIntent = new Intent(activity, LoginActivity.class);
                                        activity.startActivity(loginIntent);
                                    }
                                })
                                .create()
                                .show();

                    }
                });
    }


    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }

    @Override
    public String getToken() {
        return token;
    }


}
