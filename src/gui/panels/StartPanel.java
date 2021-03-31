package gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;

import gui.MyComboBox;
import util.Fach;

public class StartPanel extends GridPanel {

    private JLabel label_name, label_fach1, label_fach2, label_schulart;
    private MyComboBox cb_schulart, cb_fach1, cb_fach2;
    private JTextField textField_name;

    private HashMap<String, HashMap<String, Fach>> map;

    // public StartPanel(String[] schularten, HashMap<String,HashMap<String,Fach>
    // fachnNachSchulart);
    public StartPanel(HashMap<String, HashMap<String, Fach>> map) {
        super(6, 2);

        this.map = map;

        initLabels();
        initInputs();
        initButtons();
    }

    private void initLabels() {
        label_name = new JLabel("Name:");
        label_schulart = new JLabel("Schulart:");
        label_fach1 = new JLabel("1. Fach:");
        label_fach2 = new JLabel("2. Fach:");

        label_name.setVisible(true);
        label_schulart.setVisible(true);
        label_fach1.setVisible(true);
        label_fach2.setVisible(true);

        place(0, 1, label_name);
        place(0, 2, label_schulart);
        place(0, 3, label_fach1);
        place(0, 4, label_fach2);
    }

    private void initInputs() {
        Object[] tmp = map.keySet().toArray();
        String[] schularten = Arrays.copyOf(tmp, tmp.length, String[].class);
        Arrays.sort(schularten);

        cb_schulart = new MyComboBox(schularten, true);
        cb_schulart.setSelectedIndex(0);
        cb_schulart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCBFach();
            }
        });

        textField_name = new JTextField("insert name", 10);

        cb_fach1 = new MyComboBox();
        cb_fach2 = new MyComboBox();

        textField_name.setVisible(true);
        cb_fach1.setVisible(false);
        cb_fach2.setVisible(false);

        place(1, 1, textField_name);
        place(1, 2, cb_schulart);
        place(1, 3, cb_fach1);
        place(1, 4, cb_fach2);
    }

    @Override
    public void initButtons() {

    }

    private void updateCBFach() {
        cb_fach1.removeAllItems();
        cb_fach2.removeAllItems();

        if (cb_schulart.getSelectedIndex() == 0) {
            // cb_fach1.setVisible(false);
            // cb_fach2.setVisible(false);
        } else {
            String schulart = (String) cb_schulart.getSelectedItem();

            cb_fach1.setVisible(true);
            cb_fach2.setVisible(true);

            Collection<Fach> tmp = map.get(schulart).values();
            ArrayList<String> faecherliste = new ArrayList<String>();
            for (Fach fach : tmp) {
                if (fach.fachname.equalsIgnoreCase("EWS") == false) {
                    faecherliste.add(fach.fachname);
                }
            }
            // sort
            Collections.sort(faecherliste.subList(0, faecherliste.size()));

            cb_fach1.addOptions(faecherliste);
            cb_fach2.addOptions(faecherliste);
            if (faecherliste.size() > 1) {
                cb_fach2.setSelectedIndex(1);
            }
        }

    }

}
