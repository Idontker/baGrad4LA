import java.util.Set;

public class Calculator {
    private final int FACH_ETCS = 70;
    private final int EWS_ETCS = 40;

    /**
     * 
     * @param fach1
     * @param fach2
     * @param ews
     * @return [note, hochrechnung]
     */
    public double[] calcGrad(Set<Modul> fach1, Set<Modul> fach2, Set<Modul> ews) {
        double[] addedFach1 = add(fach1);
        double[] addedFach2 = add(fach2);
        double[] addedEWS = add(ews);

        double notensumme = addedFach1[0] + addedFach2[0] + addedEWS[0];
        double totaletcs = addedFach1[1] + addedFach2[1] + addedEWS[1];

        double predSum = (addedFach1[0] + addedFach2[0]) * FACH_ETCS + addedEWS[0] * EWS_ETCS;
        predSum /= (FACH_ETCS * 2 + EWS_ETCS);

        double note = notensumme / totaletcs;

        return new double[] { note, predSum };
    }

    /**
     * 
     * @param moduls
     * @return [weighted sum, total etcs]
     */
    public double[] add(Set<Modul> moduls) {
        if (moduls == null) {
            return new double[] { 0, 0 };
        }

        double res[] = new double[] { 0, 0 };

        for (Modul m : moduls) {
            res[0] += m.note * m.etcs;
            res[1] += m.etcs;
        }

        return res;
    }
}
