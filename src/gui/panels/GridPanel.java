package gui.panels;

import java.awt.*;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class GridPanel extends JPanel {

    protected static final long serialVersionUID = 1L;
    protected JPanel[][] panels;

    public GridPanel(int rows, int cels) {
        this.setLayout(new GridLayout(rows, cels));

        panels = new JPanel[rows][cels];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cels; j++) {
                panels[i][j] = new JPanel();
                this.add(panels[i][j]);
            }
        }
    }

    public void place(int x, int y, JComponent c) {
        panels[y][x].add(c);
    }

    public void removeComponent(int x, int y, JComponent c) {
        panels[y][x].remove(c);
    }


    public abstract void initButtons(); 
}
