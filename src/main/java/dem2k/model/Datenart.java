package dem2k.model;

import java.util.ArrayList;
import java.util.List;

public enum Datenart {
    SPEICHERDATEN("SPD"), ANWEISUNGSDATEN("ANW"), REVISION("REV"), KEINE_REVISION("NOREV");

    public static final int LEN_OF_ALL = "XXX, XXX, XXX".length();

    private String shortname;

    Datenart(String shortname) {
        this.shortname = shortname;
    }

    public static List<Datenart> getAll() {
        List<Datenart> result = new ArrayList<>();
        result.add(SPEICHERDATEN);
        result.add(ANWEISUNGSDATEN);
        result.add(REVISION);
        return result;
    }

    @Override
    public String toString() {
        return shortname;
    }
}
