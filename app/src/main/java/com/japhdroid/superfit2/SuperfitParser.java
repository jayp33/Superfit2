package com.japhdroid.superfit2;

/**
 * Created by User on 04.12.2015.
 */
public class SuperfitParser {

    private String url;

    public SuperfitParser(String url) {
        this.url = url;
        if (!CheckUrlIsValid())
            throw new IllegalArgumentException("The supplied URL is not valid.");
    }

    private boolean CheckUrlIsValid() {
        // http://m.mysuperfit.com/kursplaene/berlin-friedrichshain
        // http://m.mysuperfit.com/kursplaene/berlin-mitte
        // http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain
        // http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte
        // /di /mi /do /fr /sa /so

        String[] basicUrls = {"http://m.mysuperfit.com/kursplaene/",
                "http://m.mysuperfit.com/teamtrainingsplaene/"};
        String[] locationUrlPart = {"berlin-friedrichshain",
                "berlin-mitte"};
        String[] dateUrlPart = {"/di", "/mi", "/do", "/fr", "/sa", "/so"};

        for (String basic : basicUrls) {
            if (url.startsWith(basic))
                for (String location : locationUrlPart) {
                    if (url.equalsIgnoreCase(basic + location)) {
                        return true;
                    }
                    for (String date : dateUrlPart) {
                        if (url.equalsIgnoreCase(basic + location + date)) {
                            return true;
                        }
                    }
                }
        }

        return false;
    }
}
