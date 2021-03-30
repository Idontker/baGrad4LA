package gui;

import java.awt.event.*;

import util.Modul;

public class NotenBox extends MyComboBox {

    private static final long serialVersionUID = 1L;
    private static final String[] noten = new String[] { "1.0", "1.3", "1.7", "2.0", "2.3", "2.7", "3.0", "3.3", "3.7",
            "4.0" };

    public NotenBox(Modul modul) {
        super(noten, true);

        int i = indexOfGrad(modul.note) + 1;

        this.setSelectedItem(this.getItemAt(i));
        this.setVisible(true);

        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) getSelectedItem();

                double newValue = 0.0;
                try {
                    newValue = Double.parseDouble(selected);
                } catch (NumberFormatException nfe) {
                }

                modul.note = newValue;

                GUI.update();

            }

        });
    }

    private int indexOfGrad(double d) {
        for (int i = 0; i < noten.length; i++) {
            double grad = Double.parseDouble(noten[i]);

            if (Math.abs(grad - d) < 0.001) {
                return i;
            }
        }
        return -1;

    }
}
