package gui;

import java.util.HashMap;

import gui.buttons.ListenerGoToPage;
import gui.panels.GridPanel;
import gui.panels.StartPanel;
import util.Fach;

public class GUI {

    public static GUI gui;

    public MainFrame frame;
    public HashMap<String, Fach> faechermap;

    public GUI(HashMap<String, HashMap<String, Fach>> map) {
        GUI.gui = this;
        
        frame = new MainFrame(480, 460);

        StartPanel startPanel = new StartPanel(map);
        startPanel.setFrame(frame);
        frame.showPanel(startPanel);

        frame.setVisible(true);
    }
}