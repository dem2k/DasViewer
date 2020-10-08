package dem2k;

import java.io.File;
import java.io.IOException;

import dem2k.model.DasTeilfeld;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;

/**
 * @author x004123 on 18.09.2017.
 */
public class DasViewerController {

	@FXML
	public TableView fxHeaderTable;

	@FXML
	public CheckMenuItem fxShowUnknown;

	@FXML
	private ListView<DasTeilfeld> fxListView;

	@FXML
	private TreeView<DasTreeItem> fxTreeView;

	@FXML
	private ProgressBar fxProgressBar;

	private DasTreeController treeContoller;
	private DasListController listController;


	public DasTreeController getTreeContoller() {
		if (treeContoller == null) {
			treeContoller = new DasTreeController(fxTreeView, fxProgressBar, fxShowUnknown, this);
		}
		return treeContoller;
	}

	public DasListController getListController() {
		if (listController == null) {
			listController = new DasListController(fxListView, fxHeaderTable, this);
		}
		return listController;
	}

	@FXML
	public void onShowUnknown(ActionEvent actionEvent) {
		getTreeContoller().fillTreeView();
	}

	@FXML
	public void onOpenFile(ActionEvent actionEvent) {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			try {
				getTreeContoller().loadFile(file.getAbsolutePath());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
