package Classes;
import Authentication.Authentication;

public class User extends Account {
    public User(String username, String password, String role) {
        super(username, password, role);
    }
    public void deleteAccount(Authentication auth) {
        auth.loadaccounts();

        boolean removed = Authentication.getAccounts().removeIf(account -> account.getusername().equals(this.getusername()));
        if (removed) {
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}