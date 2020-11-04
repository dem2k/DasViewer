package dem2k;

import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author x004123 on 15.09.2017.
 */
public class DasViewer extends Application {

    public static void main(String[] args) {

//		if (args.length < 1) {
//			System.out.println("USAGE: java " + DasViewer.class.getName() + " <DasFile>");
//			System.exit(1);
//		}

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        List<String> args = getParameters().getRaw();

        FXMLLoader loader = new FXMLLoader(DasViewer.class.getResource("/layout.fxml"));
        Pane root = loader.load();
        DasViewerController mainController = loader.getController();

        stage.setTitle("DAS Viewer");
        stage.setScene(new Scene(root));
        stage.show();

        if (args != null && !args.isEmpty()) {
            mainController.getTreeContoller().loadFile(args.get(0));
        }

    }
}
