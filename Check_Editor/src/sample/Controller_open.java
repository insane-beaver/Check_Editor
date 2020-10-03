package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.other.Check;

import java.awt.*;
import java.io.IOException;

public class Controller_open {

    @FXML
    private TextField number;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField shop_name;

    @FXML
    private TextField date;

    @FXML
    private TextArea note;

    @FXML
    private ImageView check_photo;

    @FXML
    private Button CloseButton;

    @FXML
    private Button open;

    public static Image check;

    @FXML
    void initialize()
    {
        CloseButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });

        Check item = Controller_database.selected_item;

        number.setText(Integer.toString(item.getNumber()));
        name.setText(item.getName());
        price.setText(Double.toString(item.getPrice()));
        shop_name.setText(item.getShop_Name());
        date.setText(item.getDate_Day() + "." + item.getDate_Month() + "." + item.getDate_Year());
        note.setText(item.getNote());
        check_photo.setImage(item.getCheck_Photo());

        check=item.getCheck_Photo();

        //EFFECTS
        MotionBlur motionBlur = new MotionBlur();
        motionBlur.setRadius(4);
        motionBlur.setAngle(1);
        CloseButton.setOnMouseEntered(mouseEvent -> {
            CloseButton.setEffect(motionBlur);
            CloseButton.setText("x");
        });
        CloseButton.setOnMouseExited(mouseEvent -> {
            CloseButton.setEffect(null);
            CloseButton.setText(null);
        });

    }


    public void Open(ActionEvent actionEvent)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/open_image.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        root.setStyle("-fx-background-radius: 5;"/* + "-fx-background-color:  #2E3348;"*/);
        Scene scene = new Scene(root, Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        scene.getStylesheets().add(Main.class.getResource("sample_style.css").toExternalForm());
        stage.showAndWait();
    }

    public void Open_image(MouseEvent mouseEvent)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/open_image.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        root.setStyle("-fx-background-radius: 5;"/* + "-fx-background-color:  #2E3348;"*/);
        Scene scene = new Scene(root, Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        scene.getStylesheets().add(Main.class.getResource("sample_style.css").toExternalForm());
        stage.showAndWait();
    }
}
