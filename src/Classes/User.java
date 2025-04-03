package Classes;
import Authentication.Authentication;

public class User extends Account {
    public User(String username, String password, String role) {
        super(username, password, role);
    }
    public void deleteAccount(Authentication auth) {
        auth.loadaccounts();

        boolean removed = Authentication.getAccounts().removeIf(account -> account.getusername().equals(this.getusername()));
        if (removed) {
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
    public boolean bookTickets(Event event, int numberOfTickets) {
        boolean success = event.bookTickets(numberOfTickets);
        if (success) {
            System.out.println("Successfully booked " + numberOfTickets + " ticket(s) for event: " + event.getEventName());
            return true;
        } else {
            System.out.println("Failed to book tickets. Not enough available seats.");
            return false;
        }
    }
}
