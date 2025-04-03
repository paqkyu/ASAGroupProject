package GUI;
import javax.swing.*;

import Controller.Controller;

import java.awt.*;
import java.util.concurrent.Flow;

public class UserDashboardPanel extends JPanel {
    public UserDashboardPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel userLabel = new JLabel("User Dashboard", SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(userLabel, BorderLayout.CENTER);

        //Settings Button
        JButton settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(50,30));
        settingsButton.addActionListener(e -> cardLayout.show(cardPanel, "Settings"));
        

        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        settingsPanel.add(settingsButton);
        headerPanel.add(settingsPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton viewEventsButton = new JButton("View Events");
        viewEventsButton.setFont(new Font("Arial", Font.BOLD, 18));
        viewEventsButton.setPreferredSize(new Dimension(200, 50));
        viewEventsButton.addActionListener(e -> cardLayout.show(cardPanel, "Events"));
        buttonPanel.add(viewEventsButton);

        //Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(150, 40));
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);



    }
}