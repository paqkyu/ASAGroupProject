import java.awt.*;
import javax.swing.*;

public class TicketingSystemGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField usernameField, regUsernameField;
    private JPasswordField passwordField, regPasswordField;
    private JButton loginButton, registerButton, promoteButton;
    private JLabel errorLabel;
    private JPanel DashboardPanel;
    private Authentication Authentication; 
    private Account loggedInAccount; 

    public TicketingSystemGUI() {
        Authentication = new Authentication();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicketingSystemGUI gui = new TicketingSystemGUI();
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

        JButton logoutButton = new JButton("Logout:");
        logoutButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Login");
            errorLabel.setText("");
        });
        DashboardPanel.add(logoutButton, BorderLayout.SOUTH);

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

        JPanel promotePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        promotePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel promoteLabel = new JLabel("Promote User: ");
        JTextField userToPromoteField = new JTextField();
        promotePanel.add(promoteLabel);
        promotePanel.add(userToPromoteField);

        // Promote BUTTON
        promoteButton = new JButton ("Promote");
        promoteButton.addActionListener(e -> {
            String username = userToPromoteField.getText().trim();
            if (username.isEmpty()) {
                errorLabel.setText("Enter a username.");
                errorLabel.setForeground(Color.RED);
                return;
            }
            
            if (loggedInAccount instanceof Admin) {
                Admin admin = (Admin) loggedInAccount;
                boolean success = admin.promoteUser(username);

                if (success) {
                    errorLabel.setText("User has been promoted.");
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
        promotePanel.add(promoteButton);
        
        Panel.add(promotePanel, BorderLayout.CENTER);
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
