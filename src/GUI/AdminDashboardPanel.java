package GUI;
import javax.swing.*;

import Classes.Admin;
import Classes.Root;
import Controller.Controller;

import java.awt.*;

public class AdminDashboardPanel extends JPanel {
    private JLabel errorLabel;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    
    
    public AdminDashboardPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        JLabel adminLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(adminLabel, BorderLayout.NORTH);

        JPanel userManagementPanel = new JPanel(new BorderLayout());
        userManagementPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setPreferredSize(new Dimension(200, 100));
        userManagementPanel.add(userListScrollPane, BorderLayout.CENTER);

        controller.getAuthentication().loadUsersIntoList(userListModel);
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton promoteToAdminButton = new JButton("Set as Admin");
        promoteToAdminButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if(selectedUser == null) {
                showError("Please select a user to promote.");
                return;
            }
            if (controller.getLoggedInAccount() instanceof Root root) {
                boolean success = root.promoteUser(selectedUser);
                if (!success) {
                    showError("Failed to promote user. User may not exist or is already an admin.");
                }
            }
        });
        buttonsPanel.add(promoteToAdminButton);

        JButton promoteToOrganizerButton = new JButton("Set as Organizer");
        promoteToOrganizerButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if(selectedUser == null) {
                showError("Please select a user to promote.");
                return;
            }
            if (controller.getLoggedInAccount() instanceof Admin admin) {
                boolean success = admin.promoteToOrganizer(selectedUser);
                if (success) {
                    showSuccess("User " + selectedUser + " has been promoted to organizer.");
                    controller.getAuthentication().loadUsersIntoList(userListModel);
                } else {
                    showError("Failed to promote user. User may not exist or is already an organizer.");
                }
            } else {
                showError("You are not authorized to perform this action.");
            }
        });
        buttonsPanel.add(promoteToOrganizerButton);

        //Delete User Button
        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if(selectedUser == null) {
                showError("Please select a user to delete.");
                return;
            }
            if (controller.getLoggedInAccount() instanceof Admin admin) {
                boolean success = admin.deleteUser(selectedUser);
                if (success) {
                    showSuccess("User " + selectedUser + " has been deleted.");
                    controller.getAuthentication().loadUsersIntoList(userListModel);
                } else {
                    showError("Failed to delete user. User may not exist or is an admin.");
                }
            } else {
                showError("You are not authorized to perform this action.");
            }
        });
        buttonsPanel.add(deleteUserButton);

        // Refresh Button
        JButton refreshButton = new JButton ("Refresh");
        refreshButton.addActionListener(e -> {
            controller.getAuthentication().loadUsersIntoList(userListModel);
            showSuccess("User list refreshed.");
        });
        buttonsPanel.add(refreshButton);

        userManagementPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(userManagementPanel, BorderLayout.CENTER);

        //Error Label
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        add(errorLabel, BorderLayout.SOUTH);

        //Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        add(logoutButton, BorderLayout.SOUTH);
    }
    //Display error messages method
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setForeground(Color.RED);
    }
    // Display success messages
    private void showSuccess(String message) {
        errorLabel.setText(message);
        errorLabel.setForeground(Color.GREEN);
    }
}