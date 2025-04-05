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

public class Admin extends Account {
    public Admin(String username, String password, String role) {
        super(username, password, role);
    }

    public boolean deleteUser(String username, Authentication auth) {
    File file = new File("Accounts.csv");
    List<String> updatedLines = new ArrayList<>();
    boolean userFound = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                String fileUsername = parts[0].trim();
                String filePassword = parts[1].trim();
                String fileRole = parts[2].trim();

                if (fileUsername.equalsIgnoreCase(username)) {
                    userFound = true;
                    if (fileRole.equalsIgnoreCase("Admin")) {
                        System.out.println("Cannot delete an admin account.");
                        return false; // Do not proceed with deletion
                    }
                    System.out.println("User " + username + " has been deleted.");
                    continue; // Skip adding this user to the updated list
                }
            }
            updatedLines.add(line); // Add all other lines to the updated list
        }
    } catch (IOException e) {
        System.err.println("Error reading the accounts file: " + e.getMessage());
        return false;
    }

    if (!userFound) {
        System.out.println("User " + username + " not found.");
        return false;
    }

    // Write the updated list back to the file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (String updatedLine : updatedLines) {
            writer.write(updatedLine);
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Error writing to the accounts file: " + e.getMessage());
        return false;
    }

    return true;
}
    
    public boolean promoteToOrganizer(String username) {
        Authentication auth = new Authentication();
        Account account = auth.getAccountByUsername(username);
        if (account != null && !account.getrole().equals("Organizer")) {
            account.setrole("Organizer");
            auth.accountToFile();
            System.out.println("User " + username + " has been promoted to Organizer.");
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "User: " + this.getusername();
    }
}