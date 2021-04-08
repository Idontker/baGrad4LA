package gui.panels;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import gui.buttons.ListenerGoToPage;
import gui.buttons.MyButton;
import util.Fach;
import util.Saver;
import main.Calculator;

public class OverviewPanel extends GridPanel {

    private static final long serialVersionUID = 1L;

    private JLabel label_note, label_ects, label_goalECTS;

    private JLabel label_fachnamen[];
    private JLabel label_fachnote[];
    private JLabel label_fachects[];
    private JLabel label_fachgoalECTS[];
    private JLabel label_warning[];

    private JLabel label_total, label_total_note, label_total_ects;
    private JLabel label_pred, label_pred_note;

    private JRadioButton radio_stex, radio_ba;

    private JButton[] change_modul;

    private FachPanel fachPanel[];
    private Fach fach[];

    private boolean mode_ba;

    public OverviewPanel(Fach[] fach) {
        super(10, 7);

        this.fach = fach;

        mode_ba = true;

        createLabels();
        placeLabels();
        createFachPanels();
        initButtons();

        update();

    }

    private void createLabels() {
        label_note = new JLabel("Teilnote");
        label_ects = new JLabel("ECTS bislang");
        label_goalECTS = new JLabel("ECTS benötigt");

        label_fachnamen = new JLabel[fach.length];
        label_fachnote = new JLabel[fach.length];
        label_fachects = new JLabel[fach.length];
        label_fachgoalECTS = new JLabel[fach.length];
        label_warning = new JLabel[fach.length];

        for (int i = 0; i < fach.length; i++) {
            label_fachnamen[i] = new JLabel(fach[i].fachname);
            label_fachnote[i] = new JLabel("Note " + i);
            label_fachects[i] = new JLabel("ECTS " + i);
            label_warning[i] = new JLabel("zu viele ECTS");
            label_warning[i].setForeground(Color.red);
            label_warning[i].setVisible(false);

            label_fachgoalECTS[i] = new JLabel(fach[i].ects_ba + " ECTS");
        }

        label_total = new JLabel("Gesamt:");
        label_total_note = new JLabel("0.0");
        label_total_ects = new JLabel("0 ECTS");

        // ===================================

        label_pred = new JLabel("Hochrechnung:");
        label_pred_note = new JLabel();
        label_pred.setVisible(true);
        label_pred_note.setVisible(true);

        // ===================================
    }

    private void placeLabels() {

        place(2, 0, label_note);
        place(3, 0, label_ects);
        place(4, 0, label_goalECTS);

        int i = 0;
        for (; i < fach.length; i++) {
            place(1, i + 1, label_fachnamen[i]);
            place(2, i + 1, label_fachnote[i]);
            place(3, i + 1, label_fachects[i]);
            place(4, i + 1, label_fachgoalECTS[i]);
            place(5, i + 1, label_warning[i]);
        }
        i++;

        place(0, i, label_total);
        place(2, i, label_total_note);
        place(3, i, label_total_ects);
        i++;

        place(0, i, label_pred);
        place(2, i, label_pred_note);
    }

    private void createFachPanels() {
        fachPanel = new FachPanel[fach.length];

        ArrayList<String> fachnamen = new ArrayList<String>();
        for (Fach f : fach) {
            fachnamen.add(f.fachname);
        }

        for (int i = 0; i < fach.length; i++) {
            fachPanel[i] = new FachPanel(fach[i], fachnamen, mode_ba);
            ListenerGoToPage.PANEL_MAP.put(fach[i].fachname, fachPanel[i]);
        }
    }

    private void initButtons() {
        change_modul = new JButton[fach.length];

        for (int i = 0; i < change_modul.length; i++) {
            change_modul[i] = new MyButton("Fachübersicht");
            change_modul[i].addActionListener(new ListenerGoToPage(fach[i].fachname));

            place(6, i + 1, change_modul[i]);
        }

        initToggleButton();
    }

