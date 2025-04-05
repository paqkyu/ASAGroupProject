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

    // Public constructor for creating new tickets
    public Ticket(String username, String eventName, int numberOfTickets) {
        this.username = username;
        this.eventName = eventName;
        this.numberOfTickets = numberOfTickets;
        tickets.add(this); // Add the ticket to the in-memory list
    }

    // Private constructor for loading tickets from the file
    private Ticket(String username, String eventName, int numberOfTickets, boolean skipSave) {
        this.username = username;
        this.eventName = eventName;
        this.numberOfTickets = numberOfTickets;
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
                    tickets.add(new Ticket(username, eventName, numberOfTickets, true)); // Use private constructor
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createAndSaveTicket(String username, String eventName, int numberOfTickets) {
        System.out.println("createAndSaveTicket called for: " + username + ", " + eventName + ", " + numberOfTickets);
        loadTickets(); // Load existing tickets from the file
    
        boolean ticketUpdated = false;
    
        // Check if the user already has a booking for the same event
        for (Ticket ticket : tickets) {
            if (ticket.getUsername().equals(username) && ticket.getEventName().equals(eventName)) {
                ticket.numberOfTickets += numberOfTickets; // Append the ticket count
                ticketUpdated = true;
                break;
            }
        }
    
        // If no existing booking was found, create a new ticket
        if (!ticketUpdated) {
            tickets.add(new Ticket(username, eventName, numberOfTickets));
        }
    
        // Save the updated tickets list to the file
        saveTickets();
    }
    public static void saveTickets() {
        System.out.println("saveTickets called");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TICKETS_FILE))) {
            for (Ticket ticket : tickets) {
                writer.write(ticket.getUsername() + "," + ticket.getEventName() + "," + ticket.getNumberOfTickets());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method for when user updates usernmame
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
        System.out.println("Confirmation: " + username + " booked " + numberOfTickets + " tickets for " + eventName);
    }
}