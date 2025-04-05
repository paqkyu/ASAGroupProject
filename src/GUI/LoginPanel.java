package GUI;

import javax.swing.*;
import Controller.Controller;
import Classes.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel feedbackLabel;

    public LoginPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout, UserDashboardPanel userDashboardPanel, OrganizerDashboardPanel organizerDashboardPanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Header
        JLabel headerLabel = new JLabel("Welcome to the ASA Ticketing System", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(headerLabel, BorderLayout.NORTH);

       
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(new Color(255, 255, 255)); // White background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:", SwingConstants.RIGHT), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(usernameField, gbc);

        // Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:", SwingConstants.RIGHT), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordField, gbc);

        // buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Align buttons in a single row
        buttonPanel.setBackground(new Color(255, 255, 255)); // White background

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 123, 255)); // Blue button
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim().toLowerCase();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                showError("Username and password cannot be empty.");
                return;
            }
            if (controller.login(username, password)) {
                Account account = controller.getLoggedInAccount();
                if (account instanceof Admin) {
                    cardLayout.show(cardPanel, "AdminDashboard");
                } else if (account instanceof Root) {
                    cardLayout.show(cardPanel, "RootDashboard");
                } else if (account instanceof Organizer) {
                    cardLayout.show(cardPanel, "OrganizerDashboard");
                } else if (account instanceof User) {
                    userDashboardPanel.refreshUsername(); // refreshes username so that it displays properly
                    cardLayout.show(cardPanel, "UserDashboard");
                } else {
                    cardLayout.show(cardPanel, "Dashboard");
                }
                usernameField.setText("");
                passwordField.setText("");
            } else {
                showError("Invalid username or password.");
            }
        });
        buttonPanel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(40, 167, 69)); 
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));
        buttonPanel.add(registerButton);

        JButton viewEventsButton = new JButton("View Events");
        viewEventsButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewEventsButton.setBackground(new Color(255, 193, 7)); 
        viewEventsButton.setForeground(Color.WHITE);
        viewEventsButton.setFocusPainted(false);
        viewEventsButton.addActionListener(e -> {
            
            cardLayout.show(cardPanel, "Events");
        });
        buttonPanel.add(viewEventsButton);

        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; 
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Feedback Label
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(feedbackLabel, BorderLayout.SOUTH);
    }

    private void showError(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.RED);

        Timer timer = new Timer(2000, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
}