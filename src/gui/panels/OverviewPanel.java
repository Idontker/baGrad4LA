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

    private JLabel label_note, label_etcs, label_goalETCS;

    private JLabel label_fachnamen[];
    private JLabel label_fachnote[];
    private JLabel label_fachetcs[];
    private JLabel label_fachgoalETCS[];
    private JLabel label_warning[];

    private JLabel label_total, label_total_note, label_total_etcs;
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
        label_etcs = new JLabel("ETCS bislang");
        label_goalETCS = new JLabel("ETCS benötigt");

        label_fachnamen = new JLabel[fach.length];
        label_fachnote = new JLabel[fach.length];
        label_fachetcs = new JLabel[fach.length];
        label_fachgoalETCS = new JLabel[fach.length];
        label_warning = new JLabel[fach.length];

        for (int i = 0; i < fach.length; i++) {
            label_fachnamen[i] = new JLabel(fach[i].fachname);
            label_fachnote[i] = new JLabel("Note " + i);
            label_fachetcs[i] = new JLabel("ETCS " + i);
            label_warning[i] = new JLabel("zu viele ETCS");
            label_warning[i].setForeground(Color.red);
            label_warning[i].setVisible(false);

            label_fachgoalETCS[i] = new JLabel(fach[i].etcs_ba + " ETCS");
        }

        label_total = new JLabel("Gesamt:");
        label_total_note = new JLabel("0.0");
        label_total_etcs = new JLabel("0 ETCS");

        // ===================================

        label_pred = new JLabel("Hochrechnung:");
        label_pred_note = new JLabel();
        label_pred.setVisible(true);
        label_pred_note.setVisible(true);

        // ===================================
    }

    private void placeLabels() {

        place(2, 0, label_note);
        place(3, 0, label_etcs);
        place(4, 0, label_goalETCS);

        int i = 0;
        for (; i < fach.length; i++) {
            place(1, i + 1, label_fachnamen[i]);
            place(2, i + 1, label_fachnote[i]);
            place(3, i + 1, label_fachetcs[i]);
            place(4, i + 1, label_fachgoalETCS[i]);
            place(5, i + 1, label_warning[i]);
        }
        i++;

        place(0, i, label_total);
        place(2, i, label_total_note);
        place(3, i, label_total_etcs);
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
            fachPanel[i].initButtons();
            ListenerGoToPage.PANEL_MAP.put(fach[i].fachname, fachPanel[i]);
        }
    }

    @Override
    public void initButtons() {
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
        for (int i = 0; i < label_fachgoalETCS.length; i++) {
            if (mode_ba) {
                label_fachgoalETCS[i].setText(fach[i].etcs_ba + " ETCS");
            } else {
                label_fachgoalETCS[i].setText(fach[i].etcs_stex + " ETCS");
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
            this.updateETCS(res[i][1], i);

            fachPanel[i].updateETCS(res[i][1]);
            fachPanel[i].updateNote(res[i][0]);
        }

        // ==============================
        // update totals
        // ==============================

        double total_etcs = 0.0;
        double total_note = 0.0;

        for (int i = 0; i < fach.length; i++) {
            total_etcs += res[i][1];
            total_note += res[i][0] * res[i][1];
        }
        total_note = Math.abs(total_etcs) > 0.0001 ? total_note / total_etcs : 0.0;

        this.updateTotal(total_note, total_etcs);

        // ==============================
        // update pred
        // ==============================

        double pred = 0.0;
        pred += Math.abs(res[0][0]) > 0.0001 ? res[0][0] * 70 : total_note * 70;
        pred += Math.abs(res[1][0]) > 0.0001 ? res[1][0] * 70 : total_note * 70;
        pred += Math.abs(res[2][0]) > 0.0001 ? res[2][0] * 41 : total_note * 41;
        pred /= 70 + 70 + 41;
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

    public void updateETCS(double val, int fachnummer) {
        label_fachetcs[fachnummer].setText(formatETCS(val));

        if (label_warning[fachnummer] != null) {
            if (val > 70.001) {
                label_warning[fachnummer].setVisible(true);
            } else {
                label_warning[fachnummer].setVisible(false);
            }
        }
    }

    public void updateTotal(double note, double etcs) {
        String s = formatGrad(note);
        label_total_note.setText(s);

        s = formatETCS(etcs);

        label_total_etcs.setText(s + " ETCS");
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

    private String formatETCS(double d) {
        return roundedDoubleAsString(1, d);
    }

    private String roundedDoubleAsString(int digits, double d) {
        double expo = Math.pow(10, digits);
        d = Math.round(d * expo) / expo;
        return String.format("%." + digits + "f", d);
    }
}
