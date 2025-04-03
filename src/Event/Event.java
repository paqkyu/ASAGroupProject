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

    public Event(String eventName, String eventDateTime, String venueLocation, String eventDescription, double ticketPrice, int capacity, int availableSeats, String specialInstructions) {
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.venueLocation = venueLocation;
        this.eventDescription = eventDescription;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.availableSeats = availableSeats;
        this.specialInstructions = specialInstructions;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getEventDateTime() {
        return eventDateTime;
    }
    public void setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public double getTicketPrice() {
        return ticketPrice;
    }
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
