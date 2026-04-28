package controller;

import model.UserFileManager;
import model.SesijaModel;
import model.LaboratorijaModel;
import view.LoginView;
import view.RegisterView;
import view.AdminView;

public class LoginController {

    private LoginView view;
    private UserFileManager userModel;

    public LoginController(LoginView view, UserFileManager userModel) {
        this.view = view;
        this.userModel = userModel;

        this.view.addLoginListener(e -> handleLogin());
        this.view.addRegisterListener(e -> openRegister());
    }

    private void handleLogin() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            view.showWarning("Unesite korisnicko ime i lozinku.");
            return;
        }

        if (userModel.login(username, password)) {
            view.showSuccess("Uspesna prijava! Dobrodosli, " + username + ".");
            view.dispose();

            AdminView adminView = new AdminView(username);
            SesijaModel sesijaModel = new SesijaModel();
            LaboratorijaModel labModel = new LaboratorijaModel();
            new AdminController(adminView, sesijaModel, labModel);
            adminView.setVisible(true);
        } else {
            view.showError("Pogresno korisnicko ime ili lozinka.");
        }
    }

    private void openRegister() {
        RegisterView regView = new RegisterView(view);
        new RegisterController(regView, userModel);
        regView.setVisible(true);
    }
}
