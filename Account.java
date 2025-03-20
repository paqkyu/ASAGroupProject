import java.io.Serializable;

public class Account implements Serializable {
    String Username;
    String Password;
    String Email;

    public Account(String Username, String Password, String Email) {
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
    }

    public String getUsername() {
        return Username;
    }
    public String getPassword() {
        return Password;
    }
    public String getEmail() {
        return Email;
    }
}