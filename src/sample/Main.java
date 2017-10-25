package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public Stage home;
    @Override
    public void start(Stage primaryStage) throws Exception{
        home = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        home.setTitle("Asilo San Antonio");
        home.setScene(new Scene(root, 600, 300));
        home.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
