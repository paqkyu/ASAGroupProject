package Classes;

import Authentication.Authentication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Root extends Account {
    public Root(String username, String password, String role) {
        super(username, password, "Root");
    }
    public boolean promoteUser(String username) {
        Authentication auth = new Authentication();
        Account account = auth.getAccountByUsername(username);

        if (account != null && !account.getrole().equals("Admin")) {
            account.setrole("Admin");
            auth.accountToFile(); // Save changes to accounts.csv
            System.out.println("User " + username + " has been promoted to Organizer.");
            return true;
        }
        System.out.println("User " + username + " not found or already an Organizer.");
        return false;
    }


    public boolean promoteToOrganizer(String username) {
        Authentication Authentication = new Authentication();
        Account account = Authentication.getAccountByUsername(username);
        if (account != null && !account.getrole().equals("Organizer")) {
            account.setrole("Organizer");
            Authentication.accountToFile();
            System.out.println("User " + username + " has been promoted to Organizer.");
            return true;
        }
        return false;
    }
    public boolean demoteUser(String username) {
        Authentication auth = new Authentication();
        for (Account account : Authentication.getAccounts()) {
            if (account.getusername().equalsIgnoreCase(username)) {
                account.setrole("User");
                auth.accountToFile();
                System.out.println("User " + username + " has been demoted to User.");
                return true;
            }
        }
        System.out.println("User " + username + " not found or already a User.");
        return false;
    }


    
   public boolean deleteUser(String username, Authentication auth) {
    Iterator<Account> iterator = auth.getAccounts().iterator(); // Use an iterator to avoid concurrent modification
    while (iterator.hasNext()) {
        Account account = iterator.next();
        if (account.getusername().equalsIgnoreCase(username)) {
            // Remove the account safely
            iterator.remove();
            auth.accountToFile(); // Save changes to accounts.csv

            // Check and remove tickets for the user in tickets.csv
            removeUserTickets(username);

            System.out.println("User " + username + " and their tickets have been deleted.");
            return true;
        }
    }
    System.out.println("User " + username + " not found.");
    return false;
}

// Helper method to remove tickets for the user in tickets.csv
private void removeUserTickets(String username) {
    File ticketsFile = new File("tickets.csv");
    List<String> updatedLines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(ticketsFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Check if the line contains the username
            if (!line.startsWith(username + ",")) {
                updatedLines.add(line); // Keep lines that don't belong to the user
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading tickets.csv: " + e.getMessage());
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ticketsFile))) {
        for (String updatedLine : updatedLines) {
            writer.write(updatedLine);
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Error writing to tickets.csv: " + e.getMessage());
    }
}
}
