package main;

import gui.GUI;
import util.*;

import java.util.HashMap;

public class Main {

    public static final String path = "C:/Users/Karol/proj/baGrad4LA/config";

    public static void main(String[] args) {

        Loader loader = new Loader(path);

        HashMap<String, Fach> faecher = loader.loadFaecher();

        Saver.construct(path);

        
        HashMap<String, Fach> dummy_realschule = new HashMap<String, Fach>();
        dummy_realschule.put("Informatik", faecher.get("Informatik"));
        
        HashMap<String, Fach> dummy_mittelschule = new HashMap<String, Fach>();
        dummy_mittelschule.put("Englisch", faecher.get("Englisch"));
        
        HashMap<String, HashMap<String, Fach>> map = new HashMap<String, HashMap<String, Fach>>();
        map.put("Gymnasium", faecher);
        map.put("Realschule", dummy_realschule);
        map.put("Mittelschule", dummy_mittelschule);
        
        
        GUI g = new GUI(map);
        // g.update(); TODO
    }
}