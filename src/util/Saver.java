package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Saver {

    private static Saver s;

    public static void save(ArrayList<Fach> faecher) {
        s.saveFaecher(faecher);
    }

    public static void construct() {
        if (s == null) {
            s = new Saver();
        }
    }

    public static void construct(String path) {
        if (s == null) {
            s = new Saver(path);
        }
    }

    // =======================================
    // ============ non Static ===============
    // =======================================

    private String path;
    private final String saveName = "saver.txt";
    private final String tmpSaveName = "saverTMP.txt";

    private Saver() {
        this(".");
    }

    private Saver(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void saveFaecher(ArrayList<Fach> faecher) {
        try {
            File file = createSaveFile();
            FileWriter writer = new FileWriter(file);

            for (Fach fach : faecher) {
                writeFach(writer, fach);
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        endOfTransaction();
    }

    private void writeFach(FileWriter writer, Fach fach) throws IOException {
        String header = "#" + fach.fachname;
        boolean first = true;

        for (Modul m : fach.module) {
            if (m.note > 0.001) {
                if (first) {
                    writer.append(header + "\n");
                    first = false;
                }
                writer.append(m.name + "," + m.note + "\n");
            }
        }
    }

    private File createSaveFile() {
        return new File(path + "/" + tmpSaveName);
    }

    private void endOfTransaction() {
        File tmp = new File(path + "/" + tmpSaveName);
        File old = new File(path + "/" + saveName);

        try {
            Files.deleteIfExists(old.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        tmp.renameTo(old);

    }
}
