package user;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.linegrillpresent.studybuddy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import SBRequestManager.SBRequestQueue;
import system.Course;

/**
 * Created by Ao on 2017-10-15.
 */

public class Student implements User, Serializable {

    private String token;
    private String name;
    private String email;
    private String username;
    private int numberOfCourses;
    private int numberOfGroups;
    private List<String> groups;
    private List<Course> courses;
    private String motto;
    public boolean isSet;

    private static Student instance;

    private Student() {
        isSet = false;
        groups = new ArrayList<String>();
        courses = new ArrayList<Course>();
    }

    public void setToken(String s) {
        token = s;
    }

    public static Student getInstance() {
        if(instance == null)
            instance = new Student();
        return instance;
    }


    public void  updateGroupInfo(Activity this_act) {
        String staticURL = this_act.getResources().getString(R.string.deployURL) + "group?";
        String url = staticURL + "token=" + token + "&action=listGroup";
        final Activity activity = this_act;

        JsonArrayRequest  jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //receive the student'info
                        int length = response.length();
                        for(int i = 0; i < length;i++)
                            try {
                                if(!groups.contains(response.getString(i)))
                                    groups.add(response.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        Log.d("hello", "length is " + length);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // fail to receive the student'info based on the token


                    }
                });
        SBRequestQueue.getInstance(activity).addToRequestQueue(jsonArrayRequest);
    }


    public void  updateCourseInfo(Activity this_act) {
        String staticURL = this_act.getResources().getString(R.string.deployURL) + "course?";
        String url = staticURL + "token=" + token + "&action=listCourses";
        final Activity activity = this_act;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //receive the list of all course name
                        int length = response.length();
                        numberOfCourses = length;
                        for(int i = 0; i < length;i++)
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String courseName = object.getString("courseName");
                                int courseNum = object.getInt("courseNum");
                                int courseID = object.getInt("id");
                                Course course = new Course(courseID, courseName, courseNum);
                                courses.add(course);
                                Log.d("helloCourse", "onResponse: "+ courseName + courseNum + courseID);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        Log.d("helloCourse", "length is " + length);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // fail to receive the student'info based on the token
                    }
                });
        SBRequestQueue.getInstance(activity).addToRequestQueue(jsonArrayRequest);
    }

    public void updateInfo(Activity this_act) {
        String staticURL = this_act.getResources().getString(R.string.deployURL) + "main?";
        String url = staticURL + "token=" + token;
        final Activity activity = this_act;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //receive the student'info
                        try {
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

    public int getNumberOfGroups() {return numberOfGroups;}

    public List<String> getGroups() {return groups;}

    public List<Course> getCourses() {return courses;}

    public  int getNumberOfCourses(){return  numberOfCourses;}
}
