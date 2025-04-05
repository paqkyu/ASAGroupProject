package GUI;

import javax.swing.*;
import Controller.Controller;
import java.awt.*;

public class UserDashboardPanel extends JPanel {
    private JLabel userLabel;
    private JLabel feedbackLabel;
    private Controller controller;

    public void refreshUsername() {
        // Fetch the username from the logged-in account
        String username = (controller.getLoggedInAccount() != null) ? controller.getLoggedInAccount().getusername() : "Guest";
        userLabel.setText("Welcome, " + username);
        System.out.println("Logged-in username: " + username);
    }

    public UserDashboardPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout, BookingsPanel bookingsPanel) {
        this.controller = controller; // Store controller for later use
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        headerPanel.setBackground(new Color(255, 255, 255)); // White background

        // Initialize the username
        String username = (controller.getLoggedInAccount() != null) ? controller.getLoggedInAccount().getusername() : "Guest";
        userLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(userLabel, BorderLayout.CENTER);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Arial", Font.BOLD, 14));
        settingsButton.setBackground(new Color(0, 123, 255)); // Blue button
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setFocusPainted(false);
        settingsButton.setPreferredSize(new Dimension(150, 40));
        settingsButton.addActionListener(e -> cardLayout.show(cardPanel, "Settings"));

        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        settingsPanel.setBackground(new Color(255, 255, 255)); // White background
        settingsPanel.add(settingsButton);
        headerPanel.add(settingsPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.setBackground(new Color(255, 255, 255)); // White background

        JButton viewEventsButton = new JButton("View Events");
        viewEventsButton.setFont(new Font("Arial", Font.BOLD, 18));
        viewEventsButton.setBackground(new Color(40, 167, 69)); // Green button
        viewEventsButton.setForeground(Color.WHITE);
        viewEventsButton.setFocusPainted(false);
        viewEventsButton.setPreferredSize(new Dimension(200, 50));
        viewEventsButton.addActionListener(e -> cardLayout.show(cardPanel, "Events"));
        buttonPanel.add(viewEventsButton);

        JButton viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.setFont(new Font("Arial", Font.BOLD, 18));
        viewBookingsButton.setBackground(new Color(255, 193, 7)); // Yellow button
        viewBookingsButton.setForeground(Color.WHITE);
        viewBookingsButton.setFocusPainted(false);
        viewBookingsButton.setPreferredSize(new Dimension(200, 50));
        viewBookingsButton.addActionListener(e -> {
            bookingsPanel.refreshBookings();
            cardLayout.show(cardPanel, "MyBookings");
        });
        buttonPanel.add(viewBookingsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18));
        logoutButton.setBackground(new Color(220, 53, 69)); // Red button
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(200, 50));
        logoutButton.addActionListener(e -> {
            controller.logout();
            showSuccess("Logged out successfully.");
            cardLayout.show(cardPanel, "Login");
        });
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Feedback Label
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(feedbackLabel, BorderLayout.SOUTH);
    }

    private void showSuccess(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.GREEN);

        Timer timer = new Timer(3000, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
}