package GUI;
import javax.swing.*;

import Classes.Account;
import Controller.Controller;
import Classes.User;

import java.awt.*;

public class SettingsPanel extends JPanel {
    public SettingsPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setPreferredSize(new Dimension(1920, 1080));
        setLayout(new GridLayout(5, 2, 10, 10));

        // Change Username Section
        add(new JLabel("New Username:"));
        JTextField newUsernameField = new JTextField();
        add(newUsernameField);

        JButton changeUsernameButton = new JButton("Change Username");
        changeUsernameButton.addActionListener(e -> {
            String newUsername = newUsernameField.getText();
            if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean success = controller.getAuthentication().changeUsername(controller.getLoggedInAccount().getusername(), newUsername);
            if (success) {
                JOptionPane.showMessageDialog(this, "Username changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to change username. Username might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(changeUsernameButton);

        // Change Password Section
        add(new JLabel("Old Password:"));
        JPasswordField oldPasswordField = new JPasswordField();
        add(oldPasswordField);

        add(new JLabel("New Password:"));
        JPasswordField newPasswordField = new JPasswordField();
        add(newPasswordField);

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean success = controller.getAuthentication().changePassword(controller.getLoggedInAccount().getusername(), oldPassword, newPassword);
            if (success) {
                JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Old password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(changePasswordButton);

        // Delete Account Section
        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Account loggedInAccount = controller.getLoggedInAccount();
                if (loggedInAccount instanceof User user) {
                    user.deleteAccount(controller.getAuthentication());
                    JOptionPane.showMessageDialog(this, "Account deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(cardPanel, "Login");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete account.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(deleteAccountButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "UserDashboard"));
        add(backButton);
    }
}