package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import gui.*;
import gui.panels.*;

public class ListenerGoToPage implements ActionListener {

    private String goTo;
    private MainFrame frame;
    private JComboBox<String> combo;

    public static HashMap<String, JPanel> PANEL_MAP = new HashMap<String, JPanel>();

    public ListenerGoToPage(JComboBox<String> combo) {
        this.goTo = null;
        this.frame = GUI.gui.frame;
        this.combo = combo;
    }

    public ListenerGoToPage(String goTo) {
        this.goTo = goTo;
        this.frame = GUI.gui.frame;
        this.combo = null;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String next = getGoToName();
        JPanel panel = PANEL_MAP.get(next);

        if (panel == null) {
            System.err.println("Subject not found: " + next);
        } else {
            frame.showPanel(panel);
            if (panel instanceof FachPanel) {
                FachPanel fp = (FachPanel) panel;
                frame.setSize(frame.getWidth(), 20 + 40 * fp.getRows());
            } else {
                frame.setSize(frame.getWidth(), 400);
            }
        }
    }

    private String getGoToName() {
        if (combo != null) {
            return (String) combo.getSelectedItem();
        } else {
            return goTo;
        }
    }

}
