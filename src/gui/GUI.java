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

    // public static String[] FACHERLISTE; // = new String[] { "None", "Mathematik",
    // "Informatik", "Wirtschaft", "Englisch" };
    public static HashMap<String, FachPanel> FACH_PANEL_MAP;
    private static ArrayList<Fach> faecherliste;

    private static String DEFAULT_AUSWAHL = "bitte auswählen";

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
        // FachPanel f1 = GUI.FACH_PANEL_MAP.get(mainPanel.getComboBoxString(0));
        // FachPanel f2 = GUI.FACH_PANEL_MAP.get(mainPanel.getComboBoxString(1));
        String f1 = mainPanel.getComboBoxString(0);
        String f2 = mainPanel.getComboBoxString(1);

        double[] r1 = updateFach(f1);
        double[] r2 = updateFach(f2);
        double[] r3 = updateFach("EWS");
        mainPanel.updateNote(r1[0], 0);
        mainPanel.updateNote(r2[0], 1);
        mainPanel.updateNote(r3[0], 2);

        mainPanel.updateETCS(r1[1], 0);
        mainPanel.updateETCS(r2[1], 1);
        mainPanel.updateETCS(r3[1], 2);
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