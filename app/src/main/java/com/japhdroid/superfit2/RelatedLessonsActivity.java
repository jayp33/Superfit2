package com.japhdroid.superfit2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RelatedLessonsActivity extends AppCompatActivity {

    private Lesson lesson;
    private List<Lesson> surroundingLessons;
    private TextView relatedItemsHeader;
    private ListView relatedItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_lessons);
        getExtras();
        bindWidgets();
        populateWidgets();
    }

    private void getExtras() {
        Intent intent = getIntent();
        int lessonId = intent.getIntExtra("LESSON_ID", 0);
        if (lessonId > 0)
            lesson = DataProvider.getLessons().getLessonById(lessonId);
    }

    private void bindWidgets() {
        relatedItemsHeader = (TextView) findViewById(R.id.relatedItemsHeader);
        relatedItemsList = (ListView) findViewById(R.id.relatedItemsList);
    }

    private void populateWidgets() {
        relatedItemsHeader.setText(getLessonHeader(lesson));
        surroundingLessons = DataProvider.getLessons().getSurroundingLessons(lesson);
        List<String> strSurroundingLessons = new ArrayList<>();
        for (Lesson surroundingLesson : surroundingLessons) {
            strSurroundingLessons.add(getLessonDetails(surroundingLesson));
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, strSurroundingLessons);
        relatedItemsList.setAdapter(adapter);
        relatedItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lesson = surroundingLessons.get(position);
                populateWidgets();
            }
        });
    }

    private String getLessonHeader(Lesson parentLesson) {
        String studio = parentLesson.getStudio().getTitle();
        String lessonTitle = parentLesson.getCourse().getTitle();
        String lessonType = parentLesson.getCourse().getFloor().toString();
        String lessonCapacity = parentLesson.getCapacity().toString();
        String lessonWeekday = parentLesson.getWeekday().toString();
        String lessonStarttime = DateTimeParser.getTimeStringFromDate(parentLesson.getStarttime());
        String lessonHeader = studio + "\n" + lessonWeekday + " " + lessonStarttime + "\n" +
                lessonTitle + "\n" + lessonType + ", " + lessonCapacity;
        return lessonHeader;
    }

    private String getLessonDetails(Lesson surroundingLesson) {
        String lessonTitle = surroundingLesson.getCourse().getTitle();
        String lessonType = surroundingLesson.getCourse().getFloor().toString();
        String lessonCapacity = surroundingLesson.getCapacity().toString();
        String lessonStarttime = DateTimeParser.getTimeStringFromDate(surroundingLesson.getStarttime());
        String lessonDetails = lessonTitle + "\n" + lessonType + ", " + lessonCapacity + ", " + lessonStarttime;
        return lessonDetails;
    }
}
