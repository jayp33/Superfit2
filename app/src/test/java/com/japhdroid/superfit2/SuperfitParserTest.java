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

    @Test
    public void testValidUrls() throws Exception {
        // http://m.mysuperfit.com/kursplaene/berlin-friedrichshain
        // http://m.mysuperfit.com/kursplaene/berlin-mitte
        // http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain
        // http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte
        // /di /mi /do /fr /sa /so

        SuperfitParser parser;

        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/di");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/mi");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/do");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/fr");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/sa");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/so");

        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/di");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/mi");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/do");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/fr");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/sa");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/so");

        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/di");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/mi");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/do");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/fr");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/sa");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/so");

        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/di");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/mi");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/do");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/fr");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/sa");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/so");
    }
}