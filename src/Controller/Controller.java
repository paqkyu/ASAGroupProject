package Controller;

import Authentication.Authentication;
import Classes.Account;
import Classes.User;
import Classes.Organizer;
import Classes.Ticket;
import Event.Event;
import Event.EventManager;

public class Controller {
    private Authentication authentication;
    private Account loggedInAccount;
    private EventManager eventManager;

    public Controller() {
        authentication = new Authentication();
        eventManager = new EventManager();
        this.eventManager = new EventManager();
    }

    public boolean login(String username, String password) {
        loggedInAccount = authentication.login(username, password);
        return loggedInAccount != null;
    }

    public boolean register(String username, String password) {
        return authentication.register(username, password);
    }

    public void logout() {
        loggedInAccount = null;
    }

    public Account getLoggedInAccount() {
        System.out.println("Logged in account: " + loggedInAccount);
        return loggedInAccount;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public boolean bookTickets(Event event, int numberOfTickets) {
        System.out.println("bookTickets called with event: " + event + ", numberOfTickets: " + numberOfTickets);
    if (loggedInAccount instanceof User user && event != null) {
        boolean success = user.bookTickets(event, numberOfTickets);
        if (success) {
            eventManager.updateEvent(event);

            // Write to the tickets file
            System.out.println("Writing to file in Controller.bookTickets");
            Ticket.createAndSaveTicket(user.getusername(), event.getEventName(), numberOfTickets);

            return true;
        }
    }
    return false;
}

    public boolean addEvent(Event event) {
        if (loggedInAccount instanceof Organizer organizer && event != null) {
            return organizer.addEvent(event, eventManager);
        }
        return false;
    }

    public boolean editEvent(Event event) {
        if (loggedInAccount instanceof Organizer organizer && event != null) {
            return organizer.editEvent(event, eventManager);
        }
        return false;
    }

    public boolean deleteEvent(Event event) {
        if (loggedInAccount instanceof Organizer organizer && event != null) {
            return organizer.deleteEvent(event, eventManager);
        }
        return false;
    }
}