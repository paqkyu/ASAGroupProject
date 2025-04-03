package Event;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class EventManager {
    private static final String EVENTS_FILE = "events.csv";
    private static List<Event> events = new ArrayList<>();

    static {
        loadEvents();
    }

    private static void loadEvents() {
        File file = new File(EVENTS_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 9) {
                    String eventName = data[0];
                    String eventDateTime = data[1];
                    String venueLocation = data[2];
                    String eventDescription = data[3];
                    double ticketPrice = Double.parseDouble(data[4]);
                    int capacity = Integer.parseInt(data[5]);
                    int availableSeats = Integer.parseInt(data[6]);
                    String specialInstructions = data[7];
                    String organizerUsername = data[8];
                    events.add(new Event(eventName, eventDateTime, venueLocation, eventDescription, 
                        ticketPrice, capacity, availableSeats, specialInstructions, organizerUsername));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEvent(Event event) {
        if (event != null) {
            events.add(event);
            saveEventsToFile();
        }
    }

    public void updateEvent(Event updatedEvent) {
        if (updatedEvent != null) {
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                if (event.getEventName().equals(updatedEvent.getEventName()) && 
                    event.getOrganizerUsername().equals(updatedEvent.getOrganizerUsername())) {
                    events.set(i, updatedEvent);
                    saveEventsToFile();
                    break;
                }
            }
        }
    }

    public void deleteEvent(Event event) {
        if (event != null) {
            events.removeIf(e -> e.getEventName().equals(event.getEventName()) && 
                                e.getOrganizerUsername().equals(event.getOrganizerUsername()));
            saveEventsToFile();
        }
    }

    private static void saveEventsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTS_FILE))) {
            for (Event event : events) {
                writer.write(event.getEventName() + "," + event.getEventDateTime() + "," + 
                    event.getVenueLocation() + "," + event.getEventDescription() + "," + 
                    event.getTicketPrice() + "," + event.getCapacity() + "," + 
                    event.getAvailableSeats() + "," + event.getSpecialInstructions() + "," + 
                    event.getOrganizerUsername());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public List<Event> getEventsByOrganizer(String organizerUsername) {
        return events.stream()
            .filter(e -> e.getOrganizerUsername().equals(organizerUsername))
            .collect(Collectors.toList());
    }
}