    private void initToggleButton() {
        radio_ba = new JRadioButton("BA-Rechner");
        radio_stex = new JRadioButton("Stex-Rechner");
        place(0, 0, radio_ba);
        place(1, 0, radio_stex);

        radio_ba.setSelected(mode_ba);
        radio_stex.setSelected(!mode_ba);

        radio_ba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radio_ba.isSelected() == false) {
                    radio_ba.setSelected(true);
                } else {
                    radio_stex.setSelected(false);
                    mode_ba = true;
                    updateMode();
                }
            }
        });

        radio_stex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radio_stex.isSelected() == false) {
                    radio_stex.setSelected(true);
                } else {
                    radio_ba.setSelected(false);
                    mode_ba = false;
                    updateMode();
                }
            }
        });
    }

    // ============================================
    // ============================================
    // ============= Update stuff =================
    // ============================================
    // ============================================

    private void updateMode() {
        //
        for (int i = 0; i < label_fachgoalECTS.length; i++) {
            if (mode_ba) {
                label_fachgoalECTS[i].setText(fach[i].ects_ba + " ECTS");
            } else {
                label_fachgoalECTS[i].setText(fach[i].ects_stex + " ECTS");
            }
        }

        for (int i = 0; i < fachPanel.length; i++) {
            fachPanel[i].updateMode(mode_ba);
        }

        update();
    }

    public void update() {

        // ==============================
        // pull and update data from fachPanels
        // ==============================

        double res[][] = new double[fach.length][];

        for (int i = 0; i < fach.length; i++) {
            res[i] = updateFach(fach[i]);
        }

        // ==============================
        // update Panels with new data
        // ==============================

        for (int i = 0; i < fach.length; i++) {
            this.updateNote(res[i][0], i);
            this.updateECTS(res[i][1], i);

            fachPanel[i].updateECTS(res[i][1]);
            fachPanel[i].updateNote(res[i][0]);
        }

        // ==============================
        // update totals
        // ==============================

        double total_ects = 0.0;
        double total_note = 0.0;

        for (int i = 0; i < fach.length; i++) {
            total_ects += res[i][1];
            total_note += res[i][0] * res[i][1];
        }
        total_note = Math.abs(total_ects) > 0.0001 ? total_note / total_ects : 0.0;

        this.updateTotal(total_note, total_ects);

        // ==============================
        // update pred
        // ==============================

        double pred = 0.0;
        double pred_ects = 0.0;
        for (int i = 0; i < fach.length; i++) {
            double ects = mode_ba ? fach[i].ects_ba : fach[i].ects_stex;
            pred += Math.abs(res[i][0]) > 0.0001 ? res[i][0] * ects : total_note * ects;
            pred_ects += ects;
        }
        pred /= pred_ects;
        this.updatePred(pred);

        // ==============================
        // Save updates
        // ==============================

        Saver.save();
    }

    private double[] updateFach(Fach fach) {
        double tmp[] = Calculator.calcFach(fach, mode_ba);
        return new double[] { tmp[1], tmp[2] };
    }

    public void updateNote(double val, int fachnummer) {
        label_fachnote[fachnummer].setText(formatGrad(val));
    }

    public void updateECTS(double val, int fachnummer) {
        label_fachects[fachnummer].setText(formatECTS(val));

        if (label_warning[fachnummer] != null) {
            double threshold = mode_ba ? fach[fachnummer].ects_ba : fach[fachnummer].ects_stex;
            if (val > threshold + 0.0001) {
                label_warning[fachnummer].setVisible(true);
            } else {
                label_warning[fachnummer].setVisible(false);
            }
        }
    }

    public void updateTotal(double note, double ects) {
        String s = formatGrad(note);
        label_total_note.setText(s);

        s = formatECTS(ects);

        label_total_ects.setText(s + " ECTS");
    }

    public void updatePred(double pred) {
        String s = formatGrad(pred);
        label_pred_note.setText(s);
    }

    // ============================================
    // ============================================
    // ============= Format Stuff =================
    // ============================================
    // ============================================

    private String formatGrad(double d) {
        if (d == 0.0 || Double.isNaN(d)) {
            return "???";
        }
        return roundedDoubleAsString(2, d);
    }

    private String formatECTS(double d) {
        return roundedDoubleAsString(1, d);
    }

    private String roundedDoubleAsString(int digits, double d) {
        double expo = Math.pow(10, digits);
        d = Math.round(d * expo) / expo;
        return String.format("%." + digits + "f", d);
    }
}
