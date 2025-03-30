import java.io.*;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

public class Authentication {
    private static final String Accounts_FILE = "Accounts.csv";
    private static ArrayList<Account> accounts;

    public Authentication() {
        accounts = new ArrayList<>();
        loadaccounts();
    }
    public static ArrayList<Account> getAccounts() {
        return accounts;
    }
    
    public void loadaccounts() {
        File file = new File(Accounts_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                
                if (data.length == 3) {
                    String username = data[0];
                    String password = data[1];
                    String role = data[2];
                    
                    if (role.equals("Admin")) {
                    accounts.add(new Admin(username, password, role));
                } else {
                    accounts.add(new User(username, password, role));
                }
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsersIntoList(DefaultListModel<String> userListModel) {
        userListModel.clear();
        for (Account account : accounts) {
            if(!(account instanceof Admin)) {
                userListModel.addElement(account.getusername());
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

    public static void accountToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Accounts_FILE))) {
            for (Account account : accounts) {
                writer.write(account.getusername() + "," + account.getpassword() + "," + account.getrole());
                writer.newLine();
            }
            System.out.println("Accounts have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
    } }

    public boolean register(String username, String password) {
        for (Account account : accounts) {
            if (account.getusername().equals(username)){
                return false; 
            } // Username not available
        }    
    //ALWAYS CREATES USER
    accounts.add(new User(username, password, "User"));

    accountToFile();
    return true; }

    public static Account login(String username, String password) {
        for (Account account : accounts) {
            if (account.getusername().equals(username) && account.getpassword().equals(password)) {
                return account;
            }
        }
        return null;
    }
} 
