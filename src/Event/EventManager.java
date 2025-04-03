package Event;

import java.io.*;
import java.util.*;

public class EventManager {
    private static final String EVENTS_FILE = "events.csv";
    private static List<Event> events = new ArrayList<>();

    static {
        loadEvents();  // Load events from CSV file when class is loaded
    }

    // Load events from CSV file, including special instructions
    private static void loadEvents() {
        File file = new File(EVENTS_FILE);
        if (!file.exists()) {
            return; // No data to load
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {  // Updated to 8 to accommodate specialInstructions
                    String eventName = data[0];
                    String eventDateTime = data[1];
                    String venueLocation = data[2];
                    String eventDescription = data[3];
                    double ticketPrice = Double.parseDouble(data[4]);
                    int capacity = Integer.parseInt(data[5]);
                    int availableSeats = Integer.parseInt(data[6]);
                    String specialInstructions = data[7];  // Add special instructions

                    events.add(new Event(eventName, eventDateTime, venueLocation, eventDescription, ticketPrice, capacity, availableSeats, specialInstructions));  // Include specialInstructions when creating the Event
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add event to CSV file and list
    public static void addEvent(Event event) {
        events.add(event);
        saveEventsToFile();
    }

    // Save events to CSV file, including special instructions
    private static void saveEventsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTS_FILE))) {
            for (Event event : events) {
                // Write each event with special instructions to the CSV file
                writer.write(event.getEventName() + "," 
                           + event.getEventDateTime() + "," 
                           + event.getVenueLocation() + "," 
                           + event.getEventDescription() + "," 
                           + event.getTicketPrice() + "," 
                           + event.getCapacity() + "," 
                           + event.getAvailableSeats() + "," 
                           + event.getSpecialInstructions());  // Include special instructions here
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // List all events
    public static List<Event> getEvents() {
        return events;
    }
}
