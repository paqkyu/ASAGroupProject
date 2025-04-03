package GUI;

import javax.swing.*;
import Controller.Controller;
import Event.Event;
import Classes.User; // Added missing import
import java.awt.*;

public class EventsPanel extends JPanel {
    private DefaultListModel<String> eventListModel;
    private JList<String> eventList;
    private JLabel feedbackLabel;
    private Controller controller;

    public EventsPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        this.controller = controller;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Available Events", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        eventListModel = new DefaultListModel<>();
        eventList = new JList<>(eventListModel);
        refreshEventList();
        add(new JScrollPane(eventList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton bookButton = new JButton("Book Tickets");
        bookButton.addActionListener(e -> {
            String selected = eventList.getSelectedValue();
            if (selected == null) {
                showError("Please select an event.");
                return;
            }
            if (controller.getLoggedInAccount() == null || !(controller.getLoggedInAccount() instanceof User)) {
                showError("Please log in as a user to book tickets.");
                return;
            }
            String[] parts = selected.split(" - ");
            Event event = controller.getEventManager().getEvents().stream()
                .filter(ev -> ev.getEventName().equals(parts[0])).findFirst().orElse(null);
            if (event != null) {
                String ticketsStr = JOptionPane.showInputDialog(this, "Number of tickets (1-" + event.getAvailableSeats() + "):");
                try {
                    int tickets = Integer.parseInt(ticketsStr);
                    if (tickets <= 0 || tickets > event.getAvailableSeats()) {
                        showError("Invalid number of tickets.");
                        return;
                    }
                    if (controller.bookTickets(event, tickets)) {
                        refreshEventList();
                        showSuccess("Booked " + tickets + " tickets for " + event.getEventName());
                    } else {
                        showError("Failed to book tickets.");
                    }
                } catch (NumberFormatException ex) {
                    showError("Please enter a valid number.");
                }
            }
        });
        buttonPanel.add(bookButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            String target = controller.getLoggedInAccount() != null && 
                           controller.getLoggedInAccount() instanceof User ? "UserDashboard" : "Login";
            cardLayout.show(cardPanel, target);
        });
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        add(feedbackLabel, BorderLayout.EAST);
    }

    private void refreshEventList() {
        eventListModel.clear();
        controller.getEventManager().getEvents()
            .forEach(e -> eventListModel.addElement(e.getEventName() + " - " + e.getAvailableSeats() + " seats available"));
    }

    private void showError(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.RED);
    }

    private void showSuccess(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.GREEN);
    }
}
