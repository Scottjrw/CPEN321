package com.linegrillpresent.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import user.Student;

public class MycourseActivity extends AppCompatActivity {
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourse);
        // Inflate the layout for this fragment
        ctx = this;
        ListView listView = (ListView) findViewById(R.id.course_listview);
        TextView overview = (TextView) findViewById(R.id.course_tv_overview);
        Button btm = (Button) findViewById(R.id.btm_jnc);
        Student student = Student.getInstance();
        btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(ctx, RegisterNewCourse.class);
                ctx.startActivity(userMainIntent);
            }
        });

        overview.setText("You have registered for " + student.getNumberOfCourses() + " courses:");
        Log.d("onclickcourse", "onCreateView: " + student.getNumberOfCourses());
        final String[] listItems = new String[student.getNumberOfCourses()];
        for (int i = 0; i < student.getNumberOfCourses(); i++)
            listItems[i] = student.getCourses().get(i).getFullName();
        //ArrayAdapter adapter = new ArrayAdapter(view, list_view, listItems);
        ArrayAdapter adapter = new ArrayAdapter(ctx, R.layout.list_view, listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectCourseName = listItems[position];
                Log.d("click", selectCourseName);
                Intent showGroupInfoIntent = new Intent(ctx, ShowClassInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("course_name", selectCourseName);
                showGroupInfoIntent.putExtras(bundle);
                ctx.startActivity(showGroupInfoIntent);
            }
        });
    }
}
