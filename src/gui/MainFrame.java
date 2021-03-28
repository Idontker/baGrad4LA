package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static GridPanel mainPanel;
    
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

        currentPanel = new GridPanel(rows, cols);
        mainPanel = currentPanel;
        this.add(currentPanel);
    }

    public void place(int x, int y, JComponent c) {
        currentPanel.place(x, y, c);
    }

    public void removeComponent(int x, int y, JComponent c) {
        currentPanel.removeComponent(x, y, c);
    }

    public void showNewPanel(GridPanel next) {
        currentPanel.setVisible(false);
        this.remove(currentPanel);

        System.out.println("Check1:" + currentPanel);
        
        this.add(next);
        currentPanel = next;
        System.out.println("Check=?");
        next.setVisible(true);
    }

}
