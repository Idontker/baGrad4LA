package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;

import gui.panels.FachPanel;
import gui.panels.MainPanel;
import main.Calculator;
import util.Fach;

public class GUI {

    public static MainFrame frame;
    public static MainPanel mainPanel;

    public static HashMap<String, FachPanel> FACH_PANEL_MAP;
    private static ArrayList<Fach> faecherliste;

    public static String DEFAULT_AUSWAHL = "bitte auswählen";

    public GUI(ArrayList<Fach> faecherliste) {
        GUI.faecherliste = faecherliste;

        frame = new MainFrame(1080, 480, 10, 6);

        MainPanel p = new MainPanel();
        GUI.mainPanel = p;

        // build Fachpanels
        FACH_PANEL_MAP = new HashMap<String, FachPanel>();
        for (Fach fach : faecherliste) {
            FachPanel f = new FachPanel(fach);
            f.initButtons();
            FACH_PANEL_MAP.put(fach.fachname, f);
        }

        p.initButtons();
        frame.showPanel(p);

        frame.setVisible(true);
    }

    public static JComboBox<String> createDropDow(String items[]) {
        String[] tmp = new String[items.length + 1];
        tmp[0] = DEFAULT_AUSWAHL;
        for (int i = 0; i < items.length; i++) {
            tmp[i + 1] = items[i];
        }

        JComboBox<String> cmb = new JComboBox<String>(tmp);
        cmb.setSelectedItem(0);
        cmb.setVisible(true);
        return cmb;
    }

    public static void update() {
        String f1 = mainPanel.getComboBoxString(0);
        String f2 = mainPanel.getComboBoxString(1);

        FachPanel fp[] = new FachPanel[3];
        fp[0] = GUI.FACH_PANEL_MAP.get(f1);
        fp[1] = GUI.FACH_PANEL_MAP.get(f2);
        fp[2] = GUI.FACH_PANEL_MAP.get("EWS");

        double res[][] = new double[3][];

        res[0] = updateFach(f1);
        res[1] = updateFach(f2);
        res[2] = updateFach("EWS");

        for (int i = 0; i < 3; i++) {
            mainPanel.updateNote(res[i][0], i);
            mainPanel.updateETCS(res[i][1], i);

            if (fp[i] != null) {
                fp[i].updateNote(res[i][0]);
                fp[i].updateETCS(res[i][1]);
            }
        }

        double total_etcs  = 0.0;
        double total_note = 0.0;

        for (int i = 0; i < 3; i++) {
            if (Double.isNaN(res[i][1]) == false) {
                total_etcs += res[i][1];
                if (Double.isNaN(res[i][0]) == false) {
                    total_note += res[i][0] * res[i][1];
                }
            }
        }

        mainPanel.updateTotal(total_note / total_etcs, total_etcs);
    }

    private static double[] updateFach(String fach) {
        if (fach.equals(DEFAULT_AUSWAHL)) {
            return new double[] { 0, 0 };
        }

        double tmp[] = Calculator.calcFach(getFachByString(fach));
        return new double[] { tmp[1], tmp[2] };
    }

    private static Fach getFachByString(String fach) {
        for (Fach f : faecherliste) {
            if (f.fachname.equals(fach)) {
                return f;
            }
        }
        return null;
    }
}