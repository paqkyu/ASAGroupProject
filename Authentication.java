import java.io.*;
import java.util.ArrayList;

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
    public boolean promoteUser(String username) {
        for (Account account : accounts) {
            if (account.getusername().equals(username)) {
                System.out.println("Found user: " + username + "with role: " + account.getrole());
                if (!(account instanceof Admin)) {
                    account.setRole("Admin");
                    System.out.println("Updated Role to:" + account.getrole());
                    accountToFile();
                    System.out.println("User " + username + " has been promoted to Admin.");
                    return true;
                } else {
                    System.out.println("User is already an Admin.");
                    return false;
                }
            }
        } 
        System.out.println("User was not found or is already an Admin.");
        return false;
    } 
   
}