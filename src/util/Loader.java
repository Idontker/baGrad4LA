package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Loader {

    private String path;

    public Loader() {
        this(".");
    }

    public Loader(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HashMap<String, Fach> loadFaecher(String schulart) {
        schulart = schulart.toLowerCase();

        HashMap<String, Fach> faecher = new HashMap<String, Fach>();

        try {
            loadSettingsInto(faecher, schulart);
            loadSaveInto(faecher);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // printFaecher(faecher);

        return faecher;
    }

    private void loadSettingsInto(HashMap<String, Fach> faecher, String schulart) throws FileNotFoundException {

        Scanner sc = opeFileScanner(path + "/" + schulart + ".cfg");
        Fach fach = null;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.length() == 0) {
                // do Nothing
            } else if (line.charAt(0) == '#') {
                fach = new Fach(line.substring(1).trim(), " ");
                faecher.put(fach.fachname, fach);
            } else {
                Modul m = new Modul(line, ":");
                fach.module.add(m);
            }
        }

        if (fach == null) {
            System.err.println("[ERROR]: Settings file is empty");
            System.exit(1);
        }

        sc.close();

    }

    private void loadSaveInto(HashMap<String, Fach> faecher) throws FileNotFoundException {
        HashMap<String, Fach> map = loadSaveIntoMap();
        if (map == null) {
            return;
        }

        for (Fach fach : faecher.values()) {
            if (map.containsKey(fach.fachname)) {
                updateFachWithMap(map, fach);
            }
        }
    }

    private void updateFachWithMap(HashMap<String, Fach> map, Fach fach) {
        Fach saved = map.get(fach.fachname);

        for (Modul modul : fach.module) {
            for (Modul other : saved.module) {
                if (modul.name.equalsIgnoreCase(other.name)) {
                    modul.note = other.note;
                }
            }
        }
    }

    private HashMap<String, Fach> loadSaveIntoMap() {
        Scanner sc;
        try {
            sc = opeFileScanner(path + "/saver.txt");
        } catch (FileNotFoundException e) {
            return null; // Saver File does not exist
        }

        HashMap<String, Fach> map = new HashMap<String, Fach>();
        Fach fach = null;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            if (line.charAt(0) == '#') {
                fach = new Fach(line.substring(1).trim(), " ");
                map.put(fach.fachname, fach);
            } else {
                Modul m = new Modul(line, ",");
                fach.module.add(m);
            }
        }

        sc.close();
        return map;
    }

    private void printFaecher(HashMap<String, Fach> faecher) {
        for (Fach f : faecher.values()) {
            System.out.println("\t\t" + f.fachname);
            for (Modul m : f.module) {
                System.out.println(m);
            }
        }
    }

    private Scanner opeFileScanner(String pathToFile) throws FileNotFoundException {
        File f = new File(pathToFile);
        return new Scanner(f, "utf-8");
    }
}
