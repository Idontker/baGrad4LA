package gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import gui.HintTextField;
import gui.MyComboBox;
import gui.buttons.ListenerGoToPage;
import util.Fach;

public class StartPanel extends GridPanel {

    private static final long serialVersionUID = 1L;

    private JLabel label_name, label_fach1, label_fach2, label_schulart, label_warning;
    private MyComboBox cb_schulart, cb_fach1, cb_fach2;
    private JTextField textField_name;
    private JButton button_weiter;

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
        label_warning = new JLabel("Achtung: 2 gleiche Fächer! ");
        label_warning.setForeground(Color.red);

        label_warning.setVisible(false);

        place(0, 1, label_name);
        place(0, 2, label_schulart);
        place(0, 3, label_fach1);
        place(0, 4, label_fach2);
        place(0, 5, label_warning);
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

        textField_name = new HintTextField("insert name");

        cb_fach1 = new MyComboBox();
        cb_fach2 = new MyComboBox();

        cb_fach1.setVisible(false);
        cb_fach2.setVisible(false);

        ActionListener listenForDuplicate = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cb_fach1.getSelectedItem().equals(cb_fach2.getSelectedItem())) {
                    label_warning.setVisible(true);
                } else {
                    label_warning.setVisible(false);
                }

            }
        };

        cb_fach1.addActionListener(listenForDuplicate);
        cb_fach2.addActionListener(listenForDuplicate);

        place(1, 1, textField_name);
        place(1, 2, cb_schulart);
        place(1, 3, cb_fach1);
        place(1, 4, cb_fach2);
    }

    private void initButtons() {
        button_weiter = new JButton("Weiter");
        button_weiter.setVisible(false);

        button_weiter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cb_fach1.getSelectedItem().equals(cb_fach2.getSelectedItem())) {
                    shakeComp(button_weiter);
                    shakeComp(label_warning);
                } else {
                    goToOverview();
                }
            }
        });

        place(1, 5, button_weiter);
    }

    private void updateCBFach() {
        cb_fach1.removeAllItems();
        cb_fach2.removeAllItems();

        if (cb_schulart.getSelectedIndex() == 0) {
            button_weiter.setVisible(false);
            cb_fach1.setVisible(false);
            cb_fach2.setVisible(false);
        } else {
            String schulart = (String) cb_schulart.getSelectedItem();

            button_weiter.setVisible(true);
            cb_fach1.setVisible(true);
            cb_fach2.setVisible(true);

            Collection<Fach> tmp = map.get(schulart).values();
            ArrayList<String> faecherliste = new ArrayList<String>();
            for (Fach fach : tmp) {
                if (fach.fachname.equalsIgnoreCase("EWS") == false
                        && fach.fachname.equalsIgnoreCase("Didaktik+Weiteres") == false) {
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

    private void goToOverview() {
        String schulart = (String) cb_schulart.getSelectedItem();
        HashMap<String, Fach> fachmap = map.get(schulart);

        String f1 = (String) cb_fach1.getSelectedItem();
        String f2 = (String) cb_fach2.getSelectedItem();

        Fach fach[] = new Fach[4];
        fach[0] = fachmap.get(f1);
        fach[1] = fachmap.get(f2);
        fach[2] = fachmap.get("EWS");
        fach[3] = fachmap.get("Didaktik+Weiteres");

        GridPanel overview = new OverviewPanel(fach);
        ListenerGoToPage.PANEL_MAP.put("Overview", overview);

        frame.setVisible(false);
        frame.showPanel(overview);
        frame.setSize(1280, frame.getHeight());
        frame.setVisible(true);
    }

    private void shakeComp(JComponent comp) {
        final Point point = comp.getLocation();
        final int delay = 25;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        moveComp(new Point(point.x + 5, point.y), comp);
                        Thread.sleep(delay);
                        moveComp(point, comp);
                        Thread.sleep(delay);
                        moveComp(new Point(point.x - 5, point.y), comp);
                        Thread.sleep(delay);
                        moveComp(point, comp);
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    private void moveComp(final Point p, JComponent comp) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                comp.setLocation(p);
            }
        });
    }

}
