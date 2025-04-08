package GUI;

import javax.swing.*;

import Classes.Organizer;
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
        setBackground(new Color(240, 240, 240)); 

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 255, 255));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        String username = (controller.getLoggedInAccount() != null) ? controller.getLoggedInAccount().getusername() : "Guest";
        System.out.println("Logged-in username: " + username);

        userLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(userLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

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

        // Add Event Button
        JButton addEventButton = new JButton("Add Event");
        styleButton(addEventButton, new Color(40, 167, 69)); // Green button
        addEventButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Event Name:");
            String date = JOptionPane.showInputDialog(this, "Event Date (YYYY-MM-DD):");
            String time = JOptionPane.showInputDialog(this, "Event Time (HH:MM):");
            String[] descriptionOptions = {"Concert", "Sport", "Theater", "Other"};
            String description = (String) JOptionPane.showInputDialog(
                this,
                "Select event Description:",
                "Event Description",
                JOptionPane.QUESTION_MESSAGE,
                null,
                descriptionOptions,
                descriptionOptions[0]
            );
            if (name != null && date != null && time != null && !name.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
                Event event = new Event(name, date, time, "OAKA", description, 20.0, 100, 100, "None",
                        controller.getLoggedInAccount().getusername());
                if (controller.addEvent(event)) {
                    refreshEventList();
                    showSuccess("Event '" + name + "' added successfully.");
                } else {
                    showError("Event '" + name + "' already exists.");
                }
            } else {
                showError("All fields are required.");
            }
        });
        buttonPanel.add(addEventButton);

        // Edit Event Button
        JButton editEventButton = new JButton("Edit Event");
        styleButton(editEventButton, new Color(255, 193, 7)); 
        editEventButton.addActionListener(e -> {
            String selected = eventList.getSelectedValue();
            if (selected != null) {
                Event event = controller.getEventManager().getEvents().stream()
                        .filter(ev -> ev.getEventName().equals(selected.split(" - ")[0])).findFirst().orElse(null);
                if (event != null) {
                    // Options for editing
                    String[] options = {"Name", "Date", "Time", "Price"};
                    String choice = (String) JOptionPane.showInputDialog(
                            this,
                            "What would you like to edit?",
                            "Edit Event",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );
        
                    if (choice != null) {
                        switch (choice) {
                            case "Name":
                                String newName = JOptionPane.showInputDialog(this, "Enter new event name:", event.getEventName());
                                if (newName != null && !newName.isEmpty()) {
                                    event.setEventName(newName);
                                    if (controller.editEvent(event)) {
                                        refreshEventList();
                                        showSuccess("Event name updated.");
                                    } else {
                                        showError("Failed to update event name.");
                                    }
                                }
                                break;
        
                            case "Date":
                                String newDate = JOptionPane.showInputDialog(this, "Enter new event date (YYYY-MM-DD):", event.getEventDate());
                                if (newDate != null && !newDate.isEmpty()) {
                                    event.setEventDate(newDate);
                                    if (controller.editEvent(event)) {
                                        refreshEventList();
                                        showSuccess("Event date updated.");
                                    } else {
                                        showError("Failed to update event date.");
                                    }
                                }
                                break;
        
                            case "Time":
                                String newTime = JOptionPane.showInputDialog(this, "Enter new event time (HH:MM):", event.getEventTime());
                                if (newTime != null && !newTime.isEmpty()) {
                                    event.setEventTime(newTime);
                                    if (controller.editEvent(event)) {
                                        refreshEventList();
                                        showSuccess("Event time updated.");
                                    } else {
                                        showError("Failed to update event time.");
                                    }
                                }
                                break;
        
                            case "Price":
                                String newPrice = JOptionPane.showInputDialog(this, "Enter new ticket price:", event.getTicketPrice());
                                if (newPrice != null && !newPrice.isEmpty()) {
                                    try {
                                        event.setTicketPrice(Double.parseDouble(newPrice));
                                        if (controller.editEvent(event)) {
                                            refreshEventList();
                                            showSuccess("Event price updated.");
                                        } else {
                                            showError("Failed to update event price.");
                                        }
                                    } catch (NumberFormatException ex) {
                                        showError("Invalid price format.");
                                    }
                                }
                                break;
        
                            default:
                                showError("Invalid choice.");
                                break;
                        }
                    }
                }
            } else {
                showError("Select an event to edit.");
            }
        });
        buttonPanel.add(editEventButton);

        // Delete Event Button
        JButton deleteEventButton = new JButton("Delete Event");
        styleButton(deleteEventButton, new Color(220, 53, 69)); 
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

       // Logout Button
        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(108, 117, 125)); 
        logoutButton.addActionListener(e -> {
            controller.logout();
            cardLayout.show(cardPanel, "Login");
        });
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);

   
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(feedbackLabel, BorderLayout.NORTH);
    }

    public void refreshEventList() {
    eventListModel.clear();
    if (controller.getLoggedInAccount() instanceof Organizer organizer) {
        // Get events created by the logged-in organizer
        controller.getEventManager().getEventsByOrganizer(organizer.getusername())
                .forEach(e -> eventListModel.addElement(e.getEventName() + " - " + e.getEventDate() + " " + e.getEventTime() + 
                        " - " + e.getAvailableSeats() + " seats available - " + e.getEventDescription()));
    }
}

    private void styleButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
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