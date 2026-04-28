package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {

    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JPasswordField tfConfirm;
    private JButton btnRegister;

    public RegisterView(JFrame parent) {
        setTitle("Registracija");
        setSize(400, 280);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Naslov
        JLabel lblTitle = new JLabel("Registracija novog korisnika", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
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

        // Potvrda lozinke
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Potvrdi lozinku:"), gbc);
        tfConfirm = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(tfConfirm, gbc);

        // Dugme
        btnRegister = new JButton("Registruj se");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnRegister, gbc);

        add(panel);
    }

    // --- Getteri ---

    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(tfPassword.getPassword());
    }

    public String getConfirmPassword() {
        return new String(tfConfirm.getPassword());
    }

    // --- Listener ---

    public void addRegisterListener(ActionListener listener) {
        btnRegister.addActionListener(listener);
    }

    // --- Poruke ---

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
