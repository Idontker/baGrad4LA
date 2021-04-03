package gui.panels;

import java.util.ArrayList;

import javax.swing.JLabel;

import gui.buttons.*;
import util.Fach;
import util.Modul;
import gui.*;

public class FachPanel extends GridPanel {

    private static final long serialVersionUID = 1L;
    private JLabel fachLabel, label_note, label_ETCS;
    private Fach fach;
    private ArrayList<String> nebenfaecher;


    public FachPanel(Fach fach, ArrayList<String> fachnamen) {
        super(fach.getModulnamen().length + 1, 4);
        this.fach = fach;

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
        for (int i = 0; i < fach.module.size(); i++) {
            Modul modul = fach.module.get(i);

            //TODO: hier muss das Nebenfach auch wirklich übergeben werden
            // daszu Signatur von showIf ändern, sodass es eine Liste akzeptiert. 
            // und zu begin dieser init Methode eine Liste der Fächer erstellen
            // das aktuelle Fach muss nicht gelöscht werden, da es nicht schadet
            if (modul.showIfNebenfachIs(nebenfaecher)) {

                JLabel label = new JLabel(modul.name);
                label.setVisible(true);

                NotenBox cb = new NotenBox(modul);

                place(1, i + 1, label);
                place(2, i + 1, cb);
            }
        }
    }
}
