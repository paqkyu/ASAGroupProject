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

    public String getEventDateTime() {
        return eventDateTime;
    }

    public String getVenueLocation() {
        return venueLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    // method to book tickets
    public boolean bookTickets(int numberOfTickets) {
        if (numberOfTickets > availableSeats) {
            System.out.println("Not enough available seats.");
            return false;
        } else {
            availableSeats -= numberOfTickets;
            return true;
        }
    }
}
