package GUI;

import javax.swing.*;
import Classes.Account;
import Classes.Admin;
import Controller.Controller;
import Authentication.Authentication;
import java.awt.*;

public class AdminDashboardPanel extends JPanel {
    private JLabel feedbackLabel;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    public AdminDashboardPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Header
        JLabel adminLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 24));
        adminLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(adminLabel, BorderLayout.NORTH);

        // User List Panel
        JPanel userManagementPanel = new JPanel(new BorderLayout());
        userManagementPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        userManagementPanel.setBackground(new Color(255, 255, 255)); // White background

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
        buttonsPanel.setBackground(new Color(255, 255, 255)); // White background

        // Promote to Organizer Button
        JButton promoteToOrganizerButton = new JButton("Set as Organizer");
        styleButton(promoteToOrganizerButton, new Color(0, 123, 255)); // Blue button
        promoteToOrganizerButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                showError("Please select a user to promote.");
                return;
            }
            selectedUser = selectedUser.split(" ")[0].trim();
            if (controller.getLoggedInAccount() instanceof Admin admin) {
                boolean success = admin.promoteToOrganizer(selectedUser);
                if (success) {
                    showSuccess("User " + selectedUser + " has been promoted to organizer.");
                    controller.getAuthentication().loadUsersIntoList(userListModel, controller.getLoggedInAccount());
                } else {
                    showError("Failed to promote user. User may not exist or is already an organizer.");
                }
            }
        });
        buttonsPanel.add(promoteToOrganizerButton);

        // Set as User Button
        JButton setAsUserButton = new JButton("Set as User");
        styleButton(setAsUserButton, new Color(40, 167, 69)); // Green button
        setAsUserButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                showError("Please select a user to demote.");
                return;
            }
            selectedUser = selectedUser.split(" ")[0].trim();
            if (controller.getLoggedInAccount() instanceof Admin admin) {
                boolean success = admin.demoteUser(selectedUser);
                if (success) {
                    showSuccess("User " + selectedUser + " has been demoted to user.");
                    controller.getAuthentication().loadUsersIntoList(userListModel, controller.getLoggedInAccount());
                } else {
                    showError("Failed to demote user. User may not exist or is already a user.");
                }
            }
        });
        buttonsPanel.add(setAsUserButton);

        // Delete User Button
        JButton deleteUserButton = new JButton("Delete User");
        styleButton(deleteUserButton, new Color(220, 53, 69)); // Red button
        deleteUserButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                showError("Please select a user to delete.");
                return;
            }
            selectedUser = selectedUser.split(" ")[0].trim();

            if (controller.getLoggedInAccount() instanceof Admin admin) {
                Authentication auth = controller.getAuthentication();
                Account accountToDelete = auth.getAccountByUsername(selectedUser);
                if (accountToDelete != null && !(accountToDelete instanceof Admin)) {
                    boolean success = admin.deleteUser(selectedUser, auth);
                    if (success) {
                        showSuccess("User " + selectedUser + " has been deleted.");
                        controller.getAuthentication().loadUsersIntoList(userListModel, controller.getLoggedInAccount());
                    } else {
                        showError("Failed to delete user. User may not exist.");
                    }
                } else {
                    showError("Admins cannot delete other Admin accounts.");
                }
            }
        });
        buttonsPanel.add(deleteUserButton);

        // Refresh Button
        JButton refreshButton = new JButton("Refresh");
        styleButton(refreshButton, new Color(108, 117, 125)); // Gray button
        refreshButton.addActionListener(e -> {
            controller.getAuthentication().loadUsersIntoList(userListModel, controller.getLoggedInAccount());
            showSuccess("User list refreshed.");
        });
        buttonsPanel.add(refreshButton);

        // Settings Button
        JButton settingsButton = new JButton("Settings");
        styleButton(settingsButton, new Color(40, 167, 69)); // Green button
        settingsButton.addActionListener(e -> cardLayout.show(cardPanel, "Settings"));
        buttonsPanel.add(settingsButton);

        userManagementPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(userManagementPanel, BorderLayout.CENTER);

        // Feedback Label
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(feedbackLabel, BorderLayout.SOUTH);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(220, 53, 69)); // Red button
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        add(logoutButton, BorderLayout.SOUTH);
    }

    // Helper method to style buttons
    private void styleButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    // Display error messages method
    private void showError(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.RED);
    }

    // Display success messages
    private void showSuccess(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.GREEN);
    }
}