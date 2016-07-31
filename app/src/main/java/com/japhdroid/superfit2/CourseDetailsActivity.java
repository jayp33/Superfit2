package com.japhdroid.superfit2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CourseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        int courseId = getIntent().getIntExtra("COURSE_ID", -1);
        TextView courseDetailsTitle = (TextView) findViewById(R.id.course_details_title);

        if (courseId < 0)
            courseDetailsTitle.setText("Error");
        else
            courseDetailsTitle.setText(DataProvider.getCourses().getCourseById(courseId).getTitle());
    }
}
