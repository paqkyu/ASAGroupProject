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

        JButton viewEventsButton = new JButton("View Events");
        viewEventsButton.addActionListener(e -> cardLayout.show(cardPanel, "Events"));
        add(viewEventsButton, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Login");
        });
        add(logoutButton, BorderLayout.SOUTH);
    }
}