package com.japhdroid.superfit2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {

    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        int courseId = getIntent().getIntExtra("COURSE_ID", -1);

        if (courseId < 0) {
            Toast.makeText(CourseDetailsActivity.this, "Kurs nicht gefunden", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            course = DataProvider.getCourses().getCourseById(courseId);
        }
        setCourseTitle();
        setLessonCount();
        setTimespans();
    }

    private void setCourseTitle() {
        String title = course.getTitle();
        String duration = String.valueOf(course.getDuration());
        TextView courseDetailsTitle = (TextView) findViewById(R.id.course_details_title);
        courseDetailsTitle.setText(title + " (" + duration + "m)");
    }

    private void setLessonCount() {
        TextView courseCountMonday = (TextView) findViewById(R.id.course_count_monday);
        TextView courseCountTuesday = (TextView) findViewById(R.id.course_count_tuesday);
        TextView courseCountWednesday = (TextView) findViewById(R.id.course_count_wednesday);
        TextView courseCountThursday = (TextView) findViewById(R.id.course_count_thursday);
        TextView courseCountFriday = (TextView) findViewById(R.id.course_count_friday);
        TextView courseCountSaturday = (TextView) findViewById(R.id.course_count_saturday);
        TextView courseCountSunday = (TextView) findViewById(R.id.course_count_sunday);

        setLessonCountIfAny(courseCountMonday, DataProvider.getLessons().getLessons(course, Lesson.Weekday.MONDAY).size());
        setLessonCountIfAny(courseCountTuesday, DataProvider.getLessons().getLessons(course, Lesson.Weekday.TUESDAY).size());
        setLessonCountIfAny(courseCountWednesday, DataProvider.getLessons().getLessons(course, Lesson.Weekday.WEDNESDAY).size());
        setLessonCountIfAny(courseCountThursday, DataProvider.getLessons().getLessons(course, Lesson.Weekday.THURSDAY).size());
        setLessonCountIfAny(courseCountFriday, DataProvider.getLessons().getLessons(course, Lesson.Weekday.FRIDAY).size());
        setLessonCountIfAny(courseCountSaturday, DataProvider.getLessons().getLessons(course, Lesson.Weekday.SATURDAY).size());
        setLessonCountIfAny(courseCountSunday, DataProvider.getLessons().getLessons(course, Lesson.Weekday.SUNDAY).size());
    }

    private void setLessonCountIfAny(TextView textview, int count) {
        if (count > 0)
            textview.setText(String.valueOf(count));
    }

    private void setTimespans() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);

        TextView course_timespan_0to10_monday = (TextView) findViewById(R.id.course_timespan_0to10_monday);
        TextView course_timespan_10to14_monday = (TextView) findViewById(R.id.course_timespan_10to14_monday);
        TextView course_timespan_14to18_monday = (TextView) findViewById(R.id.course_timespan_14to18_monday);
        TextView course_timespan_18to0_monday = (TextView) findViewById(R.id.course_timespan_18to0_monday);
        if (weekday == 2)
            highlightTimespans(Arrays.asList(course_timespan_0to10_monday, course_timespan_10to14_monday, course_timespan_14to18_monday, course_timespan_18to0_monday));

        TextView course_timespan_0to10_tuesday = (TextView) findViewById(R.id.course_timespan_0to10_tuesday);
        TextView course_timespan_10to14_tuesday = (TextView) findViewById(R.id.course_timespan_10to14_tuesday);
        TextView course_timespan_14to18_tuesday = (TextView) findViewById(R.id.course_timespan_14to18_tuesday);
        TextView course_timespan_18to0_tuesday = (TextView) findViewById(R.id.course_timespan_18to0_tuesday);
        if (weekday == 3)
            highlightTimespans(Arrays.asList(course_timespan_0to10_tuesday, course_timespan_10to14_tuesday, course_timespan_14to18_tuesday, course_timespan_18to0_tuesday));

        TextView course_timespan_0to10_wednesday = (TextView) findViewById(R.id.course_timespan_0to10_wednesday);
        TextView course_timespan_10to14_wednesday = (TextView) findViewById(R.id.course_timespan_10to14_wednesday);
        TextView course_timespan_14to18_wednesday = (TextView) findViewById(R.id.course_timespan_14to18_wednesday);
        TextView course_timespan_18to0_wednesday = (TextView) findViewById(R.id.course_timespan_18to0_wednesday);
        if (weekday == 4)
            highlightTimespans(Arrays.asList(course_timespan_0to10_wednesday, course_timespan_10to14_wednesday, course_timespan_14to18_wednesday, course_timespan_18to0_wednesday));

        TextView course_timespan_0to10_thursday = (TextView) findViewById(R.id.course_timespan_0to10_thursday);
        TextView course_timespan_10to14_thursday = (TextView) findViewById(R.id.course_timespan_10to14_thursday);
        TextView course_timespan_14to18_thursday = (TextView) findViewById(R.id.course_timespan_14to18_thursday);
        TextView course_timespan_18to0_thursday = (TextView) findViewById(R.id.course_timespan_18to0_thursday);
        if (weekday == 5)
            highlightTimespans(Arrays.asList(course_timespan_0to10_thursday, course_timespan_10to14_thursday, course_timespan_14to18_thursday, course_timespan_18to0_thursday));

        TextView course_timespan_0to10_friday = (TextView) findViewById(R.id.course_timespan_0to10_friday);
        TextView course_timespan_10to14_friday = (TextView) findViewById(R.id.course_timespan_10to14_friday);
        TextView course_timespan_14to18_friday = (TextView) findViewById(R.id.course_timespan_14to18_friday);
        TextView course_timespan_18to0_friday = (TextView) findViewById(R.id.course_timespan_18to0_friday);
        if (weekday == 6)
            highlightTimespans(Arrays.asList(course_timespan_0to10_friday, course_timespan_10to14_friday, course_timespan_14to18_friday, course_timespan_18to0_friday));

        TextView course_timespan_0to10_saturday = (TextView) findViewById(R.id.course_timespan_0to10_saturday);
        TextView course_timespan_10to14_saturday = (TextView) findViewById(R.id.course_timespan_10to14_saturday);
        TextView course_timespan_14to18_saturday = (TextView) findViewById(R.id.course_timespan_14to18_saturday);
        TextView course_timespan_18to0_saturday = (TextView) findViewById(R.id.course_timespan_18to0_saturday);
        if (weekday == 7)
            highlightTimespans(Arrays.asList(course_timespan_0to10_saturday, course_timespan_10to14_saturday, course_timespan_14to18_saturday, course_timespan_18to0_saturday));

        TextView course_timespan_0to10_sunday = (TextView) findViewById(R.id.course_timespan_0to10_sunday);
        TextView course_timespan_10to14_sunday = (TextView) findViewById(R.id.course_timespan_10to14_sunday);
        TextView course_timespan_14to18_sunday = (TextView) findViewById(R.id.course_timespan_14to18_sunday);
        TextView course_timespan_18to0_sunday = (TextView) findViewById(R.id.course_timespan_18to0_sunday);
        if (weekday == 1)
            highlightTimespans(Arrays.asList(course_timespan_0to10_sunday, course_timespan_10to14_sunday, course_timespan_14to18_sunday, course_timespan_18to0_sunday));

        course_timespan_0to10_monday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.MONDAY, Lesson.Timespan._0to10).size()));
        course_timespan_10to14_monday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.MONDAY, Lesson.Timespan._10to14).size()));
        course_timespan_14to18_monday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.MONDAY, Lesson.Timespan._14to18).size()));
        course_timespan_18to0_monday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.MONDAY, Lesson.Timespan._18to0).size()));

        course_timespan_0to10_tuesday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.TUESDAY, Lesson.Timespan._0to10).size()));
        course_timespan_10to14_tuesday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.TUESDAY, Lesson.Timespan._10to14).size()));
        course_timespan_14to18_tuesday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.TUESDAY, Lesson.Timespan._14to18).size()));
        course_timespan_18to0_tuesday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.TUESDAY, Lesson.Timespan._18to0).size()));

        course_timespan_0to10_wednesday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.WEDNESDAY, Lesson.Timespan._0to10).size()));
        course_timespan_10to14_wednesday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.WEDNESDAY, Lesson.Timespan._10to14).size()));
        course_timespan_14to18_wednesday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.WEDNESDAY, Lesson.Timespan._14to18).size()));
        course_timespan_18to0_wednesday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.WEDNESDAY, Lesson.Timespan._18to0).size()));

        course_timespan_0to10_thursday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.THURSDAY, Lesson.Timespan._0to10).size()));
        course_timespan_10to14_thursday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.THURSDAY, Lesson.Timespan._10to14).size()));
        course_timespan_14to18_thursday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.THURSDAY, Lesson.Timespan._14to18).size()));
        course_timespan_18to0_thursday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.THURSDAY, Lesson.Timespan._18to0).size()));

        course_timespan_0to10_friday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.FRIDAY, Lesson.Timespan._0to10).size()));
        course_timespan_10to14_friday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.FRIDAY, Lesson.Timespan._10to14).size()));
        course_timespan_14to18_friday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.FRIDAY, Lesson.Timespan._14to18).size()));
        course_timespan_18to0_friday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.FRIDAY, Lesson.Timespan._18to0).size()));

        course_timespan_0to10_saturday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.SATURDAY, Lesson.Timespan._0to10).size()));
        course_timespan_10to14_saturday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.SATURDAY, Lesson.Timespan._10to14).size()));
        course_timespan_14to18_saturday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.SATURDAY, Lesson.Timespan._14to18).size()));
        course_timespan_18to0_saturday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.SATURDAY, Lesson.Timespan._18to0).size()));

        course_timespan_0to10_sunday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.SUNDAY, Lesson.Timespan._0to10).size()));
        course_timespan_10to14_sunday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.SUNDAY, Lesson.Timespan._10to14).size()));
        course_timespan_14to18_sunday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.SUNDAY, Lesson.Timespan._14to18).size()));
        course_timespan_18to0_sunday.setText(getStudioCountBullets(DataProvider.getLessons().getStudiosForTimespan(course, Lesson.Weekday.SUNDAY, Lesson.Timespan._18to0).size()));
    }

    private String getStudioCountBullets(int count) {
        String studioCountBullets = "";
        for (int i = 0; i < count; i++) {
            studioCountBullets += "\u2022";
        }
        return studioCountBullets;
    }

    private void highlightTimespans(List<TextView> textViews) {
        for (TextView textview : textViews) {
            textview.setBackgroundColor(Color.parseColor("#f0e792"));
        }
    }
}
