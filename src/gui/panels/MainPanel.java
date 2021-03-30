package gui.panels;

import java.util.ArrayList;
import java.util.Set;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import gui.GUI;
import gui.MyComboBox;
import gui.buttons.ListenerGoToPage;
import gui.buttons.MyButton;
import util.Fach;

public class MainPanel extends GridPanel {

    private static final long serialVersionUID = 1L;

    private JLabel label_fach1, label_fach2, label_fach3, label_ews;
    private JLabel label_note, label_etcs;
    private JLabel label_note1, label_note2, label_note3, label_etcs1, label_etcs2, label_etcs3;

    private JLabel label_warning1, label_warning2;
    private JLabel[] label_goalETCS;

    private JLabel label_total, label_total_note, label_total_etcs;

    private JButton[] change_modul;

    private JComboBox<String> comboBox[];

    public MainPanel() {
        super(10, 7);

        createLabels();
        placeLabels();

    }

    private void createLabels() {
        label_fach1 = new JLabel("Fach 1: ");
        label_fach1.setVisible(true);

        label_fach2 = new JLabel("Fach 2: ");
        label_fach2.setVisible(true);

        label_fach3 = new JLabel("Fach 3: ");
        label_fach3.setVisible(true);

        label_ews = new JLabel("EWS");
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
        label_goalETCS[3] = new JLabel("40 ETCS");

        for (int i = 0; i < label_goalETCS.length; i++) {
            label_goalETCS[1].setVisible(true);
        }

        // ===================================

        label_total = new JLabel("Gesamt:");
        label_total_note = new JLabel("0.0");
        label_total_etcs = new JLabel("0 ETCS");
        label_total.setVisible(true);
        label_total_note.setVisible(true);
        label_total_etcs.setVisible(true);

        // ===================================

        label_warning1 = new JLabel("zu viele ETCS");
        label_warning2 = new JLabel("zu viele ETCS");
        label_warning1.setForeground(Color.red);
        label_warning2.setForeground(Color.red);
    }

    private void placeLabels() {
        place(0, 1, label_fach1);
        place(0, 2, label_fach2);
        place(0, 3, label_fach3);

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

        place(5, 1, label_warning1);
        place(5, 2, label_warning2);
    }

    @Override
    public void initButtons() {
        ArrayList<String> faechernamen = new ArrayList<String>();
        for (Fach f : GUI.faecherliste) {
            if (f.fachname.equalsIgnoreCase("EWS") == false) {
                faechernamen.add(f.fachname);
            }
        }
        System.out.println(faechernamen);

        comboBox = new JComboBox[2];

        comboBox[0] = new MyComboBox(faechernamen);
        comboBox[1] = new MyComboBox(faechernamen);

        if (comboBox[0].getItemCount() >= 2) {
            comboBox[0].setSelectedIndex(0);
            comboBox[1].setSelectedIndex(1);
        }

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.update();
            }
        };
        comboBox[0].addActionListener(al);
        comboBox[1].addActionListener(al);

        place(1, 1, comboBox[0]);
        place(1, 2, comboBox[1]);

        change_modul = new JButton[3];

        for (int i = 0; i < 2; i++) {
            change_modul[i] = new MyButton("Fachübersicht");
            change_modul[i].addActionListener(new ListenerGoToPage(comboBox[i]));

            place(6, i + 1, change_modul[i]);
        }

        change_modul[2] = new MyButton("Fachübersicht");
        change_modul[2].addActionListener(new ListenerGoToPage("EWS"));
        place(5, 3, change_modul[2]);
    }

    public String getComboBoxString(int i) {
        return (String) comboBox[i].getSelectedItem();
    }

    // ============================================
    // ============================================
    // ============= Update stuff =================
    // ============================================
    // ============================================

    public void updateNote(double val, int fachnummer) {
        JLabel label;
        switch (fachnummer) {
        case 0:
            label = label_note1;
            break;
        case 1:
            label = label_note2;
            break;
        case 2:
            label = label_note3;
            break;
        default:
            label = null;
        }

        val = Math.round(val * 100) / 100.0;
        String s = String.format("%.2f", val);

        label.setText(s);
    }

    public void updateETCS(double val, int fachnummer) {
        JLabel label, warning;
        switch (fachnummer) {
        case 0:
            label = label_etcs1;
            warning = label_warning1;
            break;
        case 1:
            label = label_etcs2;
            warning = label_warning2;
            break;
        case 2:
            label = label_etcs3;
            warning = null;
            break;
        default:
            label = null;
            warning = null;
        }

        val = Math.round(val * 10) / 10.0;
        String s = String.format("%.1f", val);

        label.setText(s);

        if (warning != null) {
            if (val > 70.001) {
                warning.setVisible(true);
            } else {
                warning.setVisible(false);
            }
        }
    }

    public void updateTotal(double note, double etcs) {

        note = Math.round(note * 100) / 100.0;
        String s = String.format("%.2f", note);

        label_total_note.setText(s);

        etcs = Math.round(etcs * 10) / 10.0;
        s = String.format("%.1f", etcs);

        label_total_etcs.setText(s + " ETCS");

    }
}
