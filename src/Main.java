import javax.swing.*;
import GUI.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.createAndShowGUI();
        });
    }
}