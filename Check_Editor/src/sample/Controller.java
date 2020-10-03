package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.other.Check;
import sample.classes.other.Check_edit;
import sample.classes.other.User;
import sample.classes.sql_clients.DatabaseHandler;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    @FXML
    private AnchorPane AnchorPane1;

    @FXML
    private Button Log;

    @FXML
    private Button Reg;

    @FXML
    private Button Welcome;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Button CreateButton;

    @FXML
    private Button OpenButton;

    @FXML
    private Label text;

    @FXML
    private Button CloseButton;

    @FXML
    private Button HideButton;

    @FXML
    private AnchorPane LoginPane;

    @FXML
    private PasswordField Login_pas;

    @FXML
    private TextField Login_email;

    @FXML
    private Button Login_but;

    @FXML
    private Label text1;

    @FXML
    private AnchorPane RegPane;

    @FXML
    private PasswordField Reg_pas;

    @FXML
    private TextField Reg_email;

    @FXML
    private Button Reg_but;

    @FXML
    private TextField Reg_Firstname;

    @FXML
    private TextField Reg_Name;

    @FXML
    private TextField Reg_Lastname;

    @FXML
    private Label text11;

    public static User user;
    public static boolean logged = false;
    public static boolean opened = false;

    @FXML
    void initialize()
    {
        Login_but.setOnAction(actionEvent -> {
            String email = Login_email.getText().trim();
            String password = Login_pas.getText().trim();

            if(!email.equals("") && !password.equals(""))
            {
                try
                {
                    loginUser(email,password);
                } catch (SQLException e)
                {
                    e.printStackTrace();
                } catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
            else JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Не всі поля заповенні",
                    "Увага", JOptionPane.WARNING_MESSAGE);
        });

        Reg_but.setOnAction(actionEvent -> {
            String first_name = Reg_Name.getText().trim();
            String second_name = Reg_Firstname.getText().trim();
            String third_name = Reg_Lastname.getText().trim();
            String email = Reg_email.getText().trim();
            String password = Reg_pas.getText().trim();

            DatabaseHandler dbHandler = new DatabaseHandler();
            user = new User(first_name,second_name,third_name,email,password);

            if(!first_name.equals("") && !second_name.equals("") && !third_name.equals("") && !email.equals("") && !password.equals(""))
            {
                try {
                    Boolean success = dbHandler.signUpUser(user);
                    if(success==true)
                    {
                        Welcome.setText("Welcome "+user.getFirst_name());
                        logged =true;
                        Logined();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Не всі поля заповенні",
                    "Увага", JOptionPane.WARNING_MESSAGE);
        });

        Log.setOnAction(actionEvent -> {
            MainPane.setVisible(false);
            RegPane.setVisible(false);
            LoginPane.setVisible(true);

            Log.setVisible(false);
            Reg.setVisible(true);
            Welcome.setVisible(false);
        });

        Reg.setOnAction(actionEvent -> {
            MainPane.setVisible(false);
            RegPane.setVisible(true);
            LoginPane.setVisible(false);

            Log.setVisible(true);
            Reg.setVisible(false);
            Welcome.setVisible(false);
        });

        Welcome.setOnAction(actionEvent -> {
            MainPane.setVisible(false);
            RegPane.setVisible(false);
            LoginPane.setVisible(true);

            Log.setVisible(false);
            Reg.setVisible(true);
            Welcome.setVisible(false);

            logged=false;
        });

        text.setOnMouseClicked(mouseEvent -> {
            MainPane.setVisible(true);
            RegPane.setVisible(false);
            LoginPane.setVisible(false);

            Log.setVisible(true);
            Reg.setVisible(false);
            Welcome.setVisible(false);
        });

        CreateButton.setOnAction(actionEvent ->
        {
            Load(CreateButton);
        });

        OpenButton.setOnAction(actionEvent ->
        {
            String[] options = {"Відкрити онлайн базу даних","Відкрити локальну базу"};
            int x = JOptionPane.showOptionDialog(null, "Оберіть потрібну операцію:",
                    "",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            System.out.println(x);

            if(x==0 || x==1) opened=true;

            switch (x)
            {
                case 0:
                {
                    if(logged)
                    {
                        try
                        {
                            Get_all_checks(user);
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    break;
                }

                case 1: {
                    ArrayList<Check_edit> arr = new ArrayList<>();

                    final FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("База даних", "*.data"),
                            new FileChooser.ExtensionFilter("Всі файли", "*.*"));

                    File file = fileChooser.showOpenDialog(OpenButton.getScene().getWindow());
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        arr = (ArrayList<Check_edit>) ois.readObject();

                        for(Check_edit item:arr)
                        {
                            Check check = item.CheckEdit_TO_Check();
                            if(Check_Date(check)==true)
                                Controller_database.arrayList.add(check);
                        }

                        for (Check item : Controller_database.arrayList)
                            System.out.println(item.getNumber() + " " + item.getName());
                        Load(OpenButton);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                }

                default:
                {

                    break;
                }
            }
        });

        CloseButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });

        HideButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.setIconified(true);
        });


        //EFFECTS
        DropShadow shadow = new DropShadow();
        MotionBlur motionBlur = new MotionBlur();
        motionBlur.setRadius(4);
        motionBlur.setAngle(1);

        CreateButton.setOnMouseEntered(mouseEvent -> {
            CreateButton.setEffect(shadow);
        });
        CreateButton.setOnMouseExited(mouseEvent -> {
            CreateButton.setEffect(null);
        });

        OpenButton.setOnMouseEntered(mouseEvent -> {
            OpenButton.setEffect(shadow);
        });
        OpenButton.setOnMouseExited(mouseEvent -> {
            OpenButton.setEffect(null);
        });

        Login_but.setOnMouseEntered(mouseEvent -> {
            Login_but.setEffect(shadow);
        });
        Login_but.setOnMouseExited(mouseEvent -> {
            Login_but.setEffect(null);
        });

        Reg_but.setOnMouseEntered(mouseEvent -> {
            Reg_but.setEffect(shadow);
        });
        Reg_but.setOnMouseExited(mouseEvent -> {
            Reg_but.setEffect(null);
        });

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

    private void Load(Button button)
    {
        button.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/database.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        root.setStyle("-fx-background-radius: 5;" + "-fx-background-color:  #2E3348;");
        Scene scene = new Scene(root,Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.getStylesheets().add(Main.class.getResource("sample_style.css").toExternalForm());
        stage.showAndWait();
    }

    private void Logined() throws NullPointerException
    {
        Welcome.setVisible(true);
        Reg.setVisible(false);
        Log.setVisible(false);
        MainPane.setVisible(true);
        RegPane.setVisible(false);
        LoginPane.setVisible(false);
    }

    private void loginUser(String email, String password) throws SQLException, ClassNotFoundException, NullPointerException
    {
        DatabaseHandler dbHandler = new DatabaseHandler();

        user = new User(email,password);
        ResultSet resultSet = dbHandler.getUser(user);

        int counter =0;
        String name="";

        while(resultSet.next())
        {
            counter++;
            user.setFirst_name(resultSet.getString("first_name"));
            user.setSecond_name(resultSet.getString("second_name"));
            user.setThird_name(resultSet.getString("third_name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
        }

        if(counter>=1)
        {
            Logined();
            logged =true;
            Welcome.setText("Welcome "+user.getFirst_name());
        }
    }

    private void Get_all_checks(User user) throws SQLException, ClassNotFoundException
    {
        DatabaseHandler dbHandler = new DatabaseHandler();

        ResultSet resultSet = dbHandler.getCheck(user);
        int counter =0;

        while(resultSet.next())
        {
            Check check = new Check();
            counter++;

            check.setNumber(resultSet.getInt("number"));
            check.setName(resultSet.getString("name"));
            check.setPrice(resultSet.getDouble("price"));
            check.setShop_Name(resultSet.getString("shop_name"));

            String date = resultSet.getString("date");
            String[] Date = date.split("-", 3);
            int Date_Year = 0, Date_Month = 0, Date_Day = 0, i = 1;
            for (String a : Date) {
                if (i == 3) Date_Year = Integer.valueOf(a);
                else if (i == 2) Date_Month = Integer.valueOf(a);
                else if (i == 1) Date_Day = Integer.valueOf(a);
                i++;
            }
            check.setDate_Day(Date_Day);
            check.setDate_Month(Date_Month);
            check.setDate_Year(Date_Year);

            check.setNote(resultSet.getString("note"));
            if(resultSet.getString("photo")!=null)
            {
                Image image = new Image(resultSet.getString("photo"));
                check.setCheck_Photo(image);
            }
            System.out.println(resultSet.getString("photo"));

            if(Check_Date(check)==true)
                Controller_database.arrayList.add(check);
        }

        if(counter>=1)
        {
            Load(OpenButton);
        }
    }

    private boolean Check_Date(Check check)
    {
        LocalDate curentdate = LocalDate.now();
        System.out.println(curentdate);

        LocalDate date = LocalDate.of(check.getDate_Year(),check.getDate_Month(),check.getDate_Day());
        System.out.println(date);
        if(curentdate.isBefore(date)) return true;
        else
        {
            String[] options = {"Так","Ні"};
            int x = JOptionPane.showOptionDialog(null, "У тоавара " + check.getNumber() + " - " + check.getName() +
                            " закінчився гарантійний термін, Видалити товар?", "Попередження",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            System.out.println(x);

            if(x==1)
            {
                return true;
            }
            else {
                Controller_database.done = false;
                opened = false;
                return false;
            }
        }
    }
}

