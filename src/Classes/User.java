package Classes;

import Authentication.Authentication;
import Event.Event;
import Classes.Ticket;

public class User extends Account {
    public User(String username, String password, String role) {
        super(username, password, role);
    }

    public void deleteAccount(Authentication auth) {
        auth.getAccounts().remove(this);
        auth.accountToFile();
    }

    public boolean bookTickets(Event event, int numberOfTickets) {
        if (event.bookTickets(numberOfTickets)) {
            Ticket ticket = new Ticket(this.getusername(), event.getEventName(), numberOfTickets);
            ticket.sendConfirmation();
            return true;
        }
        return false;
    }
}