package dem2k;


import java.util.Iterator;
import java.util.Map;

import dem2k.model.DasTeilfeld;
import dem2k.model.KopfRow;
import dem2k.model.SimpleDasSatz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author x004123 on 18.09.2017.
 */
public class DasListController {

    private TableView fxHeaderTable;
    private DasViewerController dasViewerController;
    private ListView<DasTeilfeld> fxListView;

    //private ObservableList<String> items = FXCollections.observableArrayList();

    public DasListController(ListView<DasTeilfeld> fxListView, TableView fxHeaderTable, DasViewerController dasViewerController) {
        this.fxListView = fxListView;
        this.fxHeaderTable = fxHeaderTable;
        this.dasViewerController = dasViewerController;

        this.fxHeaderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn colHeader1name = new TableColumn("Name");
        colHeader1name.setStyle("-fx-font-weight: bold");
        fxHeaderTable.getColumns().add(colHeader1name);
        colHeader1name.setCellValueFactory(new PropertyValueFactory<KopfRow, String>("header1name"));

        TableColumn colHeader1value = new TableColumn("Value");
        fxHeaderTable.getColumns().add(colHeader1value);
        colHeader1value.setCellValueFactory(new PropertyValueFactory<KopfRow, String>("header1value"));

        TableColumn colHeader2name = new TableColumn("Name");
        colHeader2name.setStyle("-fx-font-weight: bold");
        fxHeaderTable.getColumns().add(colHeader2name);
        colHeader2name.setCellValueFactory(new PropertyValueFactory<KopfRow, String>("header2name"));

        TableColumn colHeader2value = new TableColumn("Value");
        fxHeaderTable.getColumns().add(colHeader2value);
        colHeader2value.setCellValueFactory(new PropertyValueFactory<KopfRow, String>("header2value"));

        TableColumn colHeader3name = new TableColumn("Name");
        colHeader3name.setStyle("-fx-font-weight: bold");
        fxHeaderTable.getColumns().add(colHeader3name);
        colHeader3name.setCellValueFactory(new PropertyValueFactory<KopfRow, String>("header3name"));

        TableColumn colHeader3value = new TableColumn("Value");
        fxHeaderTable.getColumns().add(colHeader3value);
        colHeader3value.setCellValueFactory(new PropertyValueFactory<KopfRow, String>("header3value"));

        this.fxListView.setCellFactory(this::customCellFactory);
        //fxListView.getSelectionModel().selectedItemProperty().addListener(this::onItemSelected);
    }

    private ListCell<DasTeilfeld> customCellFactory(ListView<DasTeilfeld> view) {
        return new ListCell<DasTeilfeld>() {
            @Override
            protected void updateItem(DasTeilfeld item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null||empty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(item.prettyPrint());
                    setTooltip(createToolTip(item));
                }
            }
        };
    }

    private Tooltip createToolTip(DasTeilfeld tf) {
        String tip = "Kz: " + tf.getKz() + "\n" +
                "Loeschanweisung: " + (tf.isLoeschanweisung() ? "Ja" : "Nein") + "\n" +
                "TF1von2: " + (tf.isTeilfeld1vonZwei() ? "Ja" : "Nein") + "\n" +
                "Herkunft: " + tf.getHerkunft() + "\n" +
                "Datiert: " + (tf.isDatiert() ? tf.getDatum() : "Nein") + "\n";
        if (tf.isNumerisch()) {
            tip += "Wert (Numerisch): " + tf.getWertNumerisch();
        }
        if (tf.isAlphanumerisch()) {
            tip += "Wert (Alphanumerisch): " + tf.getWertAlphanum();
        }
        return new Tooltip(tip);
    }

    public void updateItems(SimpleDasSatz dasSatz) {
        if (dasSatz == null) {
            fxListView.getItems().clear();
            fxHeaderTable.getItems().clear();
        } else {
            fxListView.setItems(FXCollections.observableArrayList(dasSatz.asOriginPieces()));
            fxHeaderTable.setItems(makeRows(dasSatz));
        }
    }

    private ObservableList<KopfRow> makeRows(SimpleDasSatz dasSatz) {
        Map<String, String> kopfdaten = dasSatz.getKopfdaten();
        ObservableList<KopfRow> objects = FXCollections.observableArrayList();
        Iterator<Map.Entry<String, String>> iterator = kopfdaten.entrySet().stream().filter(this::isShowable).iterator();
        while (iterator.hasNext()) {
            KopfRow kr = new KopfRow();
            if (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                kr.setHeader1name(next.getKey());
                kr.setHeader1value(next.getValue());
            }

            if (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                kr.setHeader2name(next.getKey());
                kr.setHeader2value(next.getValue());
            }

            if (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                kr.setHeader3name(next.getKey());
                kr.setHeader3value(next.getValue());
            }

            objects.add(kr);
        }
        KopfRow satzart = new KopfRow();
        satzart.setHeader1name("satzArt");
        satzart.setHeader1value(dasSatz.getSatzArt().toString());
        objects.add(satzart);

        return objects;
    }

    private boolean isShowable(Map.Entry<String, String> stringStringEntry) {
        if ("origin".equals(stringStringEntry.getKey())) {
            return false;
        }
        if ("FILL".equals(stringStringEntry.getKey())) {
            return false;
        }
        return true;
    }

//	public DasViewerController getMainController() {
//		return dasViewerController;
//	}
}
