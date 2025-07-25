package controller;

import model.User;
import view.LoginView;
import view.MenuView;
import view.TicketDashboardView;

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
        menuView.getBtndashboard().addActionListener(e ->{
            menuView.dispose();
            new TicketDashboardView(loggedUser).display();
        });
    }


}
