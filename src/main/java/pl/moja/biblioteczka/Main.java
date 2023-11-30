package pl.moja.biblioteczka;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.moja.biblioteczka.utils.FxmlUtils;
import pl.moja.biblioteczka.database.dbuitls.DbManager;
import pl.moja.biblioteczka.utils.FillDatabase;

import java.util.ResourceBundle;

public class Main extends Application {

    public static final String BORDER_PANE_MAIN_FXML = "/fxml/BorderPaneMain.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

//          Locale.setDefault(new Locale("pl"));

        Pane borderPane = FxmlUtils.fxmlLoader(BORDER_PANE_MAIN_FXML);
        Scene scene = new Scene(borderPane);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        ResourceBundle bundle = FxmlUtils.getResourceBundle();
        primaryStage.setTitle(bundle.getString("tittle.application"));
        primaryStage.show();

        DbManager.initDatabase();
//        FillDatabase.fillDatabase();
    }
}


