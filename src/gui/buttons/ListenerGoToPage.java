package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JComboBox;

import gui.*;
import gui.panels.*;

public class ListenerGoToPage implements ActionListener {

    private String goTo;
    private MainFrame frame;
    private JComboBox<String> combo;

    public static HashMap<String, GridPanel> PANEL_MAP = new HashMap<String, GridPanel>();

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
        GridPanel panel = PANEL_MAP.get(next);

        if (panel == null) {
            System.err.println("Subject not found: " + next);
        } else {
            frame.showPanel(panel);
            frame.setSize(frame.getWidth(), panel.rows * 40);
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
