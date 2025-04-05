package GUI;

import Classes.User;
import javax.swing.*;           // For JFrame, JPanel, etc.
import java.awt.CardLayout;    // For CardLayout
import Controller.Controller;  // For Controller class
import java.awt.Frame;

public class MainFrame {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Controller controller;

    public MainFrame() {
        controller = new Controller();
    }

    public void createAndShowGUI() {
        frame = new JFrame("Event Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        
        BookingsPanel bookingsPanel = new BookingsPanel(controller, cardPanel, cardLayout);
        UserDashboardPanel userDashboardPanel = new UserDashboardPanel(controller, cardPanel, cardLayout, bookingsPanel);
        OrganizerDashboardPanel organizerDashboardPanel = new OrganizerDashboardPanel(controller, cardPanel, cardLayout);
        // Add panels to the card layout
        cardPanel.add(new LoginPanel(controller, cardPanel, cardLayout, userDashboardPanel, organizerDashboardPanel), "Login");
        cardPanel.add(new RegisterPanel(controller, cardPanel, cardLayout), "Register");
        cardPanel.add(new DashboardPanel(controller, cardPanel, cardLayout), "Dashboard");
        cardPanel.add(new AdminDashboardPanel(controller, cardPanel, cardLayout), "AdminDashboard");
        cardPanel.add(userDashboardPanel, "UserDashboard"); // Add the shared UserDashboardPanel instance
        cardPanel.add(organizerDashboardPanel, "OrganizerDashboard");
        cardPanel.add(new SettingsPanel(controller, cardPanel, cardLayout), "Settings");
        cardPanel.add(new EventsPanel(controller, cardPanel, cardLayout), "Events");
        cardPanel.add(bookingsPanel, "MyBookings"); // Use the shared BookingsPanel instance
        cardPanel.add(new EventDetailsPanel(controller, cardPanel, cardLayout), "EventDetails");
        cardPanel.add(new RootDashboardPanel(controller, cardPanel, cardLayout), "RootDashboard");
        
        // Add the card panel to the frame
        frame.add(cardPanel);
        cardLayout.show(cardPanel, "Login");
        frame.setVisible(true);
    }

}