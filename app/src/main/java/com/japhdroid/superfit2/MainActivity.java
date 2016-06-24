package com.japhdroid.superfit2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Preferences.setCacheTimeout(this, 1000 * 60 * 60 * 24 * 7);  // one week

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        doSomething(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void doSomething(View view) {
        new LoadData().execute();
    }

    public void reloadDataFromServer(MenuItem item) {
        DataProvider.invalidateData();
        doSomething(null);
    }

    public void showByTime(View view) {
        TreeMap<Date, List<Lesson>> lessons = DataProvider.getLessons().getLessonsGroupedByStarttime();
        if (lessons.size() > 0) {
            mAdapter = new StarttimeAdapter(MainActivity.this, lessons);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void showByName(View view) {
        Map<Course, List<Lesson>> lessons = DataProvider.getLessons().getLessonCollections();
        if (lessons.size() > 0) {
            mAdapter = new MyAdapter(MainActivity.this, lessons, DataProvider.getLessons().getSortingByName());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private class LoadData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            Map urls = new HashMap<DataProvider.DataType, String[]>();
            urls.put(DataProvider.DataType.STUDIOS, new String[]{"http://superfit.navillo.de/api/v3/studios.json"});
            urls.put(DataProvider.DataType.COURSES, new String[]{"http://superfit.navillo.de/api/v3/courses.json"});
            urls.put(DataProvider.DataType.LESSONS, new String[]{"http://superfit.navillo.de/api/v3/lessons.json?studio_id=3",
                    "http://superfit.navillo.de/api/v3/lessons.json?studio_id=5"});
            DataProvider.LoadData(getApplicationContext(), urls);
            return null;
        }

        @Override
        protected void onPostExecute(String dummy) {
            if (DataProvider.getLessons().getLessons().size() > 0) {
                mAdapter = new MyAdapter(MainActivity.this,
                        DataProvider.getLessons().getLessonCollections(),
                        DataProvider.getLessons().getSortingByName());
                mRecyclerView.setAdapter(mAdapter);
            }
            Button btn = (Button) findViewById(R.id.name_btn);
            btn.setEnabled(true);
            btn = (Button) findViewById(R.id.time_btn);
            btn.setEnabled(true);
        }
    }
}
