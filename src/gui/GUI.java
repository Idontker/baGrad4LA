package gui;

import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI {

    private JFrame frame;
    private JLabel label;

    private String[] FACHERLISTE = new String[] { "Mathematik", "Informatik", "Wirtschaft", "Englisch" };

    public GUI() {
        init_frame();

        label = new JLabel("Test Text");
        label.setVisible(true);

        frame.add(createDropDow(FACHERLISTE, "Fach 1"));
        frame.add(createDropDow(FACHERLISTE, "Fach 2"));
        frame.add(label);

        frame.setVisible(true);
    }

    private void init_frame() {
        frame = new JFrame();
        frame.setSize(400, 400);
        frame.setTitle("Notenberechnung Lehramt Gymnasium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.CYAN);
        frame.setLayout(new FlowLayout());
    }

    private JComboBox<String> createDropDow(String items[], String string) {
        JComboBox<String> cmb = new JComboBox<String>(items);
        // cmb.setSelectedItem(0);
        cmb.setVisible(true);
        cmb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cmb) {
                    String msg = (String) cmb.getSelectedItem();
                    label.setText(string + msg);
                }

            }

        });

        return cmb;
    }
}
