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

import java.util.ArrayList;
import java.util.List;

import SBRequestManager.SBRequestQueue;
import system.Course;
import system.InvalidCourseException;

/**
 * Created by Ao on 2017-10-15.
 */

public class Student implements User {

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

    public static Student getInstance() {
        if (instance == null)
            instance = new Student();
        return instance;
    }

    public void updateGroupsUnderCourse(Activity this_act, final Course course) {
        String staticURL = this_act.getResources().getString(R.string.deployURL) + "course?";
        String url = staticURL + "token=" + token + "&courseId=" + course.getID() + "&action=listGroupsUnderCourse";
        final Activity activity = this_act;

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //receive the student'info
                        int length = response.length();
                        for (int i = 0; i < length; i++)
                            try {
                                course.addGroups(response.getString(i));
                                Log.d("addG", "onResponse:" + response.getString(i));
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

    public void updateGroupInfo(Activity this_act) {
        String staticURL = this_act.getResources().getString(R.string.deployURL) + "group?";
        String url = staticURL + "token=" + token + "&action=listGroup";
        final Activity activity = this_act;

        // groups = new ArrayList<String>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //receive the student'info
                        int length = response.length();
                        for (int i = 0; i < length; i++)
                            try {
                                if (!groups.contains(response.getString(i)))
                                    groups.add(response.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // fail to receive the student'info based on the token
                    }
                });
        SBRequestQueue.getInstance(activity).addToRequestQueue(jsonArrayRequest);
    }

    public void updateCourseInfo(Activity this_act) {
        String staticURL = this_act.getResources().getString(R.string.deployURL) + "course?";
        String url = staticURL + "token=" + token + "&action=listCourses";
        final Activity activity = this_act;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //receive the list of all course name
                        int length = response.length();
                        try {
                            setNumberOfCourses(length);
                        } catch (InvalidUserException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < length; i++)
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Course course = new Course(object.getInt("id"), object.getString("courseName"), object.getInt("courseNum"), object.getString("description"));
                                if (!courses.contains(course))
                                    courses.add(course);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (InvalidCourseException e) {
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
                            setName(response.get("studentName").toString());
                            setEmail(response.get("email").toString());
                            setUsername(response.get("username").toString());
                            setNumberOfGroups(response.getInt("numOfGroups"));
                            isSet = true;
                        } catch (JSONException e) {
                            Log.d("Student", "CANNOT UPDATE INFO");
                            name = null;
                            email = null;
                            username = "not set";
                            motto = null;
                            numberOfGroups = 0;
                            numberOfCourses = 0;

                            e.printStackTrace();
                        } catch (InvalidUserException e) {
                            e.printStackTrace();
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
    public void setToken(String s) throws InvalidUserException {
        if (s == null || s.isEmpty()) throw new InvalidUserException();
        this.token = s;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setName(String name) throws InvalidUserException {
        if (name == null || name.isEmpty()) throw new InvalidUserException();
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setUsername(String username) throws InvalidUserException {
        if (username == null || username.isEmpty()) throw new InvalidUserException();
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setEmail(String Email) throws InvalidUserException {
        if (Email == null || Email.isEmpty()) throw new InvalidUserException();
        this.email = Email;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    @Override
    public void setNumberOfGroups(int groups) throws InvalidUserException {
        if (groups < 0) throw new InvalidUserException();
        this.numberOfGroups = groups;
    }

    @Override
    public int getNumberOfCourses() {
        return numberOfCourses;
    }

    @Override
    public void setNumberOfCourses(int courses) throws InvalidUserException {
        if (courses < 0) throw new InvalidUserException();
        this.numberOfCourses = courses;
    }

    @Override
    public List<String> getGroups() {
        return groups;
    }

    @Override
    public List<Course> getCourses() {
        return courses;
    }

    public Course getCourseObj(String course_name) {
        for (int i = 0; i < courses.size(); i++)
            if (courses.get(i).getFullName().equals(course_name))
                return courses.get(i);
        return null;
    }

    public String getCourseNameByID(int id) {
        for (int i = 0;i < courses.size();i++)
            if(courses.get(i).getID() == id)
                return courses.get(i).getFullName();
        return "Course UNKNOWN";
    }

    public boolean joinCourseOrNot(String course_name) {
        /*
        if(courses == null)
            updateCourseInfo(this_act);
        */
        Log.d("courseTest", "***COURSENAME IS " + course_name);
        /*
        for(int i = 0;i < courses.size();i++) {
            Log.d("courseTest", "course" + i + "=" + courses.get(i).getFullName() + "\n");
            if (groups.get(i).get.equals(course_name))
                return true;
        }*/
        return groups.contains(course_name);
        //return false;
    }

    public void leaveGroup(String group_name) {
        if (groups.contains(group_name)) {
            groups.remove(group_name);
            numberOfGroups--;
        } else
            Log.d("ShowGroup", "FATAL ERROR");
    }

    public void joinGroup(String group_name) {
        groups.add(group_name);
        numberOfGroups++;
    }

    public void leaveCourse(String course_name) {
        Course courseObj = getCourseObj(course_name);
        if (courses.contains(courseObj)) {
            courses.remove(courseObj);
            numberOfCourses--;
        } else
            Log.d("ShowClass", "FATAL ERROR");
    }

    public void joinCourse(Course tob_be_registered) {
        Course courseObj = tob_be_registered;
        courses.add(courseObj);
        numberOfCourses++;
    }

    public void destroy() {
        token = null;
        name = null;
        email = null;
        username = null;
        numberOfCourses = 0;
        numberOfGroups = 0;
        groups = null;
        courses = null;
        motto = null;
        isSet = false;
        instance = null;
    }
}
