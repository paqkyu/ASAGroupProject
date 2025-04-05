package Classes;

public abstract class Account {
    private String username;
    private String password;
    private String role;

    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getusername() { return username; }
    public String getpassword() { return password; }
    public String getrole() { return role; }

    public void setusername(String username) { this.username = username; }
    public void setpassword(String password) { this.password = password; }
    public void setrole(String role) { this.role = role; }
}