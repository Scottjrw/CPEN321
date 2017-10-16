package Users;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import SBRequestManager.SBRequestQueue;

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
    public boolean isSet;

    public void updateInfo(Activity this_act) {
        String url ="http://206.87.128.30:8080/Servlet/main?token=" + token;
        final Activity activity = this_act;
        //final TextView dia = d;
        //dia.setText(url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //receive the student'info
                        try {
                            //dia.setText("YES");
                            name = response.get("studentName").toString();
                            email = response.get("email").toString();
                            username = response.get("username").toString();
                            numberOfGroups = response.getInt("numOfGroups");
                            isSet = true;
                            Log.d("hello", "username=" + username);
                        } catch (JSONException e) {
                            //dia.setText("NO");
                            e.printStackTrace();
                            name = null;
                            email = null;
                            username = "not set";
                            motto = null;
                            numberOfGroups = 0;
                            numberOfCourses = 0;
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // fail to receive the student'info based on the token


                    }
                });
        SBRequestQueue.getInstance(activity).addToRequestQueue(jsObjRequest);

    }

    public Student(String t){
        token = t;
        isSet = false;
    }

    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }


}
