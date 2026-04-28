import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JPasswordField tfConfirm;
    private LoginFrame parentFrame;

    public RegisterFrame(LoginFrame parent) {
        this.parentFrame = parent;
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
        JButton btnRegister = new JButton("Registruj se");
        btnRegister.addActionListener(e -> handleRegister());
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnRegister, gbc);

        add(panel);
    }

    private void handleRegister() {
        String username = tfUsername.getText().trim();
        String password = new String(tfPassword.getPassword());
        String confirm = new String(tfConfirm.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Sva polja su obavezna.",
                "Greska", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this,
                "Lozinke se ne poklapaju.",
                "Greska", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (UserFileManager.register(username, password)) {
            JOptionPane.showMessageDialog(this,
                "Registracija uspesna! Mozete se prijaviti.",
                "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Korisnicko ime vec postoji ili je doslo do greske.",
                "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
}
