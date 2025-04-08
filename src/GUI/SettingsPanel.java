package GUI;

import Classes.Ticket;
import javax.swing.*;
import Controller.Controller;
import Classes.User;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private JLabel feedbackLabel;
    private Controller controller;

    public SettingsPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Header
        JLabel headerLabel = new JLabel("Settings", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(new Color(255, 255, 255)); // White background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // New Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("New Username:"), gbc);

        gbc.gridx = 1;
        JTextField newUsernameField = new JTextField();
        formPanel.add(newUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton changeUsernameButton = new JButton("Change Username");
        changeUsernameButton.setFont(new Font("Arial", Font.BOLD, 14));
        changeUsernameButton.setBackground(new Color(0, 123, 255)); 
        changeUsernameButton.setForeground(Color.WHITE);
        changeUsernameButton.setFocusPainted(false);
        changeUsernameButton.addActionListener(e -> {
            String newUsername = newUsernameField.getText().trim().toLowerCase();
            if (newUsername.isEmpty()) {
                showError("Username cannot be empty.");
                return;
            }
            String oldUsername = controller.getLoggedInAccount().getusername();
            boolean success = controller.getAuthentication().changeUsername(oldUsername, newUsername);
            if (success) {
                showSuccess("Username changed successfully!");
                Ticket.updateUsernameInTickets(oldUsername, newUsername);
            } else {
                showError("Failed to change username.");
            }
        });
        gbc.gridwidth = 2;
        formPanel.add(changeUsernameButton, gbc);

        // Old Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Old Password:"), gbc);

        gbc.gridx = 1;
        JPasswordField oldPasswordField = new JPasswordField();
        formPanel.add(oldPasswordField, gbc);

        // New Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("New Password:"), gbc);

        gbc.gridx = 1;
        JPasswordField newPasswordField = new JPasswordField();
        formPanel.add(newPasswordField, gbc);

        // Change Password Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 14));
        changePasswordButton.setBackground(new Color(40, 167, 69)); 
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                showError("Fields cannot be empty.");
                return;
            }
            boolean success = controller.getAuthentication().changePassword(
                controller.getLoggedInAccount().getusername(), oldPassword, newPassword);
            if (success) {
                showSuccess("Password changed successfully!");
            } else {
                showError("Old password is incorrect.");
            }
        });
        formPanel.add(changePasswordButton, gbc);

        // Delete Account Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteAccountButton.setBackground(new Color(220, 53, 69)); 
        deleteAccountButton.setForeground(Color.WHITE);
        deleteAccountButton.setFocusPainted(false);
        deleteAccountButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.getLoggedInAccount() instanceof User user) {
                    user.deleteAccount(controller.getAuthentication()); // Call deleteAccount from User class
                    controller.logout();
                    showSuccess("Account deleted.");
                    cardLayout.show(cardPanel, "Login");
                } else {
                    showError("Only users can delete their accounts.");
                }
            }
        });
        gbc.gridwidth = 2;
        formPanel.add(deleteAccountButton, gbc);

        // Back Button
        gbc.gridx = 0;
        gbc.gridy = 6;
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(255, 193, 7)); 
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            if (controller.getLoggedInAccount() instanceof Classes.Admin) {
                cardLayout.show(cardPanel, "AdminDashboard");
            } else {
                cardLayout.show(cardPanel, "UserDashboard");
            }
        });
        formPanel.add(backButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Feedback Label
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(feedbackLabel, BorderLayout.SOUTH);
    }

    private void showError(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.RED);

        Timer timer = new Timer(3000, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }

    private void showSuccess(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.GREEN);

        Timer timer = new Timer(3000, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
}