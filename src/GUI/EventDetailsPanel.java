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

        // Initialize labels
        eventNameLabel = new JLabel("Event Name: ");
        eventDateLabel = new JLabel("Event Date: ");
        eventTimeLabel = new JLabel("Event Time: ");
        eventLocationLabel = new JLabel("Event Location: ");
        eventDescriptionLabel = new JLabel("Event Description: ");
        eventAvailableSeatsLabel = new JLabel("Available Seats: ");
        eventPriceLabel = new JLabel("Price: ");

        // Add labels to a details panel
        JPanel detailsPanel = new JPanel(new GridLayout(7, 1, 10, 10)); // Add spacing between rows
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Event Details")); // Add a titled border
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
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Events"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the event details displayed in the panel.
     *
     * @param event The event whose details are to be displayed.
     */
    public void updateEventDetails(Event event) {
        eventNameLabel.setText("Event Name: " + event.getEventName());
        eventDateLabel.setText("Event Date: " + event.getEventDateTime());
        eventTimeLabel.setText("Event Time: " + event.getEventDateTime());
        eventLocationLabel.setText("Event Location: " + event.getVenueLocation());
        eventDescriptionLabel.setText("Event Description: " + event.getEventDescription());
        eventAvailableSeatsLabel.setText("Available Seats: " + event.getAvailableSeats());
        eventPriceLabel.setText("Price: $" + event.getTicketPrice());
    }
}