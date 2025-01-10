package dem2k.model;

public enum DbNames {
    DatenEhefrau("20113"), NameAnschriftStpfl("10100"), PersDatenStpfl("20101"),

    Vertreter("10103"), ZeitraumabhaengigerVertreter("10503"), KontoverbindungNeu("20104"),

    KontoverbindungPersonensteuer("20105"), KontoverbindungBetriebssteuer("20106"),

    BetriebGrunddatenAllgemein("20510"), BetriebGrunddatenBetriebGroesse("20511"),

    KennbuchstabeStpfl("21001"), KennbuchstabeBetriebGewSt("22001"),

    KennbuchstabeBetriebEWBV("24001"), KennbuchstabeStpflVst("24501"),

    KennbuchstabeBetriebUst("25001"), KennbuchstabeSonstigeArbeitgeber("26001"),

    KennbuchstabeEigenheimzulage("27301"), KennbuchstabeGewinnermittlungsart("27701"),

    KennbuchstabeSonstigeKEFEFVAB("28001"), Sonderanweisungen("20690"),

    AblageInterneDaten("20135"), FEinVertreter("10603"), PersonendatenNEU("10102"),

    KontosperreKontoAuzugArchivierung("20590"), KennbuchstabeStpflNVB("21801"),

    UstGrundangaben("25592"), LstGrundangaben("26092"),

    UstGrundangabenANW("25515"), LstGrundangabenANW("26015"),

    Sachkonten("20592"), KennbuchstabeSteuerwertBetriebsvermoegen("24101"),

    DBKennbuchstabeSonstigeEinzelsteuer("28501")    ;

    private String stxt;

    DbNames(String stxt) {
        this.stxt = stxt;
    }

    public static DbNames getForName(SimpleStxt stxt) {
        for (DbNames name : values()) {
	        if (name.stxt.equals(stxt.format5stellig())) {
		        return name;
	        }
        }
        return null;
    }

}
