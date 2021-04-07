package util;

import java.util.ArrayList;

public class Fach {

    public String fachname;
    public ArrayList<Modul> module;
    public double ects_ba;
    public double ects_stex;

    public Fach(String inputline, String splitter) {
        // defauls and init
        module = new ArrayList<Modul>();
        ects_ba = 0.0;
        ects_stex = 0.0;

        // read input
        String s[] = inputline.split(splitter);

        this.fachname = s[0];
        if (s.length == 1)
            return;
        this.ects_ba = Double.parseDouble(s[1]);
        if (s.length == 2)
            return;
        this.ects_stex = Double.parseDouble(s[2]);
    }

    public String[] getModulnamen() {
        String[] ret = new String[module.size()];
        for (int i = 0; i < module.size(); i++) {
            ret[i] = module.get(i).name;
        }

        return ret;
    }

}
