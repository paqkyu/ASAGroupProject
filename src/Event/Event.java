package Event;

public class Event {
    private String eventName;
    private String eventDateTime;
    private String venueLocation;
    private String eventDescription;
    private double ticketPrice;
    private int capacity;
    private int availableSeats;
    private String specialInstructions;
    private String organizerUsername;

    public Event(String eventName, String eventDateTime, String venueLocation, String eventDescription, 
                 double ticketPrice, int capacity, int availableSeats, String specialInstructions, 
                 String organizerUsername) {
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.venueLocation = venueLocation;
        this.eventDescription = eventDescription;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.availableSeats = availableSeats;
        this.specialInstructions = specialInstructions;
        this.organizerUsername = organizerUsername;
    }

    public String getEventName() { return eventName; }
    public String getEventDateTime() { return eventDateTime; }
    public String getVenueLocation() { return venueLocation; }
    public String getEventDescription() { return eventDescription; }
    public double getTicketPrice() { return ticketPrice; }
    public int getCapacity() { return capacity; }
    public int getAvailableSeats() { return availableSeats; }
    public String getSpecialInstructions() { return specialInstructions; }
    public String getOrganizerUsername() { return organizerUsername; }

    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    public void setOrganizerUsername(String username) { this.organizerUsername = username; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    public boolean bookTickets(int numberOfTickets) {
        if (numberOfTickets > availableSeats || numberOfTickets <= 0) {
            return false;
        } else {
            availableSeats -= numberOfTickets;
            return true;
        }
    }
}