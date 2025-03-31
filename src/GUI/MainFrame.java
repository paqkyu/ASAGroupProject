package GUI;
import javax.swing.*;

import Controller.Controller;

import java.awt.*;


public class MainFrame {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Controller controller;
    
    public MainFrame() {
        controller = new Controller();
    }

    public void createAndShowGUI() {
        frame = new JFrame("Ticketing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(new LoginPanel(controller, cardPanel, cardLayout), "Login");
        cardPanel.add(new RegisterPanel(controller, cardPanel, cardLayout), "Register");
        cardPanel.add(new DashboardPanel(controller, cardPanel, cardLayout), "Dashboard");
        cardPanel.add(new AdminDashboardPanel(controller, cardPanel, cardLayout), "AdminDashboard");
        cardPanel.add(new UserDashboardPanel(controller, cardPanel, cardLayout), "UserDashboard");
        cardPanel.add(new OrganizerDashboardPanel(controller, cardPanel, cardLayout), "OrganizerDashboard");
    
        frame.add(cardPanel);
        frame.setVisible(true);
    }

}
