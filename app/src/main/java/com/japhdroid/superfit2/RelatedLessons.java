package com.japhdroid.superfit2;

import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 09.06.2016.
 */
public class RelatedLessons {

    Context context;
    Lesson lesson;

    public RelatedLessons(Context context, Lesson lesson) {
        this.context = context;
        this.lesson = lesson;
        showRelatedLessonsActivity();
    }

    public void showRelatedLessonsActivity() {
        Intent intent = new Intent(context, RelatedLessonsActivity.class);
        intent.putExtra("LESSON_ID", lesson.getId());
        context.startActivity(intent);
    }
}
