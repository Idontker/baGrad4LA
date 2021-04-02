package gui.panels;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import gui.buttons.ListenerGoToPage;
import gui.buttons.MyButton;
import util.Fach;

public class OverviewPanel extends GridPanel {

    private static final long serialVersionUID = 1L;

    private JLabel label_fach1, label_fach2, label_ews;
    private JLabel label_note, label_etcs;
    private JLabel label_note1, label_note2, label_note3, label_etcs1, label_etcs2, label_etcs3;

    private JLabel label_warning1, label_warning2;
    private JLabel[] label_goalETCS;

    private JLabel label_total, label_total_note, label_total_etcs;
    private JLabel label_pred, label_pred_note;

    private Fach fach[];

    private JButton[] change_modul;

    public OverviewPanel(Fach fach1, Fach fach2, Fach ews) {
        super(10, 7);

        fach = new Fach[3];
        fach[0] = fach1;
        fach[1] = fach2;
        fach[2] = ews;

        createLabels();
        placeLabels();
    }

    private void createLabels() {
        label_fach1 = new JLabel(fach[0].fachname);
        label_fach1.setVisible(true);

        label_fach2 = new JLabel(fach[1].fachname);
        label_fach2.setVisible(true);

        label_ews = new JLabel(fach[2].fachname);
        label_ews.setVisible(true);
        // ===================================

        label_note = new JLabel("Teilnote");
        label_note.setVisible(true);

        label_note1 = new JLabel("Note 1");
        label_note1.setVisible(true);
        label_note2 = new JLabel("Note 2");
        label_note2.setVisible(true);
        label_note3 = new JLabel("Note 3");
        label_note3.setVisible(true);

        // ===================================

        label_etcs = new JLabel("ETCS bislang");
        label_etcs.setVisible(true);

        label_etcs1 = new JLabel("0 ECTS");
        label_etcs2 = new JLabel("0 ECTS");
        label_etcs3 = new JLabel("0 ECTS");
        label_etcs1.setVisible(true);
        label_etcs2.setVisible(true);
        label_etcs3.setVisible(true);

        // ===================================

        label_goalETCS = new JLabel[4];
        label_goalETCS[0] = new JLabel("ETCS benötigt");
        label_goalETCS[1] = new JLabel("70 ETCS");
        label_goalETCS[2] = new JLabel("70 ETCS");
        label_goalETCS[3] = new JLabel("41 ETCS");

        for (int i = 0; i < label_goalETCS.length; i++) {
            label_goalETCS[1].setVisible(true);
        }

        // ===================================

        label_warning1 = new JLabel("zu viele ETCS");
        label_warning2 = new JLabel("zu viele ETCS");
        label_warning1.setForeground(Color.red);
        label_warning2.setForeground(Color.red);

        // ===================================

        label_total = new JLabel("Gesamt:");
        label_total_note = new JLabel("0.0");
        label_total_etcs = new JLabel("0 ETCS");
        label_total.setVisible(true);
        label_total_note.setVisible(true);
        label_total_etcs.setVisible(true);

        // ===================================

        label_pred = new JLabel("Hochrechnung:");
        label_pred_note = new JLabel();
        label_pred.setVisible(true);
        label_pred_note.setVisible(true);

        // ===================================
    }

    private void placeLabels() {
        place(1, 1, label_fach1);
        place(1, 2, label_fach2);

        place(1, 3, label_ews);

        place(2, 0, label_note);
        place(2, 1, label_note1);
        place(2, 2, label_note2);
        place(2, 3, label_note3);

        place(3, 0, label_etcs);
        place(3, 1, label_etcs1);
        place(3, 2, label_etcs2);
        place(3, 3, label_etcs3);

        for (int i = 0; i < label_goalETCS.length; i++) {
            place(4, i, label_goalETCS[i]);
        }

        place(0, 4, label_total);
        place(2, 4, label_total_note);
        place(3, 4, label_total_etcs);

        place(0, 5, label_pred);
        place(2, 5, label_pred_note);

        place(5, 1, label_warning1);
        place(5, 2, label_warning2);
    }

    @Override
    public void initButtons() {
        change_modul = new JButton[3];

        for (int i = 0; i < 2; i++) {
            change_modul[i] = new MyButton("Fachübersicht");
            change_modul[0].addActionListener(new ListenerGoToPage(fach[0].fachname));

            place(6, i + 1, change_modul[i]);
        }
        change_modul[1].addActionListener(new ListenerGoToPage(fach[1].fachname));

        change_modul[2] = new MyButton("Fachübersicht");
        change_modul[2].addActionListener(new ListenerGoToPage(""));
        place(6, 3, change_modul[2]);

    }

}
