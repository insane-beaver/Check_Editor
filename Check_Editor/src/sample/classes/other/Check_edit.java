package sample.classes.other;

import javafx.scene.image.Image;
import java.io.Serializable;

public class Check_edit implements Serializable
{
    private int Number;
    private String Name;
    private double Price;
    private String Shop_Name;
    private int Date_Day;
    private int Date_Month;
    private int Date_Year;
    private String Note = "";
    private String Check_Photo = "";

    public Check_edit(Check check)
    {
        this.Number = check.getNumber();
        this.Name = check.getName();
        this.Price = check.getPrice();
        this.Shop_Name = check.getShop_Name();
        this.Date_Day = check.getDate_Day();
        this.Date_Month = check.getDate_Month();
        this.Date_Year = check.getDate_Year();
        this.Note = check.getNote();
        if(check.getCheck_Photo()!=null)
            this.Check_Photo = check.getCheck_Photo().getUrl();
    }

    public Check CheckEdit_TO_Check()
    {
        Check check=new Check();

        check.setNumber(this.Number);
        check.setName(this.Name);
        check.setPrice(this.Price);
        check.setShop_Name(this.Shop_Name);
        check.setDate_Day(this.Date_Day);
        check.setDate_Month(this.Date_Month);
        check.setDate_Year(this.Date_Year);
        check.setNote(this.Note);

        Image image = new Image(this.Check_Photo);
        check.setCheck_Photo(image);

        return check;
    }
}
