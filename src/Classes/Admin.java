package Classes;

import javax.naming.AuthenticationNotSupportedException;

import Authentication.Authentication;

public class Admin extends Account {
    public Admin(String username, String password, String role) {
        super(username, password, role);
    }
    
    public boolean deleteUser(String username) {
        Authentication auth = new Authentication();
        Account accountToDelete = auth.getAccountByUsername(username);
        if (accountToDelete == null) {
            System.out.println("User " + username + " not found.");
            return false;
        }
        if (accountToDelete instanceof Admin) {
            System.out.println("Admin " + username + " cannot be deleted.");
            return false;
        }
        Authentication.getAccounts().remove(accountToDelete);
        Authentication.accountToFile();
        System.out.println("User " + username + " has been deleted.");
        return true;
    }

    public boolean promoteUser(String username) {
        Authentication auth = new Authentication();
        for (Account account : Authentication.getAccounts()) {
            if (account instanceof User) {
                account.setrole("Admin");
                auth.accountToFile();
                System.out.println("User " + username + " has been promoted to Admin.");
            }
        }
        System.out.println("User " + username + " not found or already an Admin.");
        return false;
    }

    public boolean demoteUser(String username) {
        Authentication auth = new Authentication();
        for (Account account : Authentication.getAccounts()) {
            if (account instanceof Admin || account instanceof Organizer) {
                account.setrole("User");
                auth.accountToFile();
                System.out.println("User " + username + " has been demoted to User.");
                return true;
            }
        }
        System.out.println("User " + username + " not found or already a User.");
        return false;
    }
    public boolean promoteToOrganizer(String username) {
        Authentication auth = new Authentication();
        for (Account account : Authentication.getAccounts()) {
            if (account instanceof Admin || account instanceof User) {
                account.setrole("Organizer");
                auth.accountToFile();
                System.out.println("User " + username + " has been promoted to Organizer.");
                return true;
            }
        }
        System.out.println("User " + username + " not found or already an Organizer.");
        return false;
    }
}