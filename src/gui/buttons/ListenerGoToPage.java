package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import gui.GridPanel;
import gui.MainFrame;

public class ListenerGoToPage implements ActionListener {

    private GridPanel goTo;
    private MainFrame frame;

    public ListenerGoToPage(GridPanel goTo, MainFrame frame){
        this.goTo = goTo;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        frame.showNewPanel(goTo);
    }

}

