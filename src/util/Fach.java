package util;

import java.util.ArrayList;

public class Fach {

    public String fachname;
    public ArrayList<Modul> module = new ArrayList<Modul>();
    public double etcs_ba;
    public double etcs_stex;

    public Fach(String inputline, String splitter) {
        String s[] = inputline.split(splitter);

        this.fachname = s[0];
        this.etcs_ba = Double.parseDouble(s[1]);
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
