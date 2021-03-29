package gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import gui.buttons.*;
import util.Fach;
import gui.*;

public class FachPanel extends GridPanel {

    private static final long serialVersionUID = 1L;
    private final String[] noten = new String[] { "1.0", "1.3", "1.7", "2.0", "2.3", "2.7", "3.0", "3.3", "3.7",
            "4.0" };

    public FachPanel(Fach fach) {
        super(fach.getModulnamen().length + 1, 4);
        JLabel fachLabel = new JLabel(fach.fachname);
        fachLabel.setVisible(true);
        place(0, 0, fachLabel);

        for (int i = 0; i < fach.module.size(); i++) {
            JLabel label = new JLabel(fach.module.get(i).name);
            label.setVisible(true);
            place(1, i + 1, label);

            JComboBox<String> cb = GUI.createDropDow(noten);
            final int idx = i;
            cb.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String selected = (String) cb.getSelectedItem();

                    try {
                        fach.module.get(idx).note = Double.parseDouble(selected);
                        System.out.println(
                                "Fach: " + fach.module.get(idx).name + "\tneue Note: " + fach.module.get(idx).note);
                    } catch (NumberFormatException nfe) {
                        return;
                    }

                    GUI.update();

                }

            });
            place(2, i + 1, cb);
        }

    }
    
    @Override
    public void initButtons() {
        MyButton back = new MyButton("Zurück");
        back.addActionListener(new ListenerGoToPage(GUI.mainPanel, GUI.frame));
        place(3, 0, back);
    }

}
