package dem2k;

import dem2k.model.DasTeilfeld;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author x004123 on 19.09.2017.
 * wurde mit ToolTip ersetzt!
 */
@Deprecated //wurde mit ToolTip ersetzt!
public class KzBoxView {

    Stage stage = new Stage();
    AnchorPane pane = new AnchorPane();
    Scene scene = new Scene(pane, 305, 200);
    TableView<TblData> table = new TableView<>();


    public KzBoxView(Stage primaryStage) {
        stage.initOwner(primaryStage);
        stage.setScene(scene);

        TableColumn cName = new TableColumn("Name");
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cName.setMinWidth(150);
        TableColumn cWert = new TableColumn("Wert");
        cWert.setCellValueFactory(new PropertyValueFactory<>("wert"));
        cWert.setMinWidth(150);
        table.getColumns().addAll(cName, cWert);
        AnchorPane.setTopAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);
        AnchorPane.setLeftAnchor(table, 0.0);
        AnchorPane.setRightAnchor(table, 0.0);
        pane.getChildren().addAll(table);
    }

    public void update(DasTeilfeld tf) {
        table.getItems().clear();
        if (tf == null) {
            return;
        }
        table.getItems().add(new TblData("KZ", tf.getKz()));
        table.getItems().add(new TblData("Loeschanweisung", tf.isLoeschanweisung() ? "Ja" : "Nein"));
        table.getItems().add(new TblData("TF1von2", tf.isTeilfeld1vonZwei() ? "Ja" : "Nein"));
        table.getItems().add(new TblData("Herkunft", tf.getHerkunft() + ""));

        if (tf.isDatiert()) {
            table.getItems().add(new TblData("Datiert", tf.getDatum()));
        }

        if (tf.isNumerisch()) {
            table.getItems().add(new TblData("Wert (Numerisch)", tf.getWertNumerisch().toString()));
        }

        if (tf.isAlphanumerisch()) {
            table.getItems().add(new TblData("Wert (Alphanumerisch)", tf.getWertAlphanum()));
        }

        stage.show();
    }

    public class TblData {
        String name, wert;

        public TblData(String xx, String bb) {
            name = xx;
            wert = bb;
        }

        public String getName() {
            return name;
        }

        public String getWert() {
            return wert;
        }
    }

}
