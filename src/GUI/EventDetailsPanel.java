package GUI;

import javax.swing.*;
import java.awt.*;
import Controller.Controller;
import Event.Event;

public class EventDetailsPanel extends JPanel {
    private JLabel eventNameLabel;
    private JLabel eventDateLabel;
    private JLabel eventTimeLabel;
    private JLabel eventLocationLabel;
    private JLabel eventDescriptionLabel;
    private JLabel eventAvailableSeatsLabel;
    private JLabel eventPriceLabel;
    private JButton backButton;

    public EventDetailsPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Header
        JLabel headerLabel = new JLabel("Event Details", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Initialize labels
        eventNameLabel = createDetailLabel("Event Name: ");
        eventDateLabel = createDetailLabel("Event Date: ");
        eventTimeLabel = createDetailLabel("Event Time: ");
        eventLocationLabel = createDetailLabel("Event Location: ");
        eventDescriptionLabel = createDetailLabel("Event Description: ");
        eventAvailableSeatsLabel = createDetailLabel("Available Seats: ");
        eventPriceLabel = createDetailLabel("Price: ");

        // Add labels to a details panel
        JPanel detailsPanel = new JPanel(new GridLayout(7, 1, 10, 10)); // Add spacing between rows
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Add padding
        detailsPanel.setBackground(Color.WHITE); // White background for details panel
        detailsPanel.add(eventNameLabel);
        detailsPanel.add(eventDateLabel);
        detailsPanel.add(eventTimeLabel);
        detailsPanel.add(eventLocationLabel);
        detailsPanel.add(eventDescriptionLabel);
        detailsPanel.add(eventAvailableSeatsLabel);
        detailsPanel.add(eventPriceLabel);

        add(detailsPanel, BorderLayout.CENTER);

        // Back button
        backButton = new JButton("Back to Events");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 53, 69)); // Red button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Events"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240)); // Match the panel background
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates a styled JLabel for displaying event details.
     *
     * @param text The initial text for the label.
     * @return A styled JLabel.
     */
    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding around the text
        return label;
    }

    /**
     * Updates the event details displayed in the panel.
     *
     * @param event The event whose details are to be displayed.
     */
    public void updateEventDetails(Event event) {
        eventNameLabel.setText("Event Name: " + event.getEventName());
        eventDateLabel.setText("Event Date: " + event.getEventDate());
        eventTimeLabel.setText("Event Time: " + event.getEventTime());
        eventLocationLabel.setText("Event Location: " + event.getVenueLocation());
        eventDescriptionLabel.setText("Event Description: " + event.getEventDescription());
        eventAvailableSeatsLabel.setText("Available Seats: " + event.getAvailableSeats());
        eventPriceLabel.setText("Price: $" + event.getTicketPrice());
    }
}