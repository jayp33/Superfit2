package com.japhdroid.superfit2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        SuperfitParser parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain", 0, false);
        new LoadData().execute(parser);
    }

    private class LoadData extends AsyncTask<SuperfitParser, Void, List<SuperfitCourse>> {
        @Override
        protected List<SuperfitCourse> doInBackground(SuperfitParser... params) {
            params[0].LoadData();
            return params[0].getCourseList();
        }

        @Override
        protected void onPostExecute(List<SuperfitCourse> courseList) {
            if (courseList.size() > 0) {
                mAdapter = new MyAdapter(courseList);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }
}
