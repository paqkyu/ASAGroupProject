import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class TicketingSystemGUI {
    private static final String Accounts = "Accounts.csv";  // File to store user data
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField usernameField, regUsernameField;
    private JPasswordField passwordField, regPasswordField;
    private JButton loginButton, registerButton;
    private JLabel errorLabel;
    private ArrayList<Account> accounts;

    public TicketingSystemGUI() {
        accounts = new ArrayList<>();
        loadAccounts();  // Load existing accounts from file
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
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

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
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

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

    private void loadAccounts() {
        File file = new File(Accounts);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    accounts.add(new Account(parts[0], parts[1]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Handle login functionality
    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                errorLabel.setText("Login successful!");
                errorLabel.setForeground(Color.GREEN);
                return;
            }
        }

        errorLabel.setText("Invalid username or password.");
        errorLabel.setForeground(Color.RED);
    }

    // Handle user registration functionality
    private void performRegistration() {
        String username = regUsernameField.getText();
        String password = new String(regPasswordField.getPassword());

        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                errorLabel.setText("Username already exists.");
                errorLabel.setForeground(Color.RED);
                return;
            }
        }

        // If no duplicate username is found, register the user
        accounts.add(new Account(username, password));
        saveAccounts();  // Save the updated account list to file
        errorLabel.setText("Registration successful!");
        errorLabel.setForeground(Color.GREEN);
        cardLayout.show(cardPanel, "Login");
    }

    // Save the updated accounts to the file
    private void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Accounts))) {
            for (Account account : accounts) {
                writer.write(account.getUsername() + "," + account.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Account class to store user data
    public class Account {
        private String username;
        private String password;

        public Account(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
