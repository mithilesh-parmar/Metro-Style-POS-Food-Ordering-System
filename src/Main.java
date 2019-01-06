import HelperClasses.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

import javax.xml.crypto.Data;

public class Main extends Application {

	Database database = Database.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/OrderingWindow/ordering_window.fxml"));
//		Parent root = FXMLLoader.load(getClass().getResource("HomeWindow/home.fxml"));

        primaryStage.setTitle("Restaurant Management");
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(root,1200,800));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
     database.start();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
       database.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
