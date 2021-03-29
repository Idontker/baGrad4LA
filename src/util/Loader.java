package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {
    public static final String path = "C:/Users/Karol/proj/baGrad4LA/src";

    public static ArrayList<Fach> loadFaecher() {
        ArrayList<Fach> faecher = new ArrayList<Fach>();
        
        File f = new File(path + "/faecher.txt");
        Scanner sc = null;
        try {
            Fach fach = null;
            sc = new Scanner(f);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.length() == 0){
                    // do Nothing
                } else if (line.charAt(0) == '#') {
                    if(fach != null){
                        faecher.add(fach);
                    }
                    fach =  new Fach(line.substring(1).trim());
                } else {
                    String s[] = line.split(":"); 
                    if(s.length == 2){
                        fach.module.add(new Modul(Double.parseDouble(s[1].trim()), s[0].trim()));
                    } else if(s.length == 3){
                        fach.module.add(new Modul(Double.parseDouble(s[1].trim()), s[0].trim(), Double.parseDouble(s[2].trim())));
                    }
                }
            }
            if(fach != null){
                faecher.add(fach);
            }
        } catch (Exception e) {
        }
        if(sc != null){
            sc.close();
        }
        return faecher;
    }
}
