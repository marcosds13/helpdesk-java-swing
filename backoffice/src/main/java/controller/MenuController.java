package controller;

import model.User;
import view.MenuView;

import javax.swing.*;


public class MenuController {

    private final MenuView menuView;
    private final User loggedUser;

    public MenuController(User loggedUser) {
        this.loggedUser = loggedUser;
        menuView = new MenuView(loggedUser);
        menuView.setVisible(true);

        menuView.getBtnLogout().addActionListener(e -> {
            menuView.dispose();
            new LoginController();
        });
        menuView.getBtndashboard().addActionListener(e ->
                JOptionPane.showMessageDialog(menuView, "To be implemented")
        );
        menuView.getDBconfig().addActionListener(e -> JOptionPane.showMessageDialog(menuView, "To be Implemented"));
    }


}
