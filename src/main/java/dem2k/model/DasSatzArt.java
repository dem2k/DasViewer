package dem2k.model;

public enum DasSatzArt {

    SPD('2', "08", '0', "SPD"), SPD1('3', "08", 'T', "SPD"), SPD2('3', "00", 'T', "SPD"),
    ANW('1', "00", '0', "ANW"), ANW1('1', "00", 'V', "ANW"), ANW2('3', "00", 'V', "ANW"),
    VDB('3', "00", '0', "VDB"), VDB2('0', "00", 'A', "VDB"), UNKN('?', "??", '?', "UNKN");

    private final String dir;
    private char datArtS, spezS;
    private String pos160bis161;

    DasSatzArt(char datArtS, String pos160bis161, char spezS, String dir) {
        this.datArtS = datArtS;
        this.pos160bis161 = pos160bis161;
        this.spezS = spezS;
        this.dir = dir;
    }

    public static DasSatzArt getByKeys(char datArtS, String pos160bis161, char spezS) {
        for (DasSatzArt art : DasSatzArt.values()) {
            if (art.datArtS == datArtS && art.pos160bis161.equals(pos160bis161) && art.spezS == spezS) {
                return art;
            }
        }
        return UNKN;
    }

    public String getDir() {
        return dir;
    }
}
