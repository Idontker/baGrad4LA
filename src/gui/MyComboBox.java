package gui;

import java.util.ArrayList;

import javax.swing.JComboBox;

public class MyComboBox extends JComboBox<String> {

    private static final long serialVersionUID = 1L;

    private static String DEFAULT_AUSWAHL = "bitte auswählen";

    public MyComboBox() {
        this(new String[0]);
    }

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

    public void addOptions(ArrayList<String> items) {
        for (String s : items) {
            this.addItem(s);
        }
    }

    public void addOptions(String items[]) {
        for (String s : items) {
            this.addItem(s);
        }
    }

    public void printOptions(String prefix) {
        int size = this.getItemCount();
        for (int i = 0; i < size; i++) {
            String item = this.getItemAt(i);
            System.out.println(prefix + ": Item at " + i + " = " + item);
        }
    }

}