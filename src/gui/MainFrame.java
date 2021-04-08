package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel currentPanel;

    public MainFrame() {
        this(500, 500);
    }

    public MainFrame(int x, int y) {
        this.setSize(x, y);
        this.setTitle("Notenberechnung Lehramt Gymnasium");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.CYAN);
    }

    public void showPanel(JPanel next) {
        if (currentPanel != null) {
            currentPanel.setVisible(false);
            this.remove(currentPanel);
        }

        currentPanel = next;
        next.setVisible(true);
        this.add(next);
    }

}
