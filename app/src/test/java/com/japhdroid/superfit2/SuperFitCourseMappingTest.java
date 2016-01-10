package com.japhdroid.superfit2;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by User on 07.01.2016.
 */
public class SuperFitCourseMappingTest {

    @Test
    public void testMappingMemberCount() throws Exception {
        int count = SuperFitCourseMapping.type.length;
        Assert.assertEquals(count, SuperFitCourseMapping.filename.length);
        Assert.assertEquals(count, SuperFitCourseMapping.name.length);
    }

    @Test
    public void testInvalidMappingReturnsDefaultString() throws Exception {
        Assert.assertEquals("Kurs garbage.jpg", SuperFitCourseMapping.getName("Kurs", "garbage.jpg"));
    }

    @Test
    public void testCourseMappingNames() throws Exception {
        Assert.assertEquals("SuperFit Bauch Beine Po", SuperFitCourseMapping.getName("Kurs", "bbp.jpg"));
        Assert.assertEquals("LesMills BodyVive", SuperFitCourseMapping.getName("Kurs", "bodyvive.jpg"));
        Assert.assertEquals("SuperFit Rücken", SuperFitCourseMapping.getName("Kurs", "ruecken.jpg"));
        Assert.assertEquals("SuperFit Rücken", SuperFitCourseMapping.getName("Team", "ruecken.jpg"));
        Assert.assertEquals("SuperFit Full Body Workout", SuperFitCourseMapping.getName("Team", "fullbodyworkout.jpg"));
    }
}