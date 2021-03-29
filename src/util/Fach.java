package util;

import java.util.ArrayList;

public class Fach {

    public String fachname;
    public ArrayList<Modul> module = new ArrayList<Modul>();
    public double note;

    public Fach(String fachname){
        this.fachname = fachname;
        note = 5.0;
    }

    public String[] getModulnamen(){
        String[] ret = new String[module.size()];
        for (int i = 0; i < module.size(); i++) {
            ret[i] = module.get(i).name;
        }

        return ret;
    }
    
}
