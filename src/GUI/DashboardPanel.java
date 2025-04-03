package GUI;

import javax.swing.*;
import Controller.Controller;
import java.awt.*;

public class DashboardPanel extends JPanel {
    public DashboardPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // Two rows, one column, spacing 10px

        JButton viewEventsButton = new JButton("View Events");
        viewEventsButton.addActionListener(e -> cardLayout.show(cardPanel, "Events"));
        buttonPanel.add(viewEventsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        buttonPanel.add(logoutButton);

        // Add button panel to center
        add(buttonPanel, BorderLayout.CENTER);
    }
}
