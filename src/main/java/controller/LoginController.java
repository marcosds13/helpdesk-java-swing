package controller;

import model.User;
import view.LoginView;

public class LoginController {

    private final LoginView loginView;

    public LoginController() {
        loginView = new LoginView(this);
        loginView.setVisible(true);

        loginView.getBtnLogin().addActionListener(_ ->completeLogin());

    }

    private void completeLogin() {
        User input = loginView.getDataLogin();
        System.out.println(input.getUsername() + " " + input.getPassword());
    }

}

