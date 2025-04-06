package Classes;

import Authentication.Authentication;
import Event.Event;

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
            // Use the createAndSaveTicket method instead of creating Ticket directly
            Ticket.createAndSaveTicket(this.getusername(), event.getEventName(), numberOfTickets);
            System.out.println("Confirmation: " + this.getusername() + " booked " + 
                             numberOfTickets + " tickets for " + event.getEventName());
            return true;
        }
        return false;
    }
}