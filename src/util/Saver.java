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
import java.util.Arrays;

public class Saver {

    private static Saver s;
    private static Fach[] def_facher;

    public static void setFaecher(ArrayList<Fach> loaded_defaults) {
        def_facher = loaded_defaults.toArray(new Fach[loaded_defaults.size()]);
    }

    public static void save() {
        s.saveFaecher(def_facher);
    }

    public static Saver getSaver() {
        return s;
    }

    public static Saver construct(String path) {
        if (s == null) {
            s = new Saver(path);
        }
        return s;
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

    public void saveConfigs(String schulart, String faecher[]) {
        try {
            OutputStreamWriter out = getOutputStreamWriter("save_config.txt");
            BufferedWriter writer = new BufferedWriter(out);

            writer.append(schulart + "\n");
            writer.append(Arrays.toString(faecher));
            writer.flush();

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFaecher(Fach[] faecher) {
        try {
            OutputStreamWriter out = getOutputStreamWriter(tmpSaveName);
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
                int using = m.using ? 1 : 0;
                writer.append(m.note + "," + using + "," + m.name + "\n");
            }
        }
    }

    private OutputStreamWriter getOutputStreamWriter(String filename)
            throws FileNotFoundException, UnsupportedEncodingException {
        File file = createSaveFile(filename);
        FileOutputStream fileStream = new FileOutputStream(file.getAbsolutePath());
        OutputStreamWriter out = new OutputStreamWriter(fileStream, "UTF-8");
        return out;
    }

    private File createSaveFile(String filename) {
        return new File(path + "/" + filename);
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
