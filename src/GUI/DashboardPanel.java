package GUI;

import javax.swing.*;
import Controller.Controller;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel feedbackLabel;

    public DashboardPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Guest", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton viewEventsButton = new JButton("View Events");
        viewEventsButton.addActionListener(e -> cardLayout.show(cardPanel, "Events"));
        buttonPanel.add(viewEventsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        add(feedbackLabel, BorderLayout.SOUTH);
    }
}