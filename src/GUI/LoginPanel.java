package GUI;
import javax.swing.*;

import Classes.Account;
import Classes.Admin;
import Classes.User;
import Controller.Controller;

import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    public LoginPanel(Controller controller, JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (controller.login(username, password)) {
                errorLabel.setText("Login successful!");
                errorLabel.setForeground(Color.GREEN);

                Account account = controller.getLoggedInAccount();
                if (account instanceof Admin) {
                    cardLayout.show(cardPanel, "AdminDashboard");
                } else if (account instanceof User) {
                    cardLayout.show(cardPanel, "UserDashboard");
                } else {
                    cardLayout.show(cardPanel, "Dashboard");
                }
            } else {
                errorLabel.setText("Invalid username or password.");
                errorLabel.setForeground(Color.RED);
            }
        });
        add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));
        add(registerButton);

        JButton viewEventsButton = new JButton("View Events");
        viewEventsButton.addActionListener(e -> cardLayout.show(cardPanel, "EventsDashBoard"));

        errorLabel = new JLabel("", SwingConstants.CENTER);
        add(errorLabel);
    }
}