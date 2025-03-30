import java.awt.*;
import javax.swing.*;

public class TicketingSystem {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField usernameField, regUsernameField;
    private JPasswordField passwordField, regPasswordField;
    private JButton loginButton, registerButton, promoteButton, deleteButton, viewEventButton, promoteToOrganizerButton, demoteUser, refreshButton;
    private JLabel errorLabel;
    private JPanel DashboardPanel, buttonsPanel;
    private Authentication Authentication; 
    private Account loggedInAccount; 

    public TicketingSystem() {
        Authentication = new Authentication();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicketingSystem gui = new TicketingSystem();
            gui.createAndShowGUI();
        });
    }
    
    private void createAndShowGUI() {
        frame = new JFrame("Ticketing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createLoginPanel(), "Login");
        cardPanel.add(createRegisterPanel(), "Register");
        cardPanel.add(createDashboardPanel(), "Dashboard");
        cardPanel.add(createAdminDashboardPanel(), "AdminDashboard");
        cardPanel.add(createUserDashboardPanel(), "UserDashboard");

        frame.add(cardPanel);
        frame.setVisible(true);
    }
    // inital dashboard
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> performLogin());
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));
        panel.add(registerButton);

        errorLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(errorLabel);

        return panel;
    }
    // register dashboard
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Username:"));
        regUsernameField = new JTextField();
        panel.add(regUsernameField);

        panel.add(new JLabel("Password:"));
        regPasswordField = new JPasswordField();
        panel.add(regPasswordField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> performRegistration());
        panel.add(registerButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        panel.add(backButton);

        return panel;
    }

    //Screen after login for normal users
    private JPanel createDashboardPanel() {
        DashboardPanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        DashboardPanel.add(welcomeLabel, BorderLayout.NORTH);

        //View Events Button
        JButton viewEventsButton = new JButton("View Events");
        viewEventsButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Events");
        });
        DashboardPanel.add(viewEventsButton, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout:");
        logoutButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Login");
            errorLabel.setText("");
        });
        DashboardPanel.add(logoutButton, BorderLayout.SOUTH);

        return DashboardPanel;

    }
    //Panel for event organizer
    private JPanel createOrganizerPanel() {
        DashboardPanel = new JPanel(new BorderLayout());

        JLabel organizerLabel = new JLabel("Event Organizer Board", SwingConstants.CENTER);
        organizerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        DashboardPanel.add(organizerLabel, BorderLayout.NORTH);

        return DashboardPanel;
    }
    //Screen after login for admin users
    private JPanel createAdminDashboardPanel() {
        JPanel Panel = new JPanel(new BorderLayout());

        JLabel adminLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 24));
        Panel.add(adminLabel, BorderLayout.NORTH);

        JButton logoutButton = new JButton("Logout:");
        logoutButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Login");
            errorLabel.setText("");
        });

        Panel.add(logoutButton, BorderLayout.SOUTH);


        JPanel userManagementPanel = new JPanel(new BorderLayout());
        userManagementPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // User List
        DefaultListModel<String> userListModel = new DefaultListModel<>();
        JList<String> userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setPreferredSize(new Dimension(200, 100));
        userManagementPanel.add(userListScrollPane, BorderLayout.CENTER);

        Authentication auth = new Authentication();
        auth.loadUsersIntoList(userListModel);

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));


        // Promote TO ADMIN BUTTON
        promoteButton = new JButton ("Promote to Admin");
        promoteButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                errorLabel.setText("Select a User to promote to admin.");
                errorLabel.setForeground(Color.RED);
                return;
            }
            Account accountToPromote = auth.getAccountByUsername(selectedUser);
            if (accountToPromote == null) {
                errorLabel.setText("User not found.");
                errorLabel.setForeground(Color.RED);
                return;
            }
            if (loggedInAccount instanceof Admin) {
                Admin admin = (Admin) loggedInAccount;
                boolean success = admin.promoteUser(selectedUser);

                if (success) {
                    errorLabel.setText("User " + selectedUser + " has been promoted.");
                    errorLabel.setForeground(Color.GREEN);
                } else {
                    errorLabel.setText("User not found or is already an Admin.");
                    errorLabel.setForeground(Color.RED);
                }
            } else {
                errorLabel.setText("You do not have permission to promote users.");
                errorLabel.setForeground(Color.RED);
            }
        });
        buttonsPanel.add(promoteButton);

        //Promote to organizer button
        promoteToOrganizerButton = new JButton ("Promote to Organizer");
        promoteToOrganizerButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                errorLabel.setText("Select a User to promote to organizer.");
                errorLabel.setForeground(Color.RED);
                return;
            }
            
            Account accountToPromote = auth.getAccountByUsername(selectedUser);
            if (accountToPromote == null) {
                errorLabel.setText("User not found.");
                errorLabel.setForeground(Color.RED);
                return;
            }
            if (loggedInAccount instanceof Admin) {
                Admin admin = (Admin) loggedInAccount;
                boolean succcess = admin.promoteToOrganizer(selectedUser);

                if (succcess) {
                    errorLabel.setText("User " + selectedUser + " has been promoted to organizer.");
                    errorLabel.setForeground(Color.GREEN);
                    auth.loadUsersIntoList(userListModel);
                } else {
                    errorLabel.setText("User not found or is already an organizer.");
                    errorLabel.setForeground(Color.RED);
                }
            } else {
                errorLabel.setText("You do not have permission to promote users.");
                errorLabel.setForeground(Color.RED);
            }
    });
    buttonsPanel.add(promoteToOrganizerButton);

    // Delete User BUTTON
    deleteButton = new JButton("Delete User");
    deleteButton.addActionListener(e -> {
        String selectedUser = userList.getSelectedValue();
        if (selectedUser == null) {
            errorLabel.setText("Select a User to delete.");
            errorLabel.setForeground(Color.RED);
            return;
        }
        Account accountToDelete = auth.getAccountByUsername(selectedUser);
        if (accountToDelete == null) {
            errorLabel.setText("User not found.");
            errorLabel.setForeground(Color.RED);
            return;
        }
        if (loggedInAccount instanceof Admin) {
            Admin admin = (Admin) loggedInAccount;
            boolean success = admin.deleteUser(accountToDelete.getusername());

            if (success) {
                errorLabel.setText("User " + selectedUser + " has been deleted.");
                errorLabel.setForeground(Color.GREEN);
                auth.loadUsersIntoList(userListModel);
            } else {
                errorLabel.setText("User not found or cannot be deleted.");
                errorLabel.setForeground(Color.RED);
            }
        } else {
            errorLabel.setText("You do not have permission to delete users.");
            errorLabel.setForeground(Color.RED);
        }
    });
    buttonsPanel.add(deleteButton);

    //Refresh Button
    JButton refreshButton = new JButton("Refresh");
    refreshButton.addActionListener(e -> {
        auth.loadUsersIntoList(userListModel);
        errorLabel.setText("User list refreshed.");
        errorLabel.setForeground(Color.GREEN);
    });
    buttonsPanel.add(refreshButton);

        
        userManagementPanel.add(buttonsPanel, BorderLayout.SOUTH);
        Panel.add(userManagementPanel, BorderLayout.CENTER);
        return Panel;  
    } 
    private JPanel createUserDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel userLabel = new JLabel("User Dashboard", SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(userLabel, BorderLayout.NORTH);

        JButton logoutButton = new JButton("Logout:");
        logoutButton.addActionListener(e -> {
            this.cardLayout.show(this.cardPanel, "Login");
            this.errorLabel.setText("");
        });
        panel.add(logoutButton, BorderLayout.SOUTH);

        return panel;
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        Account account = Authentication.login(username, password);
        if (account != null) {
            errorLabel.setText("Login successful!");
            loggedInAccount = account;
            errorLabel.setForeground(Color.GREEN);

            if (loggedInAccount instanceof Admin) {
                cardLayout.show(cardPanel, "AdminDashboard");
            } else if (loggedInAccount instanceof User) {
                cardLayout.show(cardPanel, "UserDashboard");
            } else if (loggedInAccount instanceof Organizer) {
                cardLayout.show(cardPanel, "OrganizerDashboard");
            } else {
                cardLayout.show(cardPanel, "Dashboard");
            }
        } else {
            errorLabel.setText("Invalid username or password.");
            errorLabel.setForeground(Color.RED);
        }
    }

    private void performRegistration() {
        String username = regUsernameField.getText();
        String password = new String(regPasswordField.getPassword());

        if (Authentication.register(username, password)) {
            errorLabel.setText("Registration successful!");
            errorLabel.setForeground(Color.GREEN);
            cardLayout.show(cardPanel, "Login");
        } else {
            errorLabel.setText("Username already exists.");
            errorLabel.setForeground(Color.RED);
        }
    }
}
