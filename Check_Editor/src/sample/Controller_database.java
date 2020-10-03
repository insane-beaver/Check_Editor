package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.MotionBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.other.Check;
import sample.classes.other.Check_edit;

import javax.swing.*;
import java.io.*;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Controller_database {

    public static ArrayList<Check> arrayList = new ArrayList<>();
    public static Check selected_item;
    public static boolean done=false;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private Set<String> shopsList = new HashSet<>();
    private MultipleSelectionModel<String> langsSelectionModel;
    private String selected_shop = "<Усі>";

    @FXML
    void cont(MouseEvent event)
    {
        if(done)
        {
            done=false;
            Refresh();
        }

        if(Controller.opened)
        {
            Controller.opened = false;
            Refresh();;
        }
    }


    @FXML
    private ListView<String> list;

    @FXML
    private ChoiceBox<String> shops_list;

    @FXML
    private Button show_choosen;

    @FXML
    private Button add_button;

    @FXML
    private Button open_button;

    @FXML
    private Button delete_button;

    @FXML
    private Button change_button;

    @FXML
    private Button CloseButton;

    @FXML
    private Button HideButton;

    @FXML
    private Label save;

    @FXML
    void initialize()
    {
        if(Controller.opened)
        {
            Refresh();
        }

        CloseButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });
        HideButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.setIconified(true);
        });

        ObservableList<String> shops = FXCollections.observableArrayList("<Усі>");
        shops_list.setItems(shops);
        shops_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if(t1==null) selected_shop = "<Усі>";
                selected_shop=t1;
            }
        });

        langsSelectionModel = list.getSelectionModel();
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue)
            {
                String[] strings = newValue.split(" - ", 2);
                int number = 0;
                for (String a : strings) {
                    number = Integer.valueOf(a);
                    break;
                }

                for (Check item : arrayList) {
                    if (item.Search_by_number(number)) selected_item = item;
                }
            }
        });


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

        HideButton.setOnMouseEntered(mouseEvent -> {
            HideButton.setEffect(motionBlur);
            HideButton.setText("-");
        });
        HideButton.setOnMouseExited(mouseEvent -> {
            HideButton.setEffect(null);
            HideButton.setText(null);
        });
    }

    private boolean first_time = true;
    public void movable(MouseEvent mouseEvent)
    {
        if(first_time)
        {
            //Refresh();
            first_time = false;
        }
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(10);

    }


    public void Add_new_element(javafx.event.ActionEvent actionEvent)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/add_component.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        root.setStyle("-fx-background-radius: 5;"/* + "-fx-background-color:  #2E3348;"*/);
        Scene scene = new Scene(root,Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.getStylesheets().add(Main.class.getResource("sample_style.css").toExternalForm());
        stage.showAndWait();
    }


    public void Open_element(ActionEvent actionEvent)
    {
        if(selected_item!=null)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/open_component.fxml"));

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
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.getStylesheets().add(Main.class.getResource("sample_style.css").toExternalForm());
            stage.showAndWait();
        }
        else {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Нічого не вибрано або база пуста",
                    "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ShowChoosed(ActionEvent actionEvent)
    {
        stringArrayList.clear();

        for (Check item : arrayList) {
            if (equals(item.getShop_Name(), selected_shop))
                stringArrayList.add(item.getNumber() + " - " + item.getName());
            else if (selected_shop.equals("<Усі>"))
                stringArrayList.add(item.getNumber() + " - " + item.getName());
        }

        ObservableList<String> checks = FXCollections.observableArrayList(stringArrayList);
        list.setItems(checks);
        langsSelectionModel = list.getSelectionModel();
    }

    private static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public void Delete_element(ActionEvent actionEvent)
    {
        if(selected_item!=null) {
            arrayList.remove(selected_item);
            System.out.println("Deleted " + selected_item.getNumber());
            Refresh();
        }
        else {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Нічого не вибрано або база пуста",
                    "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Refresh()
    {
        stringArrayList.clear();
        shopsList.clear();
        shopsList.add("<Усі>");

        for (Check item : arrayList) {
            stringArrayList.add(item.getNumber() + " - " + item.getName());
            shopsList.add(item.getShop_Name());
        }

        ObservableList<String> checks = FXCollections.observableArrayList(stringArrayList);
        list.setItems(checks);
        langsSelectionModel = list.getSelectionModel();

        ObservableList<String> shops = FXCollections.observableArrayList(shopsList);
        shops_list.setItems(shops);

        //selected_item=arrayList.get(arrayList.size()-1);
    }

    public void Save_in_file(MouseEvent mouseEvent)
    {
        ArrayList<Check_edit> arr = new ArrayList<>();

        for(Check item: arrayList)
        {
            Check_edit check_edit = new Check_edit(item);
            arr.add(check_edit);
        }

        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("База даних", "*.data"),
                new FileChooser.ExtensionFilter("Всі файли", "*.*"));

        File file = fileChooser.showSaveDialog(save.getScene().getWindow());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
        {
            oos.writeObject(arr);

            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Файл " + file.getName() + " було успішно збережено",
                    "Готово",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void refr_choise(MouseEvent mouseEvent)
    {
        if(first_time)
        {
            Refresh();
            first_time = false;
        }
    }

    public static boolean change_option=  false;
    public void Change_existed_element(ActionEvent actionEvent)
    {
        if(selected_item!=null) {
            arrayList.remove(selected_item);
            change_option = true;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/add_component.fxml"));

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
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.getStylesheets().add(Main.class.getResource("sample_style.css").toExternalForm());
            stage.showAndWait();
        }
        else {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Нічого не вибрано або база пуста",
                    "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
