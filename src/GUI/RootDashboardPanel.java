package GUI;

import javax.swing.*;
import java.awt.*;
import Classes.Account;
import Classes.Root;
import Controller.Controller;
import Authentication.Authentication;

public class RootDashboardPanel extends JPanel {
    private JLabel feedbackLabel;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    public RootDashboardPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); 

        // Header
        JLabel titleLabel = new JLabel("Root Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        // User List Panel
        JPanel userManagementPanel = new JPanel(new BorderLayout());
        userManagementPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        userManagementPanel.setBackground(new Color(255, 255, 255)); 

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font("Arial", Font.PLAIN, 14));
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setPreferredSize(new Dimension(300, 200));
        userManagementPanel.add(userListScrollPane, BorderLayout.CENTER);

        controller.getAuthentication().loadUsersIntoList(userListModel, controller.getLoggedInAccount());

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(255, 255, 255)); 

        // Promote to Organizer Button
        JButton promoteToOrganizerButton = new JButton("Set as Organizer");
        promoteToOrganizerButton.setFont(new Font("Arial", Font.BOLD, 14));
        promoteToOrganizerButton.setBackground(new Color(0, 123, 255)); 
        promoteToOrganizerButton.setForeground(Color.WHITE);
        promoteToOrganizerButton.setFocusPainted(false);
        promoteToOrganizerButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                showError("Please select a user to promote.");
                return;
            }
            selectedUser = selectedUser.split(" ")[0].trim();
            if (controller.getLoggedInAccount() instanceof Root root) {
                boolean success = root.promoteToOrganizer(selectedUser);
                if (success) {
                    showSuccess("User " + selectedUser + " has been promoted to organizer.");
                    controller.getAuthentication().loadUsersIntoList(userListModel, controller.getLoggedInAccount());
                } else {
                    showError("Failed to promote user. User may not exist or is already an organizer.");
                }
            }
        });
        buttonsPanel.add(promoteToOrganizerButton);

        //Demote button
        JButton demoteUserButton = new JButton("Set to User");
        demoteUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        demoteUserButton.setBackground(new Color(255, 193, 7)); 
        demoteUserButton.setForeground(Color.WHITE);
        demoteUserButton.setFocusPainted(false);
        demoteUserButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                showError("Please select a user to demote.");
                return;
            }
            selectedUser = selectedUser.split(" ")[0].trim();
            if (controller.getLoggedInAccount() instanceof Root root) {
                boolean success = root.demoteUser(selectedUser);
                if (success) {
                    showSuccess("User " + selectedUser + " has been demoted to user.");
                    controller.getAuthentication().loadUsersIntoList(userListModel, controller.getLoggedInAccount());
                } else {
                    showError("Failed to demote user. User may not exist or is already a user.");
                }
            }
        });
        buttonsPanel.add(demoteUserButton);

        // Set as Admin Button
        JButton setAsAdminButton = new JButton("Set as Admin");
        setAsAdminButton.setFont(new Font("Arial", Font.BOLD, 14));
        setAsAdminButton.setBackground(new Color(40, 167, 69)); 
        setAsAdminButton.setForeground(Color.WHITE);
        setAsAdminButton.setFocusPainted(false);
        setAsAdminButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                showError("Please select a user to promote.");
                return;
            }
            selectedUser = selectedUser.split(" ")[0].trim();
            if (controller.getLoggedInAccount() instanceof Root root) {
                boolean success = root.promoteUser(selectedUser);
                if (success) {
                    showSuccess("User " + selectedUser + " has been promoted to admin.");
                    controller.getAuthentication().loadUsersIntoList(userListModel, controller.getLoggedInAccount());
                } else {
                    showError("Failed to promote user. User may not exist or is already an admin.");
                }
            }
        });
        buttonsPanel.add(setAsAdminButton);

        // Delete User Button
        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteUserButton.setBackground(new Color(220, 53, 69)); 
        deleteUserButton.setForeground(Color.WHITE);
        deleteUserButton.setFocusPainted(false);
        deleteUserButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                showError("Please select a user to delete.");
                return;
            }
            selectedUser = selectedUser.split(" ")[0].trim();

            if (controller.getLoggedInAccount() instanceof Root root) {
                Authentication auth = controller.getAuthentication();
                boolean success = root.deleteUser(selectedUser, auth);
                if (success) {
                    showSuccess("User " + selectedUser + " has been deleted.");
                    controller.getAuthentication().loadUsersIntoList(userListModel, controller.getLoggedInAccount());
                } else {
                    showError("Failed to delete user. User may not exist.");
                }
            }
        });
        buttonsPanel.add(deleteUserButton);

        // Refresh Button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(108, 117, 125)); 
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> {
            controller.getAuthentication().loadUsersIntoList(userListModel, controller.getLoggedInAccount());
            showSuccess("User list refreshed.");
        });
        buttonsPanel.add(refreshButton);

        userManagementPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(userManagementPanel, BorderLayout.CENTER);

        // Feedback Label
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(feedbackLabel, BorderLayout.SOUTH);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(220, 53, 69)); 
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        add(logoutButton, BorderLayout.SOUTH);
    }

    // Display error messages method
    private void showError(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.RED);
        Timer timer = new Timer(2000, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }

    // Display success messages
    private void showSuccess(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.GREEN);
        Timer timer = new Timer(2000, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
}