package main;

import gui.GUI;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static final String path = "./config";

    public static void main(String[] args) {

        Loader loader = new Loader(path);

        HashMap<String, Fach> gym = loader.loadFaecher("Gymnasium");
        // HashMap<String, Fach> real = loader.loadFaecher("Realschule");
        // HashMap<String, Fach> mittel = loader.loadFaecher("Mittelschule");

        HashMap<String, HashMap<String, Fach>> map = new HashMap<String, HashMap<String, Fach>>();
        map.put("Gymnasium", gym);
        // map.put("Realschule", real);
        // map.put("Mittelschule", mittel);

        Saver.construct(path);
        ArrayList<Fach> allFaecher = new ArrayList<Fach>();
        allFaecher.addAll(gym.values());
        Saver.setFaecher(allFaecher);

        new GUI(map);
    }
}