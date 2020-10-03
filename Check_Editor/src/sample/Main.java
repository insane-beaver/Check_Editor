package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.setStyle("-fx-background-radius: 5;" + "-fx-background-color:  #2E3348;");
        primaryStage.setTitle("Check Editor");
        primaryStage.setMaxHeight(350);
        primaryStage.setMaxWidth(600);
        Scene scene = new Scene(root, 500, 300, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.getStylesheets().add(Main.class.getResource("sample_style.css").toExternalForm());
        primaryStage.show();

        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(10);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
