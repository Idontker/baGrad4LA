package gui;

import java.util.ArrayList;

import javax.swing.JComboBox;

public class MyComboBox extends JComboBox<String> {

    private static final long serialVersionUID = 1L;

    private static String DEFAULT_AUSWAHL = "bitte auswählen";

    public MyComboBox(ArrayList<String> items) {
        this(items, false);
    }

    public MyComboBox(ArrayList<String> items, boolean includeDefault) {
        if (includeDefault) {
            this.addItem(DEFAULT_AUSWAHL);
        }

        addOptions(items);
        setSelectedItem(0);
        setVisible(true);
    }

    public MyComboBox(String items[]) {
        this(items, false);
    }

    public MyComboBox(String items[], boolean includeDefault) {
        if (includeDefault) {
            this.addItem(DEFAULT_AUSWAHL);
        }
        addOptions(items);
        setSelectedItem(0);
        setVisible(true);

    }

    private void addOptions(ArrayList<String> items) {
        for (String s : items) {
            this.addItem(s);
        }
    }

    private void addOptions(String items[]) {
        for (String s : items) {
            this.addItem(s);
        }
    }

}