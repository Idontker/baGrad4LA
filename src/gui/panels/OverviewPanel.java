package gui.panels;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import gui.buttons.ListenerGoToPage;
import gui.buttons.MyButton;
import util.Fach;

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

        createFachPanels();

        initButtons();

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
            // label_warning[i].setVisible(true);

            if (fach[i].fachname.equals("EWS")) {
                label_fachgoalETCS[i] = new JLabel("30 ETCS");
            } else {
                label_fachgoalETCS[i] = new JLabel("70 ETCS");
            }
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

        for (int i = 0; i < fach.length; i++) {
            place(1, i + 1, label_fachnamen[i]);
            place(2, i + 1, label_fachnote[i]);
            place(3, i + 1, label_fachetcs[i]);
            place(4, i + 1, label_fachgoalETCS[i]);
            place(5, i + 1, label_warning[i]);
        }

        place(0, 4, label_total);
        place(2, 4, label_total_note);
        place(3, 4, label_total_etcs);

        place(0, 5, label_pred);
        place(2, 5, label_pred_note);
    }

    private void createFachPanels() {
        for (int i = 0; i < fach.length; i++) {
            FachPanel fp = new FachPanel(fach[i]);
            fp.initButtons();
            ListenerGoToPage.PANEL_MAP.put(fach[i].fachname, fp);
        }
    }

    @Override
    public void initButtons() {
        change_modul = new JButton[3];

        for (int i = 0; i < 2; i++) {
            change_modul[i] = new MyButton("Fachübersicht");
            change_modul[i].addActionListener(new ListenerGoToPage(fach[i].fachname));

            place(6, i + 1, change_modul[i]);
        }
    }

}
