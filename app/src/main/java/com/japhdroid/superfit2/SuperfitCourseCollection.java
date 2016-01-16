package com.japhdroid.superfit2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by User on 13.12.2015.
 */
public class SuperfitCourseCollection {

    private List<SuperfitCourse> courseCollection;

    public List<SuperfitCourse> getCourseCollection() {
        courseCollection = new ArrayList<>();
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain", 0, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain", 0, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte", 0, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte", 0, true).getCourseList());

        Collections.sort(courseCollection);
        return courseCollection;
    }

    private void addListToCollection(List<SuperfitCourse> list) {
        for (SuperfitCourse course : list) {
            courseCollection.add(course);
        }
    }
}
