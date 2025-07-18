package controller;

import model.User;
import view.LoginView;
import view.MenuView;

import javax.swing.*;

public class MenuController {

    private final MenuView menuView;
    private final User LoggedUser;

    public MenuController(User LoggedUser) {
        this.LoggedUser = LoggedUser;
        menuView = new MenuView(LoggedUser);
        menuView.setVisible(true);

        menuView.getBtnLogout().addActionListener(e -> {
            menuView.dispose();
            new LoginController();
        });
        menuView.getBtndashboard().addActionListener(e ->{
            JOptionPane.showMessageDialog(menuView, "Not yet implemented");
            //menuView.dispose();
            //new TicketDashboardController();
        });
    }


}
