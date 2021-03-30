package main;

import gui.GUI;
import util.*;

import java.util.HashMap;

public class Main {

    public static final String path = "C:/Users/Karol/proj/baGrad4LA";

    public static void main(String[] args) {

        Loader loader = new Loader(path);

        HashMap<String,Fach> faecher = loader.loadFaecher();

        Saver.construct(path);

        GUI g = new GUI(faecher);
        g.update();
    }
}