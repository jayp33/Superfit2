package com.japhdroid.superfit2;

import java.util.ArrayList;

/**
 * Created by User on 05.12.2015.
 */
public class SuperFitCourseMapping {

    static final String[] type = {"Kurs", "Kurs", "Kurs", "Kurs", "Kurs", "Kurs", "Kurs", "Kurs",
            "Kurs", "Kurs", "Kurs", "Kurs", "Kurs", "Kurs", "Kurs", "Kurs",
            "Team", "Team", "Team", "Team", "Team", "Team", "Team", "Team", "Team", "Team"};

    // ruecken.jpg appears twice
    static final String[] filename = {"bbp.jpg", "pilates.jpg", "bodyvive.jpg", "bodypumpxp.jpg",
            "bodyattack.jpg", "bodystep.jpg", "bodypump.jpg", "bodybalance.jpg", "shbam.jpg",
            "bauchxp.jpg", "zumba.jpg", "gritplyo.jpg", "bodycombat.jpg", "yoga.jpg", "lmistep.jpg",
            "ruecken.jpg", "trx.jpg", "circuit.jpg", "tsm.jpg", "bauch.jpg", "cardio.jpg",
            "trxbauch.jpg", "stretch.jpg", "ruecken.jpg", "po.jpg", "fullbodyworkout.jpg"};

    static final String[] name = {"Bauch Beine Po (SuperFit)", "Pilates (SuperFit)", "BodyVive (LesMills)",
            "BodyPump Express (LesMills)", "BodyAttack (LesMills)", "BodyStep (LesMills)",
            "BodyPump (LesMills)", "BodyBalance (LesMills)", "Sh'Bam (LesMills)", "Bauch Express (SuperFit)",
            "Zumba Fitness", "Grit Plyo (LesMills)", "BodyCombat (LesMills)", "Yoga (SuperFit)",
            "LmiStep (LesMills)", "Rücken (SuperFit)", "TRX", "Circuit (SuperFit)",
            "Trainingsstart (SuperFit)", "Bauch (SuperFit)", "Cardio (SuperFit)", "TRX Bauch",
            "Stretch (SuperFit)", "Rücken (SuperFit)", "Po (SuperFit)", "Full Body Workout (SuperFit)"};

    public static String getName(String _type, String _filename) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < filename.length; i++) {
            if (filename[i].equalsIgnoreCase(_filename))
                indices.add(i);
        }
        if (indices.size() > 1)
            for (Integer i : indices) {
                if (_type.equalsIgnoreCase(type[i]))
                    return name[i];
            }
        if (indices.size() == 1)
            return name[indices.get(0)];

        return _type + " " + _filename;
    }
}
