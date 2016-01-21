package com.japhdroid.superfit2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 13.12.2015.
 */
public class SuperfitCourseCollection {

    private List<SuperfitCourse> courseCollection;
    private List<List<SuperfitCourse>> courseCollections;

    public List<SuperfitCourse> getCourseCollection() {
        courseCollection = new ArrayList<>();
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain", 0, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain", 0, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte", 0, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte", 0, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain", 1, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain", 1, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte", 1, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte", 1, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain", 2, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain", 2, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte", 2, true).getCourseList());
        addListToCollection(new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte", 2, true).getCourseList());

        Collections.sort(courseCollection);
        createCourseCollections();

        return courseCollection;
    }

    public List<List<SuperfitCourse>> getCourseCollections() {
        getCourseCollection();
        return courseCollections;
    }

    private void addListToCollection(List<SuperfitCourse> list) {
        int msPerHour = 60 * 60 * 1000;
        for (SuperfitCourse course : list) {
            if (course.getTime().getTime() >= new Date().getTime() - msPerHour)
                courseCollection.add(course);
        }
    }

    private void createCourseCollections() {
        List<String> courses = new ArrayList<>();
        for (SuperfitCourse course : courseCollection) {
            if (!courses.contains(course.getName()))
                courses.add(course.getName());
        }
        courseCollections = new ArrayList<>();
        for (String name : courses) {
            List<SuperfitCourse> tempList = new ArrayList<>();
            for (SuperfitCourse course : courseCollection) {
                if (course.getName().equalsIgnoreCase(name))
                    tempList.add(course);
            }
            Collections.sort(tempList);
            courseCollections.add(tempList);
        }
    }
}
