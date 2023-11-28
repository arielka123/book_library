package pl.moja.biblioteczka;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.moja.biblioteczka.utils.FxmlUtils;

import java.util.ResourceBundle;

public class Main extends Application {

    public static final String BORDER_PANE_MAIN_FXML = "/fxml/BorderPaneMain.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

//          Locale.setDefault(new Locale("pl"));
//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/BorderPaneMain.fxml"));

        try {
            Pane borderPane = FxmlUtils.fxmlLoader(BORDER_PANE_MAIN_FXML);
            Scene scene = new Scene(borderPane);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            ResourceBundle bundle = FxmlUtils.getResourceBundle();
            primaryStage.setTitle(bundle.getString("tittle.application"));
            primaryStage.show();
        }catch(Exception e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("error");
            TextArea textArea = new TextArea(e.getMessage());
            errorAlert.getDialogPane().setContent(textArea);
            errorAlert.showAndWait();
        }

        String JDBC_DRIVER_HD = "jdbc:sqlite:database.db";
        ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_DRIVER_HD);
        connectionSource.close();
//        DbManager.initDatabase();
//        FillDatabase.fillDatabase();
    }
}
