package dem2k;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;

/**
 * @author x004123 on 20.09.2017.
 */
public class DasTreeItemCellFactory {

    public static TreeCell<DasTreeItem> create(TreeView<DasTreeItem> fxTreeView) {

        TreeCell<DasTreeItem> cell = new TreeCell<DasTreeItem>() {
            @Override
            protected void updateItem(DasTreeItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    setText(item.getText());
                }
            }
        };

        cell.setOnMouseClicked(event -> fxTreeView.getSelectionModel().getSelectedItem()
                .setExpanded(!fxTreeView.getSelectionModel().getSelectedItem().isExpanded()));

        return cell;
    }
}
