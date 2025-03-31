package Controller;
import Authentication.Authentication;
import Classes.Account;

public class Controller {
    private Authentication authentication;
    private Account loggedInAccount;

    public Controller() {
        authentication = new Authentication();
    }

    public boolean login(String username, String password) {
        loggedInAccount = authentication.login(username, password);
        return loggedInAccount != null;
    }
    public boolean register(String username, String password) {
        return authentication.register(username, password);
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }
    public Authentication getAuthentication() {
        return authentication;
    }
}
