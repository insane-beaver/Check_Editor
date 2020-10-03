package sample.classes.sql_clients;

import sample.classes.other.Check;
import sample.classes.other.User;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler extends Configs
{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useUnicode=true&serverTimezone=UTC";

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public Boolean signUpUser(User user) throws SQLException, ClassNotFoundException
    {
        ResultSet resultSet = checkUser(user);
        int counter =0;
        while(resultSet.next()) counter++;
        if(counter>=1)
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Користувач з такою поштою вже існує",
                    "Помилка під час реєстрації", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                    Const.USERS_FIRSTNAME + "," + Const.USERS_SECONDNAME + "," + Const.USERS_THIRDNAME + "," +
                    Const.USERS_EMAIL + "," + Const.USERS_PASSWORD + ")" + "VALUES(?,?,?,?,?)";

            PreparedStatement PS = getDbConnection().prepareStatement(insert);
            PS.setString(1, user.getFirst_name());
            PS.setString(2, user.getSecond_name());
            PS.setString(3, user.getThird_name());
            PS.setString(4, user.getEmail());
            PS.setString(5, user.getPassword());

            System.out.println(PS);
            PS.executeUpdate();
            return true;
        }
    }

    public ResultSet getUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_EMAIL + "=? AND " + Const.USERS_PASSWORD + "=?";

        PreparedStatement PS = getDbConnection().prepareStatement(select);
        PS.setString(1,user.getEmail());
        PS.setString(2,user.getPassword());

        resSet = PS.executeQuery();

        return resSet;
    }

    private ResultSet checkUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_EMAIL + "=?";

        PreparedStatement PS = getDbConnection().prepareStatement(select);
        PS.setString(1,user.getEmail());

        resSet = PS.executeQuery();

        return resSet;
    }

    public void add_check(Check check, User user) throws SQLException, ClassNotFoundException
    {
        String insert = "INSERT INTO " + "check_editor.`check`(" +
                Const.CHECK_NUMBER + "," + Const.CHECK_NAME + "," + Const.CHECK_PRICE + "," +
                Const.CHECK_SHOP_NAME + "," + Const.CHECK_DATE + "," + Const.CHECK_NOTE + "," +
                Const.CHECK_PHOTO + "," + Const.CHECK_EMAIL + ")" + "VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement PS = getDbConnection().prepareStatement(insert);

        PS.setInt(1, check.getNumber());
        PS.setString(2, check.getName());
        PS.setDouble(3, check.getPrice());
        PS.setString(4, check.getShop_Name());
        String date = check.getDate_Day() + "-" + check.getDate_Month() + "-" + check.getDate_Year();
        PS.setString(5, date);
        PS.setString(6, check.getNote());
        if(check.getCheck_Photo()==null)
            PS.setString(7, null);
        else
            PS.setString(7, check.getCheck_Photo().getUrl());
        PS.setString(8, user.getEmail());

        System.out.println(PS);
        PS.executeUpdate();
    }

    public ResultSet getCheck(User user) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + "check_editor.`check`" + " WHERE " +
                Const.CHECK_EMAIL + "=?";

        PreparedStatement PS = getDbConnection().prepareStatement(select);
        PS.setString(1,user.getEmail());

        System.out.println(PS);
        resSet = PS.executeQuery();

        return resSet;
    }

}
