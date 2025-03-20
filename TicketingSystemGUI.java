import java.awt.*;
import javax.swing.*;

public class TicketingSystemGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField usernameField, regUsernameField;
    private JPasswordField passwordField, regPasswordField;
    private JButton loginButton, registerButton;
    private JLabel errorLabel;
    private Authentication Authentication;  

    public TicketingSystemGUI() {
        Authentication = new Authentication();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicketingSystemGUI gui = new TicketingSystemGUI();
            gui.createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Ticketing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createLoginPanel(), "Login");
        cardPanel.add(createRegisterPanel(), "Register");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> performLogin());
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));
        panel.add(registerButton);

        errorLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(errorLabel);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Username:"));
        regUsernameField = new JTextField();
        panel.add(regUsernameField);

        panel.add(new JLabel("Password:"));
        regPasswordField = new JPasswordField();
        panel.add(regPasswordField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> performRegistration());
        panel.add(registerButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        panel.add(backButton);

        return panel;
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (Authentication.login(username, password)) {
            errorLabel.setText("Login successful!");
            errorLabel.setForeground(Color.GREEN);
        } else {
            errorLabel.setText("Invalid username or password.");
            errorLabel.setForeground(Color.RED);
        }
    }

    private void performRegistration() {
        String username = regUsernameField.getText();
        String password = new String(regPasswordField.getPassword());

        if (Authentication.register(username, password)) {
            errorLabel.setText("Registration successful!");
            errorLabel.setForeground(Color.GREEN);
            cardLayout.show(cardPanel, "Login");
        } else {
            errorLabel.setText("Username already exists.");
            errorLabel.setForeground(Color.RED);
        }
    }
}
