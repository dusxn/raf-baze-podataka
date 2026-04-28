import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField tfUsername;
    private JPasswordField tfPassword;

    public LoginFrame() {
        setTitle("Prijava - Administrator");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Naslov
        JLabel lblTitle = new JLabel("Prijava na sistem", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Korisnicko ime
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Korisnicko ime:"), gbc);
        tfUsername = new JTextField(15);
        gbc.gridx = 1;
        panel.add(tfUsername, gbc);

        // Lozinka
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Lozinka:"), gbc);
        tfPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(tfPassword, gbc);

        // Dugmad
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnLogin = new JButton("Prijavi se");
        JButton btnRegister = new JButton("Registruj se");

        btnLogin.addActionListener(e -> handleLogin());
        btnRegister.addActionListener(e -> openRegister());

        btnPanel.add(btnLogin);
        btnPanel.add(btnRegister);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        add(panel);
    }

    private void handleLogin() {
        String username = tfUsername.getText().trim();
        String password = new String(tfPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Unesite korisnicko ime i lozinku.",
                "Greska", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (UserFileManager.login(username, password)) {
            JOptionPane.showMessageDialog(this,
                "Uspesna prijava! Dobrodosli, " + username + ".",
                "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new AdminPanel(username).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "Pogresno korisnicko ime ili lozinka.",
                "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegister() {
        new RegisterFrame(this).setVisible(true);
    }
}
