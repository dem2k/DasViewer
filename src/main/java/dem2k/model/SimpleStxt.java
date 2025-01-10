package dem2k.model;

import java.io.Serializable;

/**
 * Der Schlüsseltext identifiziert einen logischen Datenbereich aus dem DAS Kennzahlenplan.
 *
 * @author Dirk Schmidt (x003222)
 */

public class SimpleStxt implements Serializable, Comparable<SimpleStxt> {
    private static final long serialVersionUID = 1L;

    private char x1s;
    private int aufgS;
    private int sachBerS;

    private Long uSachBer;

    public SimpleStxt(char x1s, int aufgS, int sachBerS) {
        this.x1s = x1s;
        this.aufgS = aufgS;
        this.sachBerS = sachBerS;
    }

    private SimpleStxt(char x1s, int aufgS, int sachBerS, Long uSachBer) {
        this(x1s, aufgS, sachBerS);
        this.uSachBer = uSachBer;
    }

    public char getX1s() {
        return x1s;
    }

    public int getAufgS() {
        return aufgS;
    }

    public int getSachBerS() {
        return sachBerS;
    }

    /**
     * Untersachbereich nur manchmal vorhanden!
     */
    public Long getUSachBer() {
        return uSachBer;
    }


    public SimpleStxt untersachbereich(Long untersachbereich) {
        return new SimpleStxt(this.x1s, this.aufgS, this.sachBerS, untersachbereich);
    }


    public boolean isUSachBerSet() {
        return uSachBer != null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + aufgS;
        result = prime * result + sachBerS;
        result = prime * result + ((uSachBer == null) ? 0 : uSachBer.hashCode());
        result = prime * result + x1s;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SimpleStxt other = (SimpleStxt) obj;
        if (aufgS != other.aufgS) {
            return false;
        }
        if (sachBerS != other.sachBerS) {
            return false;
        }
        if (uSachBer == null) {
            if (other.uSachBer != null) {
                return false;
            }
        } else if (!uSachBer.equals(other.uSachBer)) {
            return false;
        }
        return x1s == other.x1s;
    }

    @Override
    public String toString() {
        return x1s + "." + aufgS + "." + sachBerS + getUsbAsString();
    }

    public String format5stellig() {
        return x1s + String.format("%02d", aufgS) + String.format("%02d", sachBerS);
    }

    public String getUsbAsString() {
        return uSachBer == null ? "" : ".usb" + String.format("%015d",uSachBer);
    }

    public String format5stelligMitPunkten() {
        return x1s + "." + String.format("%02d", aufgS) + "." + String.format("%02d", sachBerS);
    }


    @Override
    public int compareTo(SimpleStxt other) {
        return toString().compareTo(other.toString());
    }
}
