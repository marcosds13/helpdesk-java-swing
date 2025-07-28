package view;

import model.User;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {
    private JButton btndashboard;
    private final JButton btnLogout;
    private JButton btnDBconfig;

    public MenuView(User user) {
        setTitle("Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        JPanel painel = new JPanel(new GridLayout(0, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        btndashboard = new JButton("Users Management");
        btnLogout = new JButton("Logout");
        btnDBconfig = new JButton("Database Configuration");

        painel.add(btndashboard);
        painel.add(btnLogout);
        painel.add(btnDBconfig);
        setContentPane(painel);
    }

    public JButton getBtndashboard() {
        return btndashboard;
    }
    public JButton getBtnLogout() {
        return btnLogout;
    }
    public JButton getDBconfig() {return btnDBconfig;}
}
