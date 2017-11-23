package dem2k.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DAS variabler Teil. Immutable
 *
 * @author Dirk Schmidt (x003222)
 */

public class SimpleDasTeil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int MAX_LEN_WERT = 19;

	protected static final char HERKUNFT = '0';

	public static final int TF_LEN = 26;

	protected String kz = "xxx";

	protected char datS;

	protected char feldS;

	protected char loeschM;

	protected char feldHerkS;

	protected String wert;


	private SimpleDasTeil teilfeld1;


	private SimpleDasTeil(String teil26) {
		if (teil26.length() != TF_LEN)
			throw new IllegalArgumentException("Teilfeld muss 26 Zeichen haben.");
		kz = teil26.substring(0, 3);
		datS = teil26.substring(3, 4).charAt(0);
		feldS = teil26.substring(4, 5).charAt(0);
		loeschM = teil26.substring(5, 6).charAt(0);
		feldHerkS = teil26.substring(6, 7).charAt(0);
		wert = teil26.substring(7, TF_LEN);
	}


	/**
	 * Parsed aus einem String eine List von DASTeil(en)
	 */
	public static List<SimpleDasTeil> parse(String varTeil) {
		if(varTeil.startsWith("E") && varTeil.contains("9447")){
			return Collections.emptyList();
			//return Collections.singletonList(new SimpleDasTeil("0000000__HINWEIS__________"));
		}

		if(varTeil.startsWith("000400000000000000000000000000000000090900010")){
			return Collections.emptyList();
		}

		if ((varTeil.length() % TF_LEN) != 0)
			throw new IllegalArgumentException(
					"Variabler Teil muss jeweils 26 Zeichen enthalten. Stimmt das Encoding? ISO-8859-15?: [" + varTeil + "]");

		List<SimpleDasTeil> result = new ArrayList<>();
		int anzVarTeile = varTeil.length() / TF_LEN;

		SimpleDasTeil prev = null;
		for (int i = 0; i < anzVarTeile; i++) {
			int pos = i * TF_LEN;
			String sTeil = varTeil.substring(pos, pos + TF_LEN);
			SimpleDasTeil teil = new SimpleDasTeil(sTeil);

			// Bem.: anhand von feldS kann die Zuordnung 1.TF und 2.TF nicht erfolgen, ist mehrdeutig.
			//       daher hier anhand der Reihenfolge in der sie im String auftreten:
			if (teil.isSameKzAndTF1vonZwei(prev)) {
				teil.teilfeld1 = prev;
			}
			result.add(teil);
			prev = teil;
		}
		return result;
	}

	public String toBinary() {
		StringBuilder sb = new StringBuilder();

		sb.append(kz).append(datS).append(feldS).append(loeschM)
				.append(feldHerkS).append(wert);
		if (sb.length() != TF_LEN)
			throw new IllegalStateException(
					"DAS Variabler Datenteil hat nicht 26 Zeichen");
		return sb.toString();
	}

	@Override
	public String toString() {
		return toBinary();
	}


	public String getKz() {
		return kz;
	}

	public boolean isKz009Untersachgebiet() {
		return "009".equals(kz);
	}

	public boolean isDatumSet() {
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

	public boolean isTeilfeld2vonZwei() {
		return teilfeld1 != null;
	}

	public SimpleDasTeil getTeilfeld1() {
		return teilfeld1;
	}


	private boolean isSameKzAndTF1vonZwei(SimpleDasTeil prev) {
		return prev != null && prev.getKz().equals(this.kz) && prev.isTeilfeld1vonZwei();
	}


	private String getWert() {
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

	public String getWertAlphanum() {
		return getWert();
	}

	public Long getWertNumerisch() {
		String wertNum = getWert();
		String sbi = wertNum.charAt(wertNum.length() - 1)
				+ wertNum.substring(0, wertNum.length() - 1);
		return new BigInteger(sbi).longValue();
	}


}
