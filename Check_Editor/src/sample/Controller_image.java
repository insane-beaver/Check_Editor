package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.*;

public class Controller_image {

    @FXML
    private WebView image;

    @FXML
    void initialize()
    {
        WebEngine webEngine = image.getEngine();
        //String string = Controller_open.check.getUrl();

        Image image = new Image(Controller_open.check.getUrl());
        String string = image.getUrl();

        webEngine.load(string);
    }

}
