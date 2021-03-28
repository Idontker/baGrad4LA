package gui;

import javax.swing.JLabel;

import gui.buttons.ListenerGoToPage;
import gui.buttons.MyButton;

public class FachPanel extends GridPanel{

    private static final long serialVersionUID = 1L;
    private final String[] noten = new String[] { "none", "1,0", "1,3", "1,7", "2,0", "2,3", "2,7", "3,0", "3,3", "3,7",
            "4,0" };

    public FachPanel(String[] modulnamen, String fachname) {
        super(4,modulnamen.length+1);
        JLabel fachLabel = new JLabel(fachname);
        fachLabel.setVisible(true);
        place(0, 0, fachLabel);

        MyButton back = new MyButton("Zurück");
        back.addActionListener(new ListenerGoToPage(MainFrame.mainPanel, GUI.frame));
        place(3,0,back);

        for(int i = 0; i < modulnamen.length; i++){
            JLabel label = new JLabel(modulnamen[i]);
            label.setVisible(true);
            place(1, i+1, label);
            place(2, i+1, GUI.createDropDow(noten));
        }


    }

}
