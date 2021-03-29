package main;

import gui.GUI;
import util.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Fach> faecher = Loader.loadFaecher();
        for (Fach f : faecher) {
            System.out.println("\t\t" + f.fachname);
            for (Modul m : f.module) {
                System.out.println(m);
            }
        }

        new GUI(faecher);
        GUI.update();
    }
}