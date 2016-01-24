package com.japhdroid.superfit2;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 24.01.2016.
 */
public class StudiosTest {

    @Test
    public void testStudioCount() throws Exception {
        List<Studio> studios = new Studios(TestDataProvider.studios).getStudios();
        assertEquals(8, studios.size());
    }

    @Test
    public void testStudioId_5_IsCorrect() throws Exception {
        Studio studio = new Studios(TestDataProvider.studios).getStudioById(5);
        assertEquals("SUPERFIT Mitte", studio.getTitle());
    }
}