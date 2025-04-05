package GUI;

import Classes.Ticket; // Import the Ticket class
import javax.swing.*;
import java.awt.*;
import java.util.List;
import Controller.Controller;

public class BookingsPanel extends JPanel {
    private Controller controller;
    private JList<String> bookingsList;
    private DefaultListModel<String> listModel;
    private JLabel feedbackLabel;

    public BookingsPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        this.controller = controller; // Store the controller instance
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Header
        JLabel headerLabel = new JLabel("My Bookings", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Bookings List Panel
        listModel = new DefaultListModel<>();
        bookingsList = new JList<>(listModel);
        bookingsList.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(bookingsList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        add(scrollPane, BorderLayout.CENTER);

        // Button and Feedback Panel
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(255, 255, 255)); // White background

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(255, 255, 255)); // White background

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 53, 69)); // Red button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "UserDashboard"));
        buttonPanel.add(backButton);

        southPanel.add(buttonPanel, BorderLayout.CENTER);

        // Feedback Label
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        southPanel.add(feedbackLabel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    public void refreshBookings() {
        listModel.clear();
        if (controller.getLoggedInAccount() == null) {
            showError("You must be logged in to view bookings.");
            return;
        }

        String loggedInUsername = controller.getLoggedInAccount().getusername();
        List<Ticket> userTickets = Ticket.getTicketsByUsername(loggedInUsername); // Use Ticket class to get user tickets

        // Add tickets to the list model for display
        userTickets.forEach(ticket -> listModel.addElement(
            "Event: " + ticket.getEventName() + " | Tickets: " + ticket.getNumberOfTickets()
        ));

        if (userTickets.isEmpty()) {
            showError("No bookings found.");
        } else {
            showSuccess("Bookings loaded successfully.");
        }
    }

    private void showError(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.RED);

        Timer timer = new Timer(3000, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }

    private void showSuccess(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.GREEN);

        Timer timer = new Timer(3000, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
}