package controller;

import model.UserFileManager;
import view.RegisterView;

public class RegisterController {

    private RegisterView view;
    private UserFileManager userModel;

    public RegisterController(RegisterView view, UserFileManager userModel) {
        this.view = view;
        this.userModel = userModel;

        this.view.addRegisterListener(e -> handleRegister());
    }

    private void handleRegister() {
        String username = view.getUsername();
        String password = view.getPassword();
        String confirm = view.getConfirmPassword();

        if (username.isEmpty() || password.isEmpty()) {
            view.showWarning("Sva polja su obavezna.");
            return;
        }

        if (!password.equals(confirm)) {
            view.showWarning("Lozinke se ne poklapaju.");
            return;
        }

        if (userModel.register(username, password)) {
            view.showSuccess("Registracija uspesna! Mozete se prijaviti.");
            view.dispose();
        } else {
            view.showError("Korisnicko ime vec postoji ili je doslo do greske.");
        }
    }
}
