package com.japhdroid.superfit2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<List<SuperfitCourse>> courseListByTime;
    private List<List<SuperfitCourse>> courseListByName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        doSomething(null);
    }

    public void doSomething(View view) {
        new LoadData().execute();
    }

    public void showByTime(View view) {
        if (courseListByTime.size() > 0) {
            mAdapter = new MyAdapter(courseListByTime);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void showByName(View view) {
        if (courseListByName.size() > 0) {
            mAdapter = new MyAdapter(courseListByName);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private class LoadData extends AsyncTask<SuperfitParser, Void, String> {
        @Override
        protected String doInBackground(SuperfitParser... params) {
            courseListByTime = new SuperfitCourseCollection().getCourseCollections();
            sortCourseListByName(courseListByTime);
            return null;
        }

        private void sortCourseListByName(List<List<SuperfitCourse>> courseListByTime) {
            List<String> courseNames = new ArrayList<>();
            for (List<SuperfitCourse> courseList : courseListByTime) {
                if (!courseNames.contains(courseList.get(0).getName()))
                    courseNames.add(courseList.get(0).getName());
            }
            Collections.sort(courseNames);
            courseListByName = new ArrayList<>();
            for (String courseName : courseNames) {
                for (List<SuperfitCourse> courseList : courseListByTime) {
                    if (courseList.get(0).getName().equals(courseName))
                        courseListByName.add(courseList);
                }
            }
        }

        @Override
        protected void onPostExecute(String dummy) {
            if (courseListByName.size() > 0) {
                mAdapter = new MyAdapter(courseListByName);
                mRecyclerView.setAdapter(mAdapter);
            }
            Button btn = (Button) findViewById(R.id.name_btn);
            btn.setEnabled(true);
            btn = (Button) findViewById(R.id.time_btn);
            btn.setEnabled(true);
        }
    }
}
