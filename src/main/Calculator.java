package main;

import util.Fach;
import util.Modul;

public class Calculator {

    /**
     * 
     * @param fach
     * @param mode_ba true if BA; false if Stex
     * @return [weighted sum, current grad, total ects]
     */
    public static double[] calcFach(Fach fach, boolean mode_ba) {

        if (fach == null) {
            return new double[] { 0, 0, 0, 0 };
        }

        double sum = 0.0, ects = 0.0, weightECTS = 0.0;

        for (Modul m : fach.module) {
            if (m.showIfMode(mode_ba) && m.isUsed(mode_ba) && Math.abs(m.note) > 0.001) {
                sum += m.note * m.ects * m.gewicht;
                weightECTS += m.ects * m.gewicht;
                ects += m.ects;
            }
        }

        double note = weightECTS != 0 ? sum / weightECTS : 0.0;
        return new double[] { sum, note, ects };
    }
}
