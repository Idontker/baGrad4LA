package gui;

import java.util.ArrayList;
import java.util.HashMap;

import gui.buttons.ListenerGoToPage;
import gui.panels.FachPanel;
import gui.panels.GridPanel;
import gui.panels.MainPanel;
import main.Calculator;
import util.Fach;

public class GUI {

    public static MainFrame frame;
    public static ArrayList<Fach> faecherliste;

    private static HashMap<String, GridPanel> panels;

    public GUI(ArrayList<Fach> faecherliste) {
        GUI.faecherliste = faecherliste;
        GUI.panels = ListenerGoToPage.PANEL_MAP;

        frame = new MainFrame(1080, 480, 10, 6);

        MainPanel mainPanel = new MainPanel();
        panels.put("Main", mainPanel);

        // build Fachpanels
        for (Fach fach : faecherliste) {
            FachPanel f = new FachPanel(fach);
            f.initButtons();
            panels.put(fach.fachname, f);

        }

        mainPanel.initButtons();
        frame.showPanel(mainPanel);

        frame.setVisible(true);
    }

    // ==========================================================
    // ==========================================================
    // ==========================================================

    public static void update() {
        MainPanel mainPanel = (MainPanel) panels.get("Main");

        String f1 = mainPanel.getComboBoxString(0);
        String f2 = mainPanel.getComboBoxString(1);

        FachPanel fp[] = new FachPanel[3];
        fp[0] = (FachPanel) panels.get(f1);
        fp[1] = (FachPanel) panels.get(f2);
        fp[2] = (FachPanel) panels.get("EWS");

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

        double total_etcs = 0.0;
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

    // TODO: aufteilen in Parameter mit Fach und mit String - oben dann austauschen,
    // da man oben bereits ein Fachpanel sucht
    private static double[] updateFach(String fachname) {
        Fach fach = getFachByString(fachname);
        if (fach == null) {
            return new double[] { 0, 0 };
        }

        double tmp[] = Calculator.calcFach(fach);
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