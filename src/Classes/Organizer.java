package Classes;
import Event.Event;

public class Organizer extends Account {
    public Organizer(String username, String password, String role) {
        super(username, password, role);
    }
    public void deleteEvent(Event event) {
        System.out.println("Event " + event.getEventName() + " has been deleted.");
    }
    public void addEvent(Event event) {
        System.out.println("Event " + event.getEventName() + " has been added.");
    }
    public void editEvent(Event event) {
        System.out.println("Event " + event.getEventName() + " has been edited.");
    }
}