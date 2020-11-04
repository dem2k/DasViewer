package dem2k;

import dem2k.model.DbNames;
import dem2k.model.SimpleDasSatz;
import dem2k.model.SimpleStxt;

/**
 * @author x004123 on 18.09.2017.
 */
public class DasTreeItem {

    private String text;

    private SimpleDasSatz dasSatz;

    private boolean isUnknown = false;

    public DasTreeItem(String text) {
        this.text = text;
    }

    public DasTreeItem(SimpleDasSatz dasSatz) {
        this.dasSatz = dasSatz;
        SimpleStxt stxt = dasSatz.getSchluesseltext();

        DbNames dbName = DbNames.getForName(stxt);
        if (dbName == null) {
            text = "Unknown (" + stxt.toString() + ")";
        } else {
            text = dbName + " (" + stxt.toString() + ") ";
        }

        isUnknown = dbName == null;
    }

    public String getText() {
        return text;
    }

    public SimpleDasSatz getDasSatz() {
        return dasSatz;
    }

    public boolean isUnknown() {
        return isUnknown;
    }
}
