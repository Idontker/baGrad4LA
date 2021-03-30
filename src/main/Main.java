package main;

import gui.GUI;
import util.*;

import java.util.ArrayList;

public class Main {

    public static final String path = "C:/Users/Karol/proj/baGrad4LA";

    public static void main(String[] args) {

        Loader loader = new Loader(path);

        ArrayList<Fach> faecher = loader.loadFaecher();

        Saver.construct(path);

        new GUI(faecher);
        GUI.update();
    }
}