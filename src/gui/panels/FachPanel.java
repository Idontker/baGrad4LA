package gui.panels;

import java.util.ArrayList;

import java.awt.*;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.buttons.*;
import util.Fach;
import util.Modul;
import gui.*;

public class FachPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int cols = 4;

    private JLabel fachLabel, label_note, label_ECTS, label_using;
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
        firstRow = new GridPanel(1, cols);

        fachLabel = new JLabel(fach.fachname);
        label_note = new JLabel("Note: 0.0");
        label_ECTS = new JLabel("ECTS: 0.0");
        label_using = new JLabel("Einbringen?");

        back = new MyButton("Zurück");
        back.addActionListener(new ListenerGoToPage("Overview"));

        firstRow.place(0, 0, fachLabel);
        firstRow.place(1, 0, label_note);

        JPanel cell = firstRow.panels[0][2];
        cell.setLayout(new BorderLayout());
        cell.add(label_using, BorderLayout.EAST);
        cell.add(label_ECTS, BorderLayout.WEST);

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

        panel_all = new GridPanel(n_all, cols);
        panel_ba = new GridPanel(n_ba, cols);

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
    JLabel label_ects;
    NotenBox nb_modul;
    JComponent using;

    public ModulView(Modul modul) {
        label_modul = new JLabel(modul.name);
        label_ects = new JLabel(modul.ects + " ECTS");
        nb_modul = new NotenBox(modul);

        if (modul.pflicht) {
            using = new JLabel("Pflicht");
        } else {
            using = new JCheckBox("");
        }
    }

    public void placeOn(GridPanel panel, int row) {
        panel.place(0, row, this.label_modul);
        panel.place(1, row, this.nb_modul);

        JPanel cell = panel.panels[row][2];
        cell.setLayout(new BorderLayout());

        cell.add(using, BorderLayout.EAST);
        cell.add(label_ects, BorderLayout.WEST);

        // panel.place(2, row, cell);
    }
}
