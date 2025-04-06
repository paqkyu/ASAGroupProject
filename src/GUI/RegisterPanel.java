package GUI;

import javax.swing.*;
import Controller.Controller;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;
    private JLabel feedbackLabel;

    public RegisterPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Header
        JLabel headerLabel = new JLabel("Register New Account", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(new Color(255, 255, 255)); // White background

        formPanel.add(new JLabel("Username:", SwingConstants.RIGHT));
        regUsernameField = new JTextField();
        regUsernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(regUsernameField);

        formPanel.add(new JLabel("Password:", SwingConstants.RIGHT));
        regPasswordField = new JPasswordField();
        regPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(regPasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(40, 167, 69)); // Green button
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> {
            String username = regUsernameField.getText().trim().toLowerCase();
            String password = new String(regPasswordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                showError("Username and password cannot be empty.");
                return;
            }
            if (controller.register(username, password)) {
                showSuccess("Registration successful!");
                regUsernameField.setText("");
                regPasswordField.setText("");
                cardLayout.show(cardPanel, "Login");
            } else {
                showError("Username already exists.");
            }
        });
        formPanel.add(registerButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 53, 69)); // Red button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        formPanel.add(backButton);

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