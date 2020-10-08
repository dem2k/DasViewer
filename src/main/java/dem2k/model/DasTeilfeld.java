package dem2k.model;

import java.text.DecimalFormat;


/**
 * DAS variabler Teil. Immutable
 *
 * @author Dirk Schmidt (x003222)
 */

public class DasTeilfeld {
    private static final long serialVersionUID = 1L;

    public static final int MAX_LEN_WERT = 19;
    public static final int MAX_LEN_WERT_NUMERISCH = MAX_LEN_WERT - 1; // 1 Stelle fuer Vorzeichen

    public static final DecimalFormat NUM_FORMAT = new DecimalFormat("000000000000000000+;000000000000000000-");

    public enum Teilfeld {
        NUM_TF2VONZWEI_ODER_TF1VONEINS('1'), NUM_TF1VONZWEI('2'),
        ALPHA_TF2VONZWEI_ODER_TF1VONEINS('5'), ALPHA_TF1VONZWEI('6');

        private char dasCode;

        Teilfeld(char dasCode) {
            this.dasCode = dasCode;
        }

        public char getCode() {
            return dasCode;
        }

        public boolean isAlphanum() {
            return this == ALPHA_TF2VONZWEI_ODER_TF1VONEINS || this == ALPHA_TF1VONZWEI;
        }
    }

    public static final char HERKUNFT_0 = '0';    // SPD
    public static final char HERKUNFT_1 = '1';    // ANW
    public static final char HERKUNFT_2 = '2';    // VDB

    private String kz = "xxx";
    private char datS;
    private char feldS;
    private char loeschM;
    private char feldHerkS;
    private String wert;

    private final String origin;

    public DasTeilfeld(String teil26) {
        if (teil26.length() != 26) {
            throw new IllegalArgumentException("Teilfeld muss 26 Zeichen haben.");
        }
        kz = teil26.substring(0, 3);
        datS = teil26.substring(3, 4).charAt(0);
        feldS = teil26.substring(4, 5).charAt(0);
        loeschM = teil26.substring(5, 6).charAt(0);
        feldHerkS = teil26.substring(6, 7).charAt(0);
        wert = teil26.substring(7, 26);
        origin = teil26;
    }


    public String getKz() {
        return kz;
    }

    public boolean isDatiert() {
        return datS != '0';
    }

    public boolean isAlphanumerisch() {
        return feldS == '5' || feldS == '6';
    }

    public boolean isNumerisch() {
        return feldS == '1' || feldS == '2';
    }

    public boolean isTeilfeld1vonZwei() {
        return feldS == '2' || feldS == '6';
    }


    public String getDatum() {
        switch (datS) {
            case '0':
                return null;
            case '1':
                return wert.substring(0, 4);
            case '2':
                return wert.substring(0, 6);
            case '3':
                return wert.substring(0, 8);
            default:
                return null;
        }
    }

    /**
     * @return Wert dieses Teilfelds excl. Datum als String.
     */
    public String getWert() {
        switch (datS) {
            case '0':
                return wert;
            case '1':
                return wert.substring(4, wert.length());
            case '2':
                return wert.substring(6, wert.length());
            case '3':
                return wert.substring(8, wert.length());
            default:
                return wert;
        }
    }

    public char getHerkunft() {
        return feldHerkS;
    }

    /**
     * @return Wert dieses Teilfelds excl. Datum als String.
     */
    public String getWertAlphanum() {
        return getWert();
    }

    /**
     * @return Wert dieses Teilfelds excl. Datum als String.
     */
    public Long getWertNumerisch() {
        String wertNum = getWert();
        String sbi = wertNum.charAt(wertNum.length() - 1)
                + wertNum.substring(0, wertNum.length() - 1);
        return Long.valueOf(sbi);
    }

    public boolean isLoeschanweisung() {
        return '0' == loeschM;
    }

    public String getKopf() {
        return this.kz + this.datS + this.feldS + this.loeschM + feldHerkS;
    }

    public String prettyPrint() {
        return origin.substring(0, 3) + "|" + origin.substring(3, 7) + "|" + origin.substring(7);
    }

}
