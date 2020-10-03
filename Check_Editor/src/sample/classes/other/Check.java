package sample.classes.other;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Check implements Serializable
{
    private int Number;
    private String Name;
    private double Price;
    private String Shop_Name;
    private int Date_Day;
    private int Date_Month;
    private int Date_Year;
    private String Note;
    private Image Check_Photo;


    public Check() {
    }

    public Check(int number, String name, double price, String shop_Name, int date_Day, int date_Month, int date_Year, String note, Image check_Photo) {
        Number = number;
        Name = name;
        Price = price;
        Shop_Name = shop_Name;
        Date_Day = date_Day;
        Date_Month = date_Month;
        Date_Year = date_Year;
        Note = note;
        Check_Photo = check_Photo;
    }


    public int getNumber() {
        return Number;
    }
    public void setNumber(int number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }
    public void setPrice(double price) {
        Price = price;
    }

    public String getShop_Name() {
        return Shop_Name;
    }
    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }

    public int getDate_Day() {
        return Date_Day;
    }
    public void setDate_Day(int date_Day) {
        Date_Day = date_Day;
    }

    public int getDate_Month() {
        return Date_Month;
    }
    public void setDate_Month(int date_Month) {
        Date_Month = date_Month;
    }

    public int getDate_Year() {
        return Date_Year;
    }
    public void setDate_Year(int date_Year) {
        Date_Year = date_Year;
    }

    public String getNote() {
        return Note;
    }
    public void setNote(String note) {
        Note = note;
    }

    public Image getCheck_Photo() {
        return Check_Photo;
    }
    public void setCheck_Photo(Image check_Photo) {
        Check_Photo = check_Photo;
    }

    public boolean Search_by_number(int Number)
    {
        if(this.Number==Number) return true;
        else return false;
    }

}
