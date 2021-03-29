package gui;

import javax.swing.*;
import java.awt.*;
import gui.panels.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private GridPanel currentPanel;

    public MainFrame() {
        this(500, 500);
    }

    public MainFrame(int x, int y) {
        this(x, y, 5, 5);
    }

    public MainFrame(int x, int y, int rows, int cols) {
        this.setSize(x, y);
        this.setTitle("Notenberechnung Lehramt Gymnasium");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.CYAN);
    }

    public void place(int x, int y, JComponent c) {
        currentPanel.place(x, y, c);
    }

    public void removeComponent(int x, int y, JComponent c) {
        currentPanel.removeComponent(x, y, c);
    }

    public void showPanel(GridPanel next) {
        if(currentPanel != null){
            currentPanel.setVisible(false);
            this.remove(currentPanel);
        }
        
        currentPanel = next;
        next.setVisible(true);
        this.add(next);
    }

}
