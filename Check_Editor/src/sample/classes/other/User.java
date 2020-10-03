package sample.classes.other;

public class User
{
    private String first_name;
    private String second_name;
    private String third_name;
    private String email;
    private String password;

    public User() { }

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public User(String first_name, String second_name, String third_name, String email, String password) {
        this.first_name = first_name;
        this.second_name = second_name;
        this.third_name = third_name;
        this.email = email;
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getThird_name() {
        return third_name;
    }

    public void setThird_name(String third_name) {
        this.third_name = third_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
