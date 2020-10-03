package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.classes.other.Check;
import sample.classes.sql_clients.DatabaseHandler;
import javax.swing.*;
import java.io.File;
import java.net.URI;
import java.sql.SQLException;

public class Controller_add {

    public static boolean first=true;

    @FXML
    private TextField number;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField shop_name;

    @FXML
    private DatePicker date;

    @FXML
    private TextArea note;

    @FXML
    private Button open_image;

    @FXML
    private TextField image_name;

    @FXML
    private Button CloseButton;

    @FXML
    private Button done;

    private Image image;

    @FXML
    void initialize()
    {
        if(Controller_database.change_option)
        {
            Controller_database.change_option = false;

            Check check = Controller_database.selected_item;

            number.setText(String.valueOf(check.getNumber()));
            name.setText(String.valueOf(check.getName()));
            price.setText(String.valueOf(check.getPrice()));
            shop_name.setText(String.valueOf(check.getShop_Name()));
            note.setText(String.valueOf(check.getNote()));
        }

        CloseButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });

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

    public void Open_image(javafx.event.ActionEvent actionEvent)
    {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("Всі файли", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        File file = fileChooser.showOpenDialog(open_image.getScene().getWindow());
        if (file != null) {
            image_name.setText(file.getName());
            URI uri = file.toURI();
            image = new Image(uri.toString());
        }
    }

    public void Done(ActionEvent actionEvent)
    {
        if(!number.getText().equals("") && !name.getText().equals("") && !price.getText().equals("") && !shop_name.getText().equals("") &&
                date.getValue()!=null) {
            int Number = 0;
            Number = Integer.valueOf(number.getText());
            String Name = "";
            Name = name.getText();
            double Price = 0;
            Price = Double.valueOf(price.getText());
            String Shop_Name = "";
            Shop_Name = shop_name.getText();

            String[] Date = String.valueOf(date.getValue()).split("-", 3);
            int Date_Year = 0, Date_Month = 0, Date_Day = 0, i = 1;
            for (String a : Date) {
                if (i == 1) Date_Year = Integer.valueOf(a);
                else if (i == 2) Date_Month = Integer.valueOf(a);
                else if (i == 3) Date_Day = Integer.valueOf(a);
                i++;
            }

            String Note = "";
            Note = note.getText();

            Check check = new Check(Number, Name, Price, Shop_Name, Date_Day, Date_Month, Date_Year, Note, image);
            Controller_database.selected_item=check;

            if (first) {
                add(check, done);

                first = false;
            } else {
                for (Check item : Controller_database.arrayList) {
                    if (item.Search_by_number(Number) == false) {
                        add(check, done);
                    } else
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Товар з кодом " + Number + " вже існує",
                                "Увага", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Не всі поля заповенні або заповнені некоректно",
                    "Увага", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void movable(MouseEvent mouseEvent)
    {
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(10);
    }

    private void add(Check check, Button button)
    {
        Controller_database.arrayList.add(check);
        Controller_database.done=true;

        if(Controller.logged)
        {
            DatabaseHandler dbHandler = new DatabaseHandler();
            try {
                dbHandler.add_check(check, Controller.user);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}

