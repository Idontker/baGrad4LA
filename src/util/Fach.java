package util;

import java.util.ArrayList;

public class Fach {

    public String fachname;
    public ArrayList<Modul> module;
    public double etcs_ba;
    public double etcs_stex;

    public Fach(String inputline, String splitter) {
        // defauls and init
        module = new ArrayList<Modul>();
        etcs_ba = 0.0;
        etcs_stex = 0.0;

        // read input
        String s[] = inputline.split(splitter);

        this.fachname = s[0];
        if (s.length == 1)
            return;
        this.etcs_ba = Double.parseDouble(s[1]);
        if (s.length == 2)
            return;
        this.etcs_stex = Double.parseDouble(s[2]);
    }

    public String[] getModulnamen() {
        String[] ret = new String[module.size()];
        for (int i = 0; i < module.size(); i++) {
            ret[i] = module.get(i).name;
        }

        return ret;
    }

}
