package gui.panels;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;

import gui.buttons.*;
import util.Fach;
import util.Modul;
import gui.*;

public class FachPanel extends GridPanel {

    private static final long serialVersionUID = 1L;
    private JLabel fachLabel, label_note, label_ETCS;

    private HashMap<Modul, ModulView> map_modulView;

    private Fach fach;
    private ArrayList<String> nebenfaecher;
    private boolean mode_ba;

    public FachPanel(Fach fach, ArrayList<String> fachnamen, boolean mode_ba) {
        super(fach.getModulnamen().length + 1, 4);
        this.fach = fach;
        this.mode_ba = mode_ba;

        this.nebenfaecher = new ArrayList<String>();
        this.nebenfaecher.addAll(fachnamen);
        this.nebenfaecher.remove(fach.fachname);

        initFirstRow();
        initModuls();

    }

    public void updateNote(double d) {
        double val = Math.round(d * 100) / 100.0;
        String s = String.format("%.2f", val);

        label_note.setText("Note: " + s);
    }

    public void updateETCS(double d) {
        double val = Math.round(d * 10) / 10.0;
        String s = String.format("%.1f", val);

        label_ETCS.setText("ETCS: " + s);
    }

    // ================ INIT =============

    @Override
    public void initButtons() {
        MyButton back = new MyButton("Zurück");
        back.addActionListener(new ListenerGoToPage("Overview"));
        place(3, 0, back);
    }

    private void initFirstRow() {
        fachLabel = new JLabel(fach.fachname);
        label_note = new JLabel("Note: 0.0");
        label_ETCS = new JLabel("ETCS: 0.0");

        fachLabel.setVisible(true);
        label_note.setVisible(true);
        label_ETCS.setVisible(true);

        place(0, 0, fachLabel);
        place(1, 0, label_note);
        place(2, 0, label_ETCS);
    }

    private void initModuls() {
        map_modulView = new HashMap<Modul, ModulView>();

        for (int i = 0; i < fach.module.size(); i++) {
            Modul modul = fach.module.get(i);

            if (modul.showIfNebenfachIs(nebenfaecher)) {

                boolean visible = modul.showIfMode(mode_ba);
                ModulView view = new ModulView(modul, visible);

                map_modulView.put(modul, view);

                place(1, i + 1, view.label_modul);
                place(2, i + 1, view.nb_modul);
            }
        }
    }

    public void updateMode(boolean mode_ba) {
        this.mode_ba = mode_ba;

        for (int i = 0; i < fach.module.size(); i++) {
            for (Modul modul : fach.module) {
                ModulView view = map_modulView.get(modul);
                // System.out.println(mode_ba + "\t" + modul.showIfMode(mode_ba) + "\t" + modul.name);
                if (view != null) {
                    view.setVisible(modul.showIfMode(mode_ba));
                }
            }
        }
    }
}

class ModulView {
    JLabel label_modul;
    NotenBox nb_modul;

    public ModulView(Modul modul, boolean visible) {
        label_modul = new JLabel(modul.name);
        nb_modul = new NotenBox(modul);

        setVisible(visible);
    }

    void setVisible(boolean visible) {
        label_modul.setVisible(visible);
        nb_modul.setVisible(visible);
    }
}
