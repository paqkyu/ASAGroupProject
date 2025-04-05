package Classes;

import Event.Event;
import Event.EventManager;

public class Organizer extends Account {
    public Organizer(String username, String password, String role) {
        super(username, password, role);
    }

    public boolean addEvent(Event event, EventManager manager) {
        if (event != null) {
            event.setOrganizerUsername(this.getusername());
            manager.addEvent(event);
            return true;
        }
        return false;
    }

    public boolean editEvent(Event event, EventManager manager) {
        if (event != null && event.getOrganizerUsername().equals(this.getusername())) {
            manager.updateEvent(event);
            return true;
        }
        return false;
    }

    public boolean deleteEvent(Event event, EventManager manager) {
        if (event != null && event.getOrganizerUsername().equals(this.getusername())) {
            manager.deleteEvent(event);
            return true;
        }
        return false;
    }
}