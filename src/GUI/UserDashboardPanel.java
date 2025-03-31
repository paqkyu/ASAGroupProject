package GUI;
import javax.swing.*;

import Controller.Controller;

import java.awt.*;

public class UserDashboardPanel extends JPanel {
    public UserDashboardPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        JLabel userLabel = new JLabel("User Dashboard", SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(userLabel, BorderLayout.NORTH);

        //Settings Button
        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> cardLayout.show(cardPanel, "Settings"));
        add(settingsButton, BorderLayout.CENTER);

        //Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        add(logoutButton, BorderLayout.SOUTH);


    }
}