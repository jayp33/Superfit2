package com.japhdroid.superfit2;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 19.03.2016.
 */
public class DataProviderTest {

    @Test
    public void testLoadData() throws Exception {
        // this test requires a network connection
        Map urls = new HashMap<DataProvider.DataType, String[]>();
        urls.put(DataProvider.DataType.STUDIOS, new String[]{"http://superfit.navillo.de/api/v3/studios.json"});
        urls.put(DataProvider.DataType.COURSES, new String[]{"http://superfit.navillo.de/api/v3/courses.json"});
        urls.put(DataProvider.DataType.LESSONS, new String[]{"http://superfit.navillo.de/api/v3/lessons.json?studio_id=3",
                "http://superfit.navillo.de/api/v3/lessons.json?studio_id=5"});
        DataProvider.LoadData(urls);
    }
}