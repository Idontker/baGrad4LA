package gui;

import java.util.HashMap;

import gui.buttons.ListenerGoToPage;
import gui.panels.FachPanel;
import gui.panels.GridPanel;
import gui.panels.MainPanel;
import gui.panels.StartPanel;
import main.Calculator;
import util.Fach;
import util.Saver;

public class GUI {

    public static GUI gui;

    public MainFrame frame;
    public HashMap<String, Fach> faechermap;

    private HashMap<String, HashMap<String, Fach>> map;

    private HashMap<String, GridPanel> panels;

    public GUI(HashMap<String, HashMap<String, Fach>> map) {
        GUI.gui = this;

        // this.faechermap = faechermap;
        this.map = map;

        panels = ListenerGoToPage.PANEL_MAP;
        // frame = new MainFrame(1080, 480);
        frame = new MainFrame(480, 460);

        StartPanel startPanel = new StartPanel(map);
        frame.showPanel(startPanel);

        /*
         * MainPanel mainPanel = new MainPanel(); panels.put("Main", mainPanel);
         * 
         * // build Fachpanels for (Fach fach : faecherliste.values()) { FachPanel f =
         * new FachPanel(fach); f.initButtons(); panels.put(fach.fachname, f); }
         * 
         * mainPanel.initButtons(); frame.showPanel(mainPanel);
         */

        frame.setVisible(true);
    }

    // ==========================================================
    // ==========================================================
    // ==========================================================

    public void update() {

        // ==============================
        // get all Panels
        // ==============================
        MainPanel mainPanel = (MainPanel) panels.get("Main");

        String f1 = mainPanel.getComboBoxString(0);
        String f2 = mainPanel.getComboBoxString(1);

        FachPanel fp[] = new FachPanel[3];
        fp[0] = (FachPanel) panels.get(f1);
        fp[1] = (FachPanel) panels.get(f2);
        fp[2] = (FachPanel) panels.get("EWS");

        // ==============================
        // now pull and update data
        // ==============================

        double res[][] = new double[3][];

        res[0] = updateFach(f1);
        res[1] = updateFach(f2);
        res[2] = updateFach("EWS");

        // ==============================
        // update Panels with new data
        // ==============================

        for (int i = 0; i < 3; i++) {
            mainPanel.updateNote(res[i][0], i);
            mainPanel.updateETCS(res[i][1], i);

            if (fp[i] != null) {
                fp[i].updateNote(res[i][0]);
                fp[i].updateETCS(res[i][1]);
            }
        }

        // ==============================
        // update totals
        // ==============================

        double total_etcs = 0.0;
        double total_note = 0.0;

        for (int i = 0; i < 3; i++) {
            total_etcs += res[i][1];
            total_note += res[i][0] * res[i][1];
        }
        total_note = Math.abs(total_etcs) > 0.0001 ? total_note / total_etcs : 0.0;

        mainPanel.updateTotal(total_note, total_etcs);

        // ==============================
        // update pred
        // ==============================

        double pred = 0.0;
        pred += Math.abs(res[0][0]) > 0.0001 ? res[0][0] * 70 : total_note * 70;
        pred += Math.abs(res[1][0]) > 0.0001 ? res[1][0] * 70 : total_note * 70;
        pred += Math.abs(res[2][0]) > 0.0001 ? res[2][0] * 41 : total_note * 41;
        pred /= 70 + 70 + 41;
        mainPanel.updatePred(pred);

        // ==============================
        // Save updates
        // ==============================

        Saver.save(faechermap);
    }

    private double[] updateFach(String fachname) {
        Fach fach = faechermap.get(fachname);
        if (fach == null) {
            return new double[] { 0, 0 };
        }

        double tmp[] = Calculator.calcFach(fach);
        return new double[] { tmp[1], tmp[2] };
    }
}