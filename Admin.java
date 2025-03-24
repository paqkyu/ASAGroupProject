public class Admin extends Account {
    public Admin(String username, String password, String role) {
        super(username, password, role);
    }
    
    public void deleteUser(Account user) {
        
        System.out.println("User " + user.getusername() + " has been deleted.");
    }

    public boolean promoteUser(String username) {
        Authentication auth = new Authentication();
        return auth.promoteUser(username);
    }
}