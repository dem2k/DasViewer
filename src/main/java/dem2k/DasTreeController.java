package dem2k;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dem2k.model.SimpleDasSatz;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * @author x004123 on 15.09.2017.
 */
public class DasTreeController {

    private TreeView<DasTreeItem> fxTreeView;
    private ProgressBar fxProgressBar;
    private CheckMenuItem fxShowUnknown;
    private DasViewerController mainController;
    private Map<String, List<SimpleDasSatz>> stnrList;

    public DasTreeController(TreeView<DasTreeItem> fxTreeView, ProgressBar fxProgressBar, CheckMenuItem fxShowUnknown, DasViewerController dasViewerController) {
        this.fxTreeView = fxTreeView;
        this.fxProgressBar = fxProgressBar;
        this.fxShowUnknown = fxShowUnknown;
        this.mainController = dasViewerController;
        this.fxTreeView.setRoot(new TreeItem<>(new DasTreeItem("")));
        this.fxTreeView.getRoot().setExpanded(true);
        this.fxTreeView.setCellFactory(view -> DasTreeItemCellFactory.create(fxTreeView));
        this.fxTreeView.getSelectionModel().selectedItemProperty().addListener(this::onTreeItemSelected);
    }

    public void loadFile(String dasFile) throws IOException {
        fxTreeView.setRoot(new TreeItem<>(new DasTreeItem("Steuernummer")));
        Task task = new Task() {
            @Override
            protected void failed() {
                getException().printStackTrace();
            }

            @Override
            protected Object call() throws Exception {
                updateMessage("Loading...");

                AtomicLong done = new AtomicLong();
                long total = Files.size(Paths.get(dasFile));
                stnrList = getLinesFromFile(dasFile)
                        .parallel()
                        //.peek(ln-> System.out.print("."))
                        .peek(ln -> updateProgress(done.addAndGet(ln.length()), total))
                        .map(SimpleDasSatz::new)
                        .collect(Collectors.groupingBy(DasTreeController::clearstnr));
                return true;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                updateProgress(0, 0);
                fillTreeView();
            }
        };

        fxProgressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void fillTreeView() {
        if (stnrList == null || stnrList.isEmpty()) {
            return;
        }
        fxTreeView.getRoot().getChildren().clear();
        fxProgressBar.progressProperty().unbind();
        fxProgressBar.setProgress(0);
        ObservableList<TreeItem<DasTreeItem>> nodes = FXCollections.observableArrayList();
        new Thread(() -> {
            fxShowUnknown.setDisable(true);
            double workDone = 0;
            double workTotal = stnrList.size();
            for (Map.Entry<String, List<SimpleDasSatz>> e : stnrList.entrySet()) {
                nodes.add(createNode(e.getKey(), e.getValue(), fxShowUnknown.isSelected()));
                workDone++;
                fxProgressBar.setProgress(workDone / workTotal);
            }
            nodes.sort(Comparator.comparing(item -> item.getValue().getText()));
            expand(nodes);
            fxTreeView.getRoot().setExpanded(true);
            fxTreeView.getRoot().getChildren().addAll(nodes);
            fxProgressBar.setProgress(0);
            fxShowUnknown.setDisable(false);
        }).start();
    }

    private void expand(ObservableList<TreeItem<DasTreeItem>> nodes) {
        if (nodes.size() < 5) {
            nodes.forEach(node -> node.setExpanded(true));
        }
    }

    private Stream<String> getLinesFromFile(String dasFile) throws IOException {
        Path file = Paths.get(dasFile);
        if (file.getFileName().toString().startsWith("DB") && file.getFileName().toString().endsWith(".txt")) {
            String collect = Files.lines(file, Charset.forName("ISO-8859-15"))
                    .collect(Collectors.joining());
            return Stream.of(collect);
        }
        return Files.lines(file, Charset.forName("ISO-8859-15"));
    }

    private static String clearstnr(SimpleDasSatz satz) {
        String stnr = satz.getSteuernummerPretty();
        if (stnr.length() == 16 && stnr.endsWith("000")) {
            stnr = stnr.substring(0, 13);
        }
        return stnr;
    }

    private void onTreeItemSelected(ObservableValue observable, TreeItem<DasTreeItem> old, TreeItem<DasTreeItem> itemSelected) {
        if (itemSelected != null) {
            mainController.getListController().updateItems(itemSelected.getValue().getDasSatz());
        }
    }

    private TreeItem<DasTreeItem> createNode(String stnr, List<SimpleDasSatz> list, boolean withUnknown) {
        if (stnr.length() == 16 && stnr.endsWith("000")) {
            stnr = stnr.substring(0, 13);
        }

        TreeItem<DasTreeItem> item = new TreeItem<>(new DasTreeItem(stnr));
        for (SimpleDasSatz satz : list) {
            DasTreeItem subItem = new DasTreeItem(satz);
            if (withUnknown || !subItem.isUnknown()) {
                item.getChildren().add(new TreeItem<>(new DasTreeItem(satz)));
            }
        }
        return item;
    }

}
