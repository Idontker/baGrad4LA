package main;

import util.Fach;
import util.Modul;

public class Calculator {
    private static final int FACH_ETCS = 70;
    private static final int EWS_ETCS = 40;

    /**
     * 
     * @param fach1
     * @param fach2
     * @param ews
     * @return [note, hochrechnung]
     */
    public static double[] calcGrad(Fach fach1, Fach fach2, Fach ews) {
        double[] addedFach1 = calcFach(fach1);
        double[] addedFach2 = calcFach(fach2);
        double[] addedEWS = calcFach(ews);

        double notensumme = addedFach1[0] + addedFach2[0] + addedEWS[0];
        double totaletcs = addedFach1[1] + addedFach2[1] + addedEWS[1];

        // double predSum = (addedFach1[0] + addedFach2[0]) * FACH_ETCS + addedEWS[0] *
        // EWS_ETCS;
        // predSum /= (FACH_ETCS * 2 + EWS_ETCS);

        double note = notensumme / totaletcs;

        // return new double[] { note, predSum };
        return new double[] { note, 0 };
    }

    /**
     * 
     * @param fach
     * @return [weighted sum, current grad, total etcs]
     */
    public static double[] calcFach(Fach fach) {
        if (fach == null) {
            return new double[] { 0, 0, 0, 0 };
        }

        double sum = 0.0, etcs = 0.0, weightETCS = 0.0;

        for (Modul m : fach.module) {
            if (Math.abs(m.note) > 0.001) {
                sum += m.note * m.etcs * m.gewicht;
                weightETCS += m.etcs * m.gewicht;
                etcs += m.etcs;
            }
        }

        return new double[] { sum, sum / weightETCS, etcs };
    }
}
