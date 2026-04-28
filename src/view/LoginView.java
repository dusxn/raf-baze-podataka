package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginView() {
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
        btnLogin = new JButton("Prijavi se");
        btnRegister = new JButton("Registruj se");
        btnPanel.add(btnLogin);
        btnPanel.add(btnRegister);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        add(panel);
    }

    // --- Getteri za podatke iz forme ---

    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(tfPassword.getPassword());
    }

    // --- Listeneri koje controller postavlja ---

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        btnRegister.addActionListener(listener);
    }

    // --- Prikaz poruka ---

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Greska", JOptionPane.ERROR_MESSAGE);
    }

    public void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Greska", JOptionPane.WARNING_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Uspeh", JOptionPane.INFORMATION_MESSAGE);
    }
}
