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

public class MygroupActivity extends AppCompatActivity {
    private Student student;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygroup);
        student = Student.getInstance();
        ctx = this;
        ListView listView = (ListView) findViewById(R.id.listview);
        TextView overview = (TextView) findViewById(R.id.tv_overview);
        Button btm = (Button) findViewById(R.id.btm_rng);


        btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(ctx, RegisterNewGroup.class);
                ctx.startActivity(userMainIntent);
            }
        });


        overview.setText("You have registered for " + student.getNumberOfGroups() + " groups:");


        final String[] listItems = new String[student.getNumberOfGroups()];
        for (int i = 0; i < student.getNumberOfGroups(); i++)
            listItems[i] = student.getGroups().get(i);
        //ArrayAdapter adapter = new ArrayAdapter(view, list_view, listItems);
        ArrayAdapter adapter = new ArrayAdapter(ctx, R.layout.list_view, listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectGroupName = listItems[position];
                Log.d("click", selectGroupName);
                Intent showGroupInfoIntent = new Intent(ctx, ShowGroupInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("group_name", selectGroupName);
                bundle.putString("context", "MygroupActivity");
                showGroupInfoIntent.putExtras(bundle);
                ctx.startActivity(showGroupInfoIntent);
            }
        });
    }


}
