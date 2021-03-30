package gui.panels;

import javax.swing.JLabel;

import gui.buttons.*;
import util.Fach;
import gui.*;

public class FachPanel extends GridPanel {

    private static final long serialVersionUID = 1L;
    private final String[] noten = new String[] { "1.0", "1.3", "1.7", "2.0", "2.3", "2.7", "3.0", "3.3", "3.7",
            "4.0" };

    private JLabel fachLabel, label_note, label_ETCS;
    private Fach fach;

    public FachPanel(Fach fach) {
        super(fach.getModulnamen().length + 1, 4);
        this.fach = fach;

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
        back.addActionListener(new ListenerGoToPage(GUI.mainPanel, GUI.frame));
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

            JLabel label = new JLabel(fach.module.get(i).name);
            label.setVisible(true);

            NotenBox cb = new NotenBox(fach.module.get(i));

            place(1, i + 1, label);
            place(2, i + 1, cb);
        }
    }
}
