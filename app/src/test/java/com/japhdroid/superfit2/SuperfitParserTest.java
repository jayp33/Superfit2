package com.japhdroid.superfit2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by User on 04.12.2015.
 */
public class SuperfitParserTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testInvalidUrlThrowsException() throws Exception {
        exception.expect(IllegalArgumentException.class);
        SuperfitParser parser = new SuperfitParser("garbage");
    }
}