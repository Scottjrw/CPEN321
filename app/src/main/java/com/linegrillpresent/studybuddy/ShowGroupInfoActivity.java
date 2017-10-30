package com.linegrillpresent.studybuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowCourseInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_course_info);

        Bundle tokenBundle = getIntent().getExtras();
        String course_id = tokenBundle.getString("course_id");

        TextView test = (TextView) findViewById(R.id.textView);
        test.setText(course_id);
    }
}
