package Classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private String username;
    private String eventName;
    private int numberOfTickets;
    private static final String TICKETS_FILE = "Tickets.csv";
    private static List<Ticket> tickets = new ArrayList<>();

    // Public constructor - doesn't auto-add to list anymore
    public Ticket(String username, String eventName, int numberOfTickets) {
        this.username = username;
        this.eventName = eventName;
        this.numberOfTickets = numberOfTickets;
    }

    // Private constructor for loading
    private Ticket(String username, String eventName, int numberOfTickets, boolean skipSave) {
        this(username, eventName, numberOfTickets);
    }

    public String getUsername() { return username; }
    public String getEventName() { return eventName; }
    public int getNumberOfTickets() { return numberOfTickets; }

    public static void loadTickets() {
        tickets.clear();
        File file = new File(TICKETS_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String username = data[0];
                    String eventName = data[1];
                    int numberOfTickets = Integer.parseInt(data[2]);
                    tickets.add(new Ticket(username, eventName, numberOfTickets, true));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createAndSaveTicket(String username, String eventName, int numberOfTickets) {
        loadTickets(); // Load current state from file
    
        boolean found = false;
        // Check for existing ticket
        for (Ticket ticket : tickets) {
            if (ticket.getUsername().equals(username) && ticket.getEventName().equals(eventName)) {
                // Update existing ticket
                ticket.numberOfTickets = numberOfTickets; // SET the value, don't ADD
                found = true;
                break;
            }
        }
    
        if (!found) {
            // Add new ticket
            tickets.add(new Ticket(username, eventName, numberOfTickets));
        }
    
        saveTickets(); // Save updated list
    }

    public static void saveTickets() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TICKETS_FILE))) {
            for (Ticket ticket : tickets) {
                writer.write(ticket.getUsername() + "," + 
                            ticket.getEventName() + "," + 
                            ticket.getNumberOfTickets());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Other methods remain unchanged...
    public static void updateUsernameInTickets(String oldUsername, String newUsername) {
        loadTickets();
        for (Ticket ticket : tickets) {
            if (ticket.getUsername().equals(oldUsername)) {
                ticket.username = newUsername;
            }
        }
        saveTickets();
    }

    public static List<Ticket> getTicketsByUsername(String username) {
        loadTickets();
        List<Ticket> userTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getUsername().equals(username)) {
                userTickets.add(ticket);
            }
        }
        return userTickets;
    }

    public void sendConfirmation() {
        System.out.println("Confirmation: " + username + " booked " + 
                          numberOfTickets + " tickets for " + eventName);
    }
}