package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Saver {

    private static Saver s;
    private static Fach[] def_facher;

    public static void setFaecher(ArrayList<Fach> loaded_defaults) {
        def_facher = loaded_defaults.toArray(new Fach[loaded_defaults.size()]);
    }

    public static void save() {
        s.saveFaecher(def_facher);
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

    public void saveFaecher(Fach[] faecher) {
        try {
            OutputStreamWriter out = getOutputStreamWriter();
            BufferedWriter writer = new BufferedWriter(out);

            for (Fach fach : faecher) {
                writeFach(writer, fach);
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        endOfTransaction();
    }

    private void writeFach(BufferedWriter writer, Fach fach) throws IOException {
        String header = "#" + fach.fachname;
        boolean first = true;

        for (Modul m : fach.module) {
            if (m.note > 0.001) {
                if (first) {
                    writer.append(header + "\n");
                    first = false;
                }
                writer.append(m.note + "," + m.name + "\n");
            }
        }
    }

    private OutputStreamWriter getOutputStreamWriter() throws FileNotFoundException, UnsupportedEncodingException {
        File file = createSaveFile();
        FileOutputStream fileStream = new FileOutputStream( file.getAbsolutePath());
        OutputStreamWriter out = new OutputStreamWriter(fileStream, "UTF-8");
        return out;
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
