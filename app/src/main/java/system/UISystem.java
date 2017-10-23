package system;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.linegrillpresent.studybuddy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import SBRequestManager.SBRequestQueue;

/**
 * Created by Ao on 2017-10-22.
 */

public class UISystem {
    //private ArrayList<String> courseNames;
    private static UISystem instance;

    private ArrayList<Course> courseNames;
    private UISystem() {
        courseNames = new ArrayList<Course>();
    }

    public static UISystem getInstance() {
        if(instance != null)
            return instance;

        instance = new UISystem();
        return instance;
    }




    public List<String> getAllCourseNum(String name) {
        List<String> returnList = new ArrayList<String>();

        for(int i = 0;i < courseNames.size();i++)
            if(courseNames.get(i).getName().equals(name))
                returnList.add(Integer.toString(courseNames.get(i).getNum()));
        return returnList;

    }

    public ArrayList<Course> getCourseNames(Activity this_act) {

        final Activity activity = this_act;
        if(courseNames.isEmpty()) {
            String url = this_act.getResources().getString(R.string.deployURL) + "course?action=getAllCourseNames";
            Log.d("newgroup", url);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            //receive the list of all course name
                            int length = response.length();
                            Log.d("newgroup", "length = " + Integer.toString(length));
                            for(int i = 0; i < length;i++)
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    String courseName = object.getString("courseName");
                                    int courseNum = object.getInt("courseNum");
                                    int courseID = object.getInt("id");
                                    Course course = new Course(courseID, courseName, courseNum);
                                    courseNames.add(course);

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
        Log.d("newgroup", "coursename has" + Integer.toString(courseNames.size()));
        return courseNames;
    }
}
