import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Postavi izgled na sistemski look-and-feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Koristi podrazumevani izgled ako ne uspe
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
