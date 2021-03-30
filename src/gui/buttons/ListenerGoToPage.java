package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import gui.*;
import gui.panels.*;

public class ListenerGoToPage implements ActionListener {

    private GridPanel goTo;
    private MainFrame frame;
    private JComboBox<String> combo;

    public ListenerGoToPage(JComboBox<String> combo) {
        this.goTo = null;
        this.frame = GUI.frame;
        this.combo = combo;
    }

    public ListenerGoToPage(GridPanel goTo) {
        this.goTo = goTo;
        this.frame = GUI.frame;
        this.combo = null;
    }

    public ListenerGoToPage(GridPanel goTo, MainFrame frame) {
        this.goTo = goTo;
        this.frame = frame;
        this.combo = null;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        GridPanel next = goTo;
        if (next == null) {
            String key = (String) combo.getSelectedItem();
            next = GUI.FACH_PANEL_MAP.get(key);
            if (next == null) {
                System.err.println("Subject not found: " + key);
                return;
            }
        }
        frame.showPanel(next);
    }

}
