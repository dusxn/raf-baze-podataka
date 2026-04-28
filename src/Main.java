import view.LoginView;
import controller.LoginController;
import model.UserFileManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // koristi podrazumevani izgled
        }

        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            UserFileManager userModel = new UserFileManager();
            new LoginController(loginView, userModel);
            loginView.setVisible(true);
        });
    }
}
