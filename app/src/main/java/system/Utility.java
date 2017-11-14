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
import user.Student;

/**
 * Created by REX on 2017/10/29.
 */

public class Utility {
    private static Utility instance;
    private List<Course> availableCourses;

    private Utility(){
        availableCourses = new ArrayList<Course>();
    }

    public static Utility getInstance() {
        if (instance == null) {
            instance = new Utility();
        }
        return instance;
    }

    public void updateAllAvailableCourses(Activity this_act){
        String staticURL = this_act.getResources().getString(R.string.deployURL) + "course?";
        String url = staticURL + "token=" + Student.getInstance().getToken() + "&action=getAllAvailableCourses";
        final Activity activity = this_act;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //receive the list of all course name
                        int length = response.length();

                        for(int i = 0; i < length;i++)
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Course course = new Course( object.getInt("id"), object.getString("courseName"), object.getInt("courseNum"), object.getString("description"));
                                if(!availableCourses.contains(course))
                                    availableCourses.add(course);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (InvalidCourseException e) {
                                e.printStackTrace();
                            }
                        Log.d("UtilityCourse", "length is " + length);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // fail to receive the student'info based on the token
                    }
                });
        SBRequestQueue.getInstance(activity).addToRequestQueue(jsonArrayRequest);
    }

    public List<Course> getAllAvailableCourses(){

        return availableCourses;
    }

}
