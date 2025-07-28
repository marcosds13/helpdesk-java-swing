package controller;

import dao.UserDAO;
import model.User;
import service.PasswordEncryptionService;
import view.LoginView;

import javax.swing.*;


public class LoginController {

    private final LoginView loginView;

    public LoginController() {
        loginView = new LoginView(this);
        loginView.setVisible(true);

        loginView.getBtnLogin().addActionListener(_ ->completeLogin());

    }


    private void completeLogin() {
        User input = loginView.getDataLogin();

        if (!input.isLoginValid()) {
            JOptionPane.showMessageDialog(loginView, "Please make sure Username and Password are filled.");
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getByUsername(input.getUsername());


        if (user != null && PasswordEncryptionService.verifyPassword(input.getPassword(), user.getPassword())) {
            System.out.println(input.getPassword() + " " + user.getPassword());
            if (user.getRole_id() != 1) {
                JOptionPane.showMessageDialog(loginView, "You don't have permission to access this Application.");
                return;
            }
            JOptionPane.showMessageDialog(loginView, "Login Successful.");
            loginView.dispose();
            new MenuController(user);
        } else {
            JOptionPane.showMessageDialog(loginView, "Invalid Username or Password.");
        }

    }

}

