package GUI;

import javax.swing.*;
import Controller.Controller;
import Event.Event;
import Event.EventManager;

import java.awt.*;

public class OrganizerDashboardPanel extends JPanel {
    private DefaultListModel<String> eventListModel;
    private JList<String> eventList;
    private JLabel feedbackLabel;
    private Controller controller;
    private JLabel userLabel;
    
    public void refreshUsername() {
        String username = (controller.getLoggedInAccount() != null) ? controller.getLoggedInAccount().getusername() : "Guest";
        userLabel.setText("Welcome, " + username);
        System.out.println("Logged-in username: " + username);
    }
    
    public OrganizerDashboardPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        this.controller = controller;
        setLayout(new BorderLayout());
       

        JPanel headerPanel = new JPanel(new BorderLayout());
        String username = (controller.getLoggedInAccount() != null) ? controller.getLoggedInAccount().getusername() : "Guest";
        System.out.println("Logged-in username: " + username);

        userLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(userLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        eventListModel = new DefaultListModel<>();
        eventList = new JList<>(eventListModel);
        refreshEventList();
        add(new JScrollPane(eventList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Event Name:");
            if (name != null && !name.isEmpty()) {
                Event event = new Event(name, "2025-05-01", "OAKA", "Live event", 20.0, 100, 100, "None", 
                    controller.getLoggedInAccount().getusername());
                if (controller.addEvent(event)) {
                    refreshEventList();
                    showSuccess("Event " + name + " added.");
                } else {
                    showError("Failed to add event.");
                }
            }
        });
        buttonPanel.add(addEventButton);

        JButton editEventButton = new JButton("Edit Event");
        editEventButton.addActionListener(e -> {
            String selected = eventList.getSelectedValue();
            if (selected != null) {
                Event event = controller.getEventManager().getEvents().stream()
                    .filter(ev -> ev.getEventName().equals(selected.split(" - ")[0])).findFirst().orElse(null);
                if (event != null) {
                    String newPrice = JOptionPane.showInputDialog(this, "New Ticket Price:", event.getTicketPrice());
                    if (newPrice != null && !newPrice.isEmpty()) {
                        try {
                            event.setTicketPrice(Double.parseDouble(newPrice));
                            if (controller.editEvent(event)) {
                                refreshEventList();
                                showSuccess("Event updated.");
                            } else {
                                showError("Failed to edit event.");
                            }
                        } catch (NumberFormatException ex) {
                            showError("Invalid price.");
                        }
                    }
                }
            } else {
                showError("Select an event to edit.");
            }
        });
        buttonPanel.add(editEventButton);

        JButton deleteEventButton = new JButton("Delete Event");
        deleteEventButton.addActionListener(e -> {
            String selected = eventList.getSelectedValue();
            if (selected != null) {
                Event event = controller.getEventManager().getEvents().stream()
                    .filter(ev -> ev.getEventName().equals(selected.split(" - ")[0])).findFirst().orElse(null);
                if (event != null && controller.deleteEvent(event)) {
                    refreshEventList();
                    showSuccess("Event deleted.");
                } else {
                    showError("Failed to delete event.");
                }
            } else {
                showError("Select an event to delete.");
            }
        });
        buttonPanel.add(deleteEventButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            controller.logout();
            cardLayout.show(cardPanel, "Login");
        });
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        add(feedbackLabel, BorderLayout.EAST);
    }

    private void refreshEventList() {
        eventListModel.clear();
        if (controller.getLoggedInAccount() != null) {
            ((EventManager) controller.getEventManager()).getEventsByOrganizer(controller.getLoggedInAccount().getusername())
                .forEach(e -> eventListModel.addElement(e.getEventName() + " - " + e.getAvailableSeats() + " seats"));
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