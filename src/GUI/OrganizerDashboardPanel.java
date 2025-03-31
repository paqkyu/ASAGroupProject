package GUI;
import javax.swing.*;

import Controller.Controller;

import java.awt.*;

public class OrganizerDashboardPanel extends JPanel {
    public OrganizerDashboardPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        JLabel organizerLabel = new JLabel("Organizer Dashboard", SwingConstants.CENTER);
        organizerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(organizerLabel, BorderLayout.NORTH);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        add(logoutButton, BorderLayout.SOUTH);
    }    
}
