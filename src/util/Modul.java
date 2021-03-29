package util;

public class Modul {
    public double note;
    public double etcs;
    public String name;
    public double gewicht;

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

    @Override
    public String toString() {
        return note + " * " + gewicht + "\t" + etcs + " ETCS" + "\t" + name;
    }
}
