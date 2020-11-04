package dem2k.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class SimpleDasSatz {

    private static final int KOPF_LEN = 192;
    private static final int VARTEIL_LEN = 26;

    private static final String FILL = "###########################################";

    private String satzLaenge = FILL.substring(0, 5);

    // Datensatznummer aufsteigend. Beginnend bei 00001, nach Satz-Nr.
    // 99999 nächste Satz-Nr. 00000. Satz-Nr. spielt für die inhaltliche
    // Verarbeitung keine Rolle, ggf. im Rahmen von Anschlußprüfungen
    // überprüft (fortlaufend aufsteigend) :
    private String satzNr = FILL.substring(0, 5); // satzNr erst bei toBinary()

    // Finanzamtsnummer (ohne Länderschlüssel, die letzten drei Stellen der BUFA-Nr):
    private String herkDstNr = FILL.substring(0, 3);

    private String satzArtS = FILL.substring(0, 2);
    private String uSatzArtS = FILL.substring(0, 2);
    private String bundStNr = FILL.substring(0, 18);
    private String aufgS = FILL.substring(0, 2);
    private String tAufgS = FILL.substring(0, 2);
    private String ztrDat = FILL.substring(0, 8);
    private String zerlVorgS = FILL.substring(0, 2);
    private String anwVorgS = FILL.substring(0, 2);
    private String anwDatZt = FILL.substring(0, 12);
    private String sachBerS = FILL.substring(0, 3);
    private String uSachBer = FILL.substring(0, 15);

    // Verwendungsart 1="Create", 2="Update" :
    private char x2sX;

    private String folgeNr = FILL.substring(0, 2);

    private String pos85bis96 = "000000000000";

    // Rücklaufadresse 3-stellig numerisch Dienststelle (ohne LS)
    // hier:011 8-stellig alphanumerisch hier: RZFIDM00
    private String ruecklaufAdr = FILL.substring(0, 11);

    private char x1s;

    // Verarbeitungsbereich (hier: 110) :
    private String verarbBerS = FILL.substring(0, 3);

    // Steuernummernschlüssel / Fallart: hier zunächst:
    // 11 = Veranlagung (und Einheitswert Grundvermögen Hamburg) Daneben weitere
    // Ausprägungen:
    // 12 = Einheitswert Grundvermögen
    // 13 = Umlage Land- und Forstwirte
    // 14 = KfZ-Steuer (unverschlüsselt)
    // 15 = KfZ-Steuer (verschlüsselt) 16 = Erbschaft- und Schenkungs-steuer
    private String bundStNrS = FILL.substring(0, 2);

    private String deDat = FILL.substring(0, 8);

    // Datensatzkopfschlüssel (hier: 0001) :
    private String kopfVarS = FILL.substring(0, 4);

    // Sortierschlüssel (hier: 00):
    private String stxtSortS = FILL.substring(0, 2);

    // Änderungsmerker :
    private char stxtAendM;

    // Bereich:
    private String ergebBerS = FILL.substring(0, 2);

    // Teilbereich / Satzart
    private String ergebTBerS = FILL.substring(0, 2);

    private String pos133bis158 = "00000000000000000000000000";

    // Datenart Hier: 2 = Integrationsdaten :
    private char datArtS;

    private static final char DATARTS_ANWEISUNGSDATEN = '1';
    private static final char DATARTS_SPEICHERDATEN = '2';
    private static final char DATARTS_VOLLMACHTSDATEN = '3';

    private String pos160bis161 = "00";

    // Spezieller Schlüssel, hier =0 :
    private char spezS;

    private String pos163bis182 = "00000000000000000000";

    // Umspeicherungsverfolgungs-Merker Hier: 1
    private char umspVerfM;

    // Erfassungsplatz (hier: 00)
    private String herkErfPl = FILL.substring(0, 2);

    // Schlüssel variabler Teil (hier: 0001)
    private String rumpVarS = FILL.substring(0, 4);
    private String rumpfVarAnz = FILL.substring(0, 3);

    private List<SimpleDasTeil> variablerTeil = new ArrayList<>();

    private String origin;
    private final DasSatzArt satzArtEnum;

    public SimpleDasSatz(String satz) {
        if (satz.length() < KOPF_LEN) {
            throw new IllegalArgumentException(
                    "Satzlaenge < " + KOPF_LEN + " : '" + satz + "'");
        }

        origin = satz;

        satzLaenge = satz.substring(0, 5);
        satzNr = satz.substring(5, 10);
        herkDstNr = satz.substring(10, 13);
        satzArtS = satz.substring(13, 15);
        uSatzArtS = satz.substring(15, 17);
        bundStNr = satz.substring(17, 35);
        aufgS = satz.substring(35, 37);
        tAufgS = satz.substring(37, 39);
        ztrDat = satz.substring(39, 47);
        zerlVorgS = satz.substring(47, 49);
        anwVorgS = satz.substring(49, 51);
        anwDatZt = satz.substring(51, 63);
        sachBerS = satz.substring(63, 66);
        uSachBer = satz.substring(66, 81);
        x2sX = satz.substring(81, 82).charAt(0);
        folgeNr = satz.substring(82, 84);
        pos85bis96 = satz.substring(84, 96);
        ruecklaufAdr = satz.substring(96, 107);
        x1s = satz.substring(107, 108).charAt(0);
        verarbBerS = satz.substring(108, 111);
        bundStNrS = satz.substring(111, 113);
        deDat = satz.substring(113, 121);
        kopfVarS = satz.substring(121, 125);
        stxtSortS = satz.substring(125, 127);
        stxtAendM = satz.substring(127, 128).charAt(0);
        ergebBerS = satz.substring(128, 130);
        ergebTBerS = satz.substring(130, 132);
        pos133bis158 = satz.substring(132, 158);
        datArtS = satz.substring(158, 159).charAt(0);
        pos160bis161 = satz.substring(159, 161);
        spezS = satz.substring(161, 162).charAt(0);
        pos163bis182 = satz.substring(162, 182);
        umspVerfM = satz.substring(182, 183).charAt(0);
        herkErfPl = satz.substring(183, 185);
        rumpVarS = satz.substring(185, 189);
        rumpfVarAnz = satz.substring(189, 192);

        String varTeil = satz.substring(KOPF_LEN);
        variablerTeil.addAll(SimpleDasTeil.parse(varTeil));
        satzArtEnum = DasSatzArt.getByKeys(datArtS, pos160bis161, spezS);
    }

    public SimpleStxt getSchluesseltext() {
        int iAufgs = Integer.parseInt(aufgS);
        int iSachber = Integer.parseInt(sachBerS);
        SimpleStxt result = new SimpleStxt(x1s, iAufgs, iSachber);

        long iUSB = Long.parseLong(uSachBer);
        if (iUSB > 0L) {
            result = result.untersachbereich(iUSB);
        } else {
            result = getUsachBerFromKz009(result);
        }
        return result;
    }

    private SimpleStxt getUsachBerFromKz009(SimpleStxt result) {
        Optional<SimpleDasTeil> firstKz009 = variablerTeil.stream()
                .filter(e -> "009".equals(e.getKz())).findFirst();
        if (firstKz009.isPresent()) {
            return result.untersachbereich(firstKz009.get().getWertNumerisch());
        }
//		for (SimpleDasTeil teil : variablerTeil) {
//			if ("009".equals(teil.getKz())) {
//				return result.untersachbereich(teil.getWertNumerisch());
//			}
//		}
        return result;
    }

    public String getSteuernummerBund() {
        return bundStNr;
    }

    public String getSteuernummerGinster() {
        String stnr = getSteuernummerBund().trim();
        return stnr.substring(0, 4) + stnr.substring(6);
    }

    public String getSteuernummerPretty() {
        String stnr = getSteuernummerBund().trim();
        stnr = stnr.substring(1, 4) + "-" + stnr.substring(7, 11) + "-" + stnr.substring(11);
        return stnr;
    }

    public Map<String, String> getKopfdaten() {
        Map<String, String> result = new TreeMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                Class<?> c = field.getType();
                if (String.class.isAssignableFrom(c) || char.class.isAssignableFrom(c)) {
                    result.put(field.getName(), field.get(this).toString());
                }
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                result.put(field.getName(), e.getMessage());
            }
        }
        return result;
    }

    public int getSatznummer() {
        return Integer.valueOf(satzNr);
    }

    public boolean isSpeicherdatensatz() {
//		return (datArtS == DATARTS_SPEICHERDATEN && "08".equals(pos160bis161) && spezS == '0')
//				|| (datArtS == '3' && "08".equals(pos160bis161) && spezS == 'T');
        return satzArtEnum.name().startsWith("SPD");
    }

    public boolean isAnweisungsdatensatz() {
//		return (datArtS == DATARTS_ANWEISUNGSDATEN && "00".equals(pos160bis161) && spezS == '0')
//				|| (datArtS == '3' && "00".equals(pos160bis161) && spezS == 'V');
        return satzArtEnum.name().startsWith("ANW");
    }

    public boolean isVollmachtsDatensatz() {
//		return datArtS == DATARTS_VOLLMACHTSDATEN
//				&& "00".equals(pos160bis161) && spezS == '0';
        return satzArtEnum.name().startsWith("VDB");
    }

    public String getSatzArtAsString() {
        return "POS-159-DAT-ART-S='" + datArtS
                + "'; POS-160='" + pos160bis161
                + "'; POS-162-SPEZ-S='" + spezS + "'";
    }

    @Override
    public String toString() {
        return mapKopfdaten(getSatznummer(), 0);
    }

    public String replaceStNr(String stnr) {
        herkDstNr = stnr.substring(1, 4);
        bundStNr = String.format("%1$-18s", stnr);
        return mapKopfdaten(getSatznummer(), 0) + origin.substring(KOPF_LEN);
    }

    private String mapKopfdaten(int satznummer, int folgenummer) {
        StringBuilder sb = new StringBuilder();
        String currentSatzNr = String.format("%05d", satznummer);
        String currentFolgeNr = String.format("%02d", folgenummer);
        sb.append(satzLaenge).append(currentSatzNr).append(herkDstNr)
                .append(satzArtS).append(uSatzArtS).append(bundStNr)
                .append(aufgS).append(tAufgS).append(ztrDat).append(zerlVorgS)
                .append(anwVorgS).append(anwDatZt).append(sachBerS)
                .append(uSachBer).append(x2sX).append(currentFolgeNr)
                .append(pos85bis96).append(ruecklaufAdr).append(x1s)
                .append(verarbBerS).append(bundStNrS).append(deDat)
                .append(kopfVarS).append(stxtSortS).append(stxtAendM)
                .append(ergebBerS).append(ergebTBerS)
                .append(pos133bis158).append(datArtS)
                .append(pos160bis161).append(spezS)
                .append(pos163bis182).append(umspVerfM).append(herkErfPl)
                .append(rumpVarS).append(rumpfVarAnz);
        return sb.toString();
    }

    public List<DasTeilfeld> asOriginPieces() {
        List<DasTeilfeld> result = new ArrayList<>();
        //result.add(origin.substring(0, KOPF_LEN));

        String varTeil = origin.substring(KOPF_LEN);
        for (int i = 0; i < varTeil.length() / VARTEIL_LEN; i++) {
            int pos = i * VARTEIL_LEN;
            result.add(new DasTeilfeld(varTeil.substring(pos, pos + VARTEIL_LEN)));
        }
        return result;
    }

    public List<SimpleDasTeil> getVariablerTeil() {
        return variablerTeil;
    }

    public DasSatzArt getSatzArt() {
        return satzArtEnum;
    }
}
