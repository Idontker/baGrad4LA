package gui;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import gui.buttons.ListenerGoToPage;
import gui.buttons.MyButton;

public class GUI {

    public static MainFrame frame;
    private JLabel label_fach1, label_fach2, label_fach3, label_ews;
    private JLabel label_note, label_etcs;
    private JLabel label_note1, label_note2, label_note3, label_etcs1, label_etcs2, label_etcs3;
    private JButton[] add_modul;
    private JButton[] change_modul;

    private String[] FACHERLISTE = new String[] { "None", "Mathematik", "Informatik", "Wirtschaft", "Englisch" };

    public GUI() {
        frame = new MainFrame(1080, 480, 10, 6);

        initLabels();
        initButtons();

        frame.place(0, 1, label_fach1);
        frame.place(0, 2, label_fach2);
        frame.place(0, 3, label_fach3);


        frame.place(1, 1, createDropDow(FACHERLISTE));
        frame.place(1, 2, createDropDow(FACHERLISTE));
        frame.place(1, 3, label_ews);

        frame.place(2, 0, label_etcs);
        frame.place(2, 1, label_etcs1);
        frame.place(2, 2, label_etcs2);
        frame.place(2, 3, label_etcs3);

        frame.place(3, 0, label_note);
        frame.place(3, 1, label_note1);
        frame.place(3, 2, label_note2);
        frame.place(3, 3, label_note3);

        for(int i = 0; i < 3; i++){
            //frame.place(4, i+1, add_modul[i]);
            frame.place(4, i+1, change_modul[i]);
        }

        frame.setVisible(true);
    }

    private void initLabels() {
        label_fach1 = new JLabel("Fach 1: ");
        label_fach1.setVisible(true);

        label_fach2 = new JLabel("Fach 2: ");
        label_fach2.setVisible(true);

        label_fach3 = new JLabel("Fach 3: ");
        label_fach3.setVisible(true);

        label_ews = new JLabel("EWS");
        label_ews.setVisible(true);
        // ===================================

        label_note = new JLabel("Teilnote");
        label_note.setVisible(true);

        label_note1 = new JLabel("Note 1");
        label_note1.setVisible(true);
        label_note2 = new JLabel("Note 2");
        label_note2.setVisible(true);
        label_note3 = new JLabel("Note 3");
        label_note3.setVisible(true);

        // ===================================

        label_etcs = new JLabel("ETCS bislang");
        label_etcs.setVisible(true);

        label_etcs1 = new JLabel("ECTS 1");
        label_etcs1.setVisible(true);
        label_etcs2 = new JLabel("ECTS 2");
        label_etcs2.setVisible(true);
        label_etcs3 = new JLabel("ECTS 3");
        label_etcs3.setVisible(true);

    }

    private void initButtons(){
        add_modul = new JButton[3]; 
        change_modul = new JButton[3];
        
        for(int i = 0; i < 3; i++){
            //add_modul[i] = new MyButton("Modul hinzufügen", i);

            change_modul[i] = new MyButton("Fachübersicht");
        }
        GridPanel p = new FachPanel(new String[]{"AuD", "TheoInf", "SP"}, "Informatik");
        change_modul[0].addActionListener(new ListenerGoToPage(p, frame));
    }

    public static JComboBox<String> createDropDow(String items[]) {
        JComboBox<String> cmb = new JComboBox<String>(items);
        cmb.setSelectedItem(0);
        cmb.setVisible(true);
        return cmb;
    }
}
