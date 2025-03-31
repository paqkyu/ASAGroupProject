package GUI;
import javax.swing.*;

import Controller.Controller;

import java.awt.*;

public class RegisterPanel extends JPanel {
    public RegisterPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Username:"));
        JTextField regUsernameField = new JTextField();
        add(regUsernameField);

        add(new JLabel("Password:"));
        JPasswordField regPasswordField = new JPasswordField();
        add(regPasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String username = regUsernameField.getText();
            String password = new String(regPasswordField.getPassword());
            if (controller.register(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                cardLayout.show(cardPanel, "Login");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(registerButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        add(backButton);
    }
}