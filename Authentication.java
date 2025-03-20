import java.io.*;
import java.util.ArrayList;

public class Authentication {
    private static final String Accounts_FILE = "Accounts.csv";
    private ArrayList<Account> accounts;

    public Authentication() {
        accounts = new ArrayList<>();
        loadaccounts();
    }
    
    private void loadaccounts() {
        File file = new File(Accounts_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                accounts.add(new Account(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accountToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Accounts_FILE))) {
            for (Account account : accounts) {
                writer.write(account.getUsername() + "," + account.getPassword() + "," + account.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
    } }

    public boolean register(String username, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)){
                return false; } // Username not available 
    }
    
    accounts.add(new Account(username, password, ""));
    accountToFile();
    return true; }

    public boolean login(String username, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}