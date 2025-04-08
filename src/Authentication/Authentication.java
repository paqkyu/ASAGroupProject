package Authentication;

import java.io.*;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

import Classes.Account;
import Classes.Admin;
import Classes.User;
import Classes.Organizer;
import Classes.Root;

public class Authentication {
    private static final String ACCOUNTS_FILE = "Accounts.csv";
    private static ArrayList<Account> accounts;

    public Authentication() {
        accounts = new ArrayList<>();
        loadAccounts();
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    private void loadAccounts() {
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) {
            // Seed with default admin
            String defaultAdminPass = PasswordUtil.hashPassword("AdminPass123");
            accounts.add(new Admin("admin1", defaultAdminPass, "Admin"));
            accountToFile();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String username = data[0];
                    String password = data[1]; // already hashed
                    String role = data[2];
                    switch (role) {
                        case "Admin":
                            accounts.add(new Admin(username, password, role));
                            break;
                        case "Organizer":
                            accounts.add(new Organizer(username, password, role));
                            break;
                        default:
                            accounts.add(new User(username, password, role));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsersIntoList(DefaultListModel<String> userListModel, Account loggedInAccount) {
        userListModel.clear();
        for (Account account : accounts) {
            if (loggedInAccount instanceof Root) {
                userListModel.addElement(account.getusername() + " (" + account.getrole() + ")");
            } else if (loggedInAccount instanceof Admin) {
                if (!(account instanceof Admin) && !(account instanceof Root)) {
                    userListModel.addElement(account.getusername() + " (" + account.getrole() + ")");
                }
            }
        }
    }

    public Account getAccountByUsername(String username) {
        for (Account account : accounts) {
            if (account.getusername().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    public void accountToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account account : accounts) {
                writer.write(account.getusername() + "," + account.getpassword() + "," + account.getrole());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean register(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) return false;
        if (getAccountByUsername(username) != null) return false;

        String hashedPassword = PasswordUtil.hashPassword(password);
        accounts.add(new User(username, hashedPassword, "User"));
        accountToFile();
        return true;
    }

    public String getRootPass(String username) {
        String rootpassword = null;
        try (BufferedReader br = new BufferedReader(new FileReader("RootPass.csv"))) {
            rootpassword = br.readLine();
            if (username.equals("root")) {
                return rootpassword;
            }
        } catch (IOException e) {
            System.out.println("Error reading root password: " + e.getMessage());
        }
        return "";
    }

    public Account login(String username, String password) {
        String rootUser = "root";
        String rootPass = getRootPass(rootUser);

        if (username.equalsIgnoreCase(rootUser) && password.equals(rootPass)) {
            return new Root(rootUser, rootPass, "Root");
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        for (Account account : accounts) {
            if (account.getusername().equalsIgnoreCase(username) &&
                account.getpassword().equals(hashedPassword)) {
                return account;
            }
        }

        return null;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Account account = getAccountByUsername(username);
        String hashedOld = PasswordUtil.hashPassword(oldPassword);
        if (account != null && account.getpassword().equals(hashedOld)) {
            String hashedNew = PasswordUtil.hashPassword(newPassword);
            account.setpassword(hashedNew);
            accountToFile();
            return true;
        }
        return false;
    }

    public boolean changeUsername(String oldUsername, String newUsername) {
        Account account = getAccountByUsername(oldUsername);
        if (account != null && getAccountByUsername(newUsername) == null) {
            account.setusername(newUsername);
            accountToFile();
            return true;
        }
        return false;
    }
}
