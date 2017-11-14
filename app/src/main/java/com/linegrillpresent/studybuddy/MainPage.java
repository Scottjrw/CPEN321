package com.linegrillpresent.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import system.Course;
import system.Utility;
import user.Student;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MycourseFragment.OnFragmentInteractionListener,MyprofileFragment.OnFragmentInteractionListener
        ,MygroupFragment.OnFragmentInteractionListener{
    private String token;
    private Student student_user;
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ctx = this;

        Button btn1 = (Button) findViewById(R.id.myprofile);
        Button btn2 = (Button) findViewById(R.id.mycourses);
        Button btn3 = (Button) findViewById(R.id.mygroups);
        Button btn4 = (Button) findViewById(R.id.button4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(ctx, MyprofileActivity.class);
                ctx.startActivity(userMainIntent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(ctx, MycourseActivity.class);
                ctx.startActivity(userMainIntent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(ctx, MygroupActivity.class);
                ctx.startActivity(userMainIntent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Log.d("cpen", "oncreate");

        //get Token passed in
        student_user = Student.getInstance();
        student_user.updateInfo(this);
        student_user.updateCourseInfo(this);
        student_user.updateGroupInfo(this);

        //student_user
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main_page, menu);

        Log.d("cpen", "menu");

        final TextView myUnameDisplay = (TextView) findViewById(R.id.Username);
        final TextView myEmailDisplay = (TextView) findViewById(R.id.Email);

       myUnameDisplay.setText(student_user.getName());
       myEmailDisplay.setText(student_user.getEmail());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager manager = getSupportFragmentManager();
        if (id == R.id.Profile) {
            MyprofileFragment profilefraement = MyprofileFragment.newInstance(student_user);
            manager.beginTransaction().replace(R.id.layout_for_fragments,profilefraement,profilefraement.getTag()).commit();
        } else if (id == R.id.Course) {
            ArrayList<Course> temp = (ArrayList<Course>) student_user.getCourses();
            for(int i = 0; i < temp.size();i++){
                student_user.updateGroupsUnderCourse(this,temp.get(i));
            }
            MycourseFragment coursefragment = MycourseFragment.newInstance("course","blablah");
            Utility.getInstance().updateAllAvailableCourses(this);
            manager.beginTransaction().replace(R.id.layout_for_fragments,coursefragment,coursefragment.getTag()).commit();
        } else if (id == R.id.Group) {
            MygroupFragment groupfragment = MygroupFragment.newInstance(student_user);
            manager.beginTransaction().replace(R.id.layout_for_fragments,groupfragment,groupfragment.getTag()).commit();
        } else if (id == R.id.Setting) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
           
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
