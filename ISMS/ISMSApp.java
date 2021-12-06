package ISMS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class ISMSApp extends Application {
    // Oracle standard JavaFX application
    // https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html
    public void start(Stage stage) throws Exception {
        Parent root= (Parent) FXMLLoader.load(getClass().getResource("ISMS.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Stock Management System");
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
