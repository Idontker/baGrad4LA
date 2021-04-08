package gui.panels;

import java.util.ArrayList;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.buttons.*;
import util.Fach;
import util.Modul;
import gui.*;

public class FachPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel fachLabel, label_note, label_ECTS;
    private MyButton back;

    // private HashMap<Modul, ModulView> map_modulView;

    private Fach fach;
    private ArrayList<String> nebenfaecher;
    private boolean mode_ba;

    private GridPanel firstRow, panel_ba, panel_all;

    public FachPanel(Fach fach, ArrayList<String> fachnamen, boolean mode_ba) {
        // super(fach.getModulnamen().length + 1, 4);
        this.fach = fach;
        this.mode_ba = mode_ba;

        this.nebenfaecher = new ArrayList<String>();
        this.nebenfaecher.addAll(fachnamen);
        this.nebenfaecher.remove(fach.fachname);

        BorderLayout border = new BorderLayout();
        this.setLayout(border);

        initFirstRow();
        initModuls();

        this.add(firstRow, BorderLayout.NORTH);
        // this.add(panel_ba,BorderLayout.CENTER);
        // this.add(panel_all, BorderLayout.CENTER);

        updateMode(mode_ba);
    }

    public void updateNote(double d) {
        double val = Math.round(d * 100) / 100.0;
        String s = String.format("%.2f", val);

        label_note.setText("Note: " + s);
    }

    public void updateECTS(double d) {
        double val = Math.round(d * 10) / 10.0;
        String s = String.format("%.1f", val);

        label_ECTS.setText("ECTS: " + s);
    }

    // ================ INIT =============

    private void initFirstRow() {
        firstRow = new GridPanel(1, 4);

        fachLabel = new JLabel(fach.fachname);
        label_note = new JLabel("Note: 0.0");
        label_ECTS = new JLabel("ECTS: 0.0");

        back = new MyButton("Zurück");
        back.addActionListener(new ListenerGoToPage("Overview"));

        firstRow.place(0, 0, fachLabel);
        firstRow.place(1, 0, label_note);
        firstRow.place(2, 0, label_ECTS);
        firstRow.place(3, 0, back);
    }

    private void initModuls() {
        // calc rows for moe_ba == true and moe_ba == false
        int n_all = 0;
        int n_ba = 0;
        for (int i = 0; i < fach.module.size(); i++) {
            Modul modul = fach.module.get(i);
            if (modul.showIfNebenfachIs(nebenfaecher)) {
                n_all++;
                if (modul.showIfMode(true)) {
                    n_ba++;
                }
            }
        }

        panel_all = new GridPanel(n_all, 4);
        panel_ba = new GridPanel(n_ba, 4);

        // map_modulView = new HashMap<Modul, ModulView>();

        int idx_all = 0;
        int idx_ba = 0;

        for (int i = 0; i < fach.module.size(); i++) {
            Modul modul = fach.module.get(i);

            if (modul.showIfNebenfachIs(nebenfaecher)) {

                ModulView view = new ModulView(modul);
                // map_modulView.put(modul, view);
                view.placeOn(panel_all, idx_all);
                idx_all++;

                if (modul.showIfMode(mode_ba)) {
                    view = new ModulView(modul);
                    view.placeOn(panel_ba, idx_ba);
                    idx_ba++;
                }
            }
        }
    }

    public void updateMode(boolean mode_ba) {
        this.mode_ba = mode_ba;

        this.remove(panel_all);
        this.remove(panel_ba);

        if (mode_ba == true) {
            this.add(panel_ba, BorderLayout.CENTER);
        } else {
            this.add(panel_all, BorderLayout.CENTER);
        }
    }

    public int getRows() {
        int r = mode_ba ? panel_ba.rows : panel_all.rows;
        return 1 + r;
    }
}

class ModulView {
    JLabel label_modul;
    NotenBox nb_modul;

    public ModulView(Modul modul) {
        label_modul = new JLabel(modul.name);
        nb_modul = new NotenBox(modul);
    }

    public void placeOn(GridPanel panel, int row) {
        panel.place(1, row, this.label_modul);
        panel.place(2, row, this.nb_modul);
    }
}
