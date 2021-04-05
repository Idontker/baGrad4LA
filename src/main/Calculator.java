package main;

import util.Fach;
import util.Modul;

public class Calculator {

    /**
     * 
     * @param fach
     * @param mode_ba true if BA; false if Stex
     * @return [weighted sum, current grad, total etcs]
     */
    public static double[] calcFach(Fach fach, boolean mode_ba) {

        if (fach == null) {
            return new double[] { 0, 0, 0, 0 };
        }

        double sum = 0.0, etcs = 0.0, weightETCS = 0.0;

        for (Modul m : fach.module) {
            if (m.showIfMode(mode_ba) && Math.abs(m.note) > 0.001) {
                sum += m.note * m.etcs * m.gewicht;
                weightETCS += m.etcs * m.gewicht;
                etcs += m.etcs;
            }
        }

        double note = weightETCS != 0 ? sum / weightETCS : 0.0;
        return new double[] { sum, note, etcs };
    }
}
