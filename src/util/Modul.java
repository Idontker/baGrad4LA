package util;

import java.util.ArrayList;

public class Modul {
    public double note;
    public String name;
    public double etcs;
    public double gewicht;
    public ArrayList<String> restricted_fach = new ArrayList<String>();
    public ArrayList<String> necessary_fach = new ArrayList<String>();

    public Modul(String loadedString, String divider) {
        // default value for gewicht:
        gewicht = 1.0;

        String s[] = loadedString.split(divider);
        for (int i = 0; i < s.length; i++) {
            s[i].trim();
        }

        int idx = 0;

        if (isDouble(s[idx])) {
            this.note = Double.parseDouble(s[idx]);
            idx++;
        }
        this.name = s[idx];
        idx++;

        if (idx >= s.length)
            return;
        this.etcs = Double.parseDouble(s[idx]);
        idx++;
        if (idx >= s.length)
            return;
        this.gewicht = isDouble(s[idx]) ? Double.parseDouble(s[idx]) : 1;
        idx++;
        if (idx >= s.length)
            return;
        initNebenfaecher(s[idx]);
        System.out.println(name + "\tr: " + restricted_fach + "\tn: " + necessary_fach);
    }

    public Modul(double etcs, String name, double gewicht) {
        this(0.0, etcs, name);
        this.gewicht = gewicht;
    }

    public Modul(double etcs, String name) {
        this(0.0, etcs, name);
    }

    public Modul(double note, double etcs, String name) {
        this.note = note;
        this.etcs = etcs;
        this.name = name;
        gewicht = 1.0;
    }

    private void initNebenfaecher(String modifier) {
        for (String s : modifier.split(" ")) {
            char first = s.charAt(0);
            switch (first) {
            case '-':
                restricted_fach.add(s.substring(1));
                break;
            case '+':
                necessary_fach.add(s.substring(1));
                break;
            default:
            }
        }
    }

    public boolean showIfNebenfachIs(ArrayList<String> nebenfaecher) {
        boolean ret = true;

        if(necessary_fach.size() > 0){
            int n = necessary_fach.size();
            for(String necessary: necessary_fach){
                if(nebenfaecher.contains(necessary)){
                    n--;
                }
            }
            return n == 0;
        }

        for (String restricted: restricted_fach) {
            if(nebenfaecher.contains(restricted)){
                return false;
            }
        }
        // if (necessary_fach.size() > 0) {

        // }
        return ret;
    }

    @Override
    public String toString() {
        return note + " * " + gewicht + "\t" + etcs + " ETCS" + "\t" + name ;
    }

    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
