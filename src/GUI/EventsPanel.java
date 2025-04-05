package GUI;

import Classes.Ticket;
import javax.swing.*;
import Controller.Controller;
import Event.Event;
import Event.EventManager;
import Classes.User;
import java.awt.*;

public class EventsPanel extends JPanel {
    private DefaultListModel<String> eventListModel;
    private JList<String> eventList;
    private JLabel feedbackLabel;
    private Controller controller;
    private EventDetailsPanel eventDetailsPanel;

    public EventsPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        this.controller = controller;
        eventDetailsPanel = new EventDetailsPanel(controller, cardPanel, cardLayout);
        cardPanel.add(eventDetailsPanel, "EventDetails");
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Header
        JLabel titleLabel = new JLabel("Available Events", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Event List Panel
        eventListModel = new DefaultListModel<>();
        eventList = new JList<>(eventListModel);
        eventList.setFont(new Font("Arial", Font.PLAIN, 14));
        refreshEventList();
        JScrollPane eventListScrollPane = new JScrollPane(eventList);
        eventListScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        add(eventListScrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(255, 255, 255)); // White background

        JButton bookButton = new JButton("Book Tickets");
        bookButton.setFont(new Font("Arial", Font.BOLD, 14));
        bookButton.setBackground(new Color(0, 123, 255)); // Blue button
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        if (bookButton.getActionListeners().length == 0) {
            bookButton.addActionListener(e -> {
            System.out.println("Book tickets button clicked");
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
                JPanel quantityPanel = new JPanel(new FlowLayout());
                JLabel quantityLabel = new JLabel("1");
                JButton minusButton = new JButton("-1");
                JButton plusButton = new JButton("+1");

                minusButton.addActionListener(ev -> {
                    int quantity = Integer.parseInt(quantityLabel.getText());
                    if (quantity > 1) {
                        quantityLabel.setText(String.valueOf(quantity - 1));
                    }
                });

                plusButton.addActionListener(ev -> {
                    int quantity = Integer.parseInt(quantityLabel.getText());
                    if (quantity < event.getAvailableSeats()) {
                        quantityLabel.setText(String.valueOf(quantity + 1));
                    }
                });

                quantityPanel.add(minusButton);
                quantityPanel.add(quantityLabel);
                quantityPanel.add(plusButton);

                int result = JOptionPane.showConfirmDialog(this, quantityPanel, "Select Quantity",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    int tickets = Integer.parseInt(quantityLabel.getText());
                    if (controller.bookTickets(event, tickets)) {
                        refreshEventList();
                        JOptionPane.showMessageDialog(this,
                                "Booked " + tickets + " tickets for " + event.getEventName(),
                                "Booking Successful",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        showError("Failed to book tickets.");
                    }
                }
            }
            });
            buttonPanel.add(bookButton);
        }

        JButton viewDetailsButton = new JButton("View Event Details");
        viewDetailsButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewDetailsButton.setBackground(new Color(255, 193, 7)); // Yellow button
        viewDetailsButton.setForeground(Color.WHITE);
        viewDetailsButton.setFocusPainted(false);
        viewDetailsButton.addActionListener(e -> {
            String selected = eventList.getSelectedValue();
            if (selected == null) {
                showError("Select an event");
            } else {
                String[] parts = selected.split(" - ");
                Event event = controller.getEventManager().getEvents().stream()
                    .filter(ev -> ev.getEventName().equals(parts[0])).findFirst().orElse(null);
                if (event != null) {
                    eventDetailsPanel.updateEventDetails(event);
                    cardLayout.show(cardPanel, "EventDetails");
                }
            }
        });
        buttonPanel.add(viewDetailsButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 53, 69)); // Red button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            String target = controller.getLoggedInAccount() != null &&
                    controller.getLoggedInAccount() instanceof User ? "UserDashboard" : "Login";
            cardLayout.show(cardPanel, target);
        });
        buttonPanel.add(backButton);

        // Add button panel to the SOUTH region
        add(buttonPanel, BorderLayout.SOUTH);

        // Feedback Label
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(feedbackLabel, BorderLayout.NORTH); // Moved to NORTH to avoid overwriting
    }

    private void refreshEventList() {
        eventListModel.clear();
        ((EventManager) controller.getEventManager()).getEvents()
            .forEach(e -> eventListModel.addElement(e.getEventName() + " - " + e.getAvailableSeats() + " seats available"));
    }

    private void showError(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.RED);
        Timer timer = new Timer(2000, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }

    private void showSuccess(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.GREEN);
        Timer timer = new Timer(2000, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
}