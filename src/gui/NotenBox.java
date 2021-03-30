package gui;

import java.awt.event.*;

import util.Modul;

public class NotenBox extends MyComboBox {

    private static final long serialVersionUID = 1L;
    private static final String[] noten = new String[] { "1.0", "1.3", "1.7", "2.0", "2.3", "2.7", "3.0", "3.3", "3.7",
            "4.0" };

    public NotenBox(Modul modul) {
        super(noten, true);

        this.setSelectedItem(indexOfGrad(modul.note));
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
        String grad = String.format("%.2f", Math.round(d * 10) / 10.0);

        for (int i = 1; i < noten.length; i++) {
            if (grad.equals(noten[i]) == true) {
                return i;
            }
        }
        return 0;

    }
}
