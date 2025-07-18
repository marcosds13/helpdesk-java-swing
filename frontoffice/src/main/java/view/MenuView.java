package view;

import model.User;
import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {
    private JButton tbd;
    private final JButton btnLogout;

    public MenuView(User user) {
        setTitle("Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        JPanel painel = new JPanel(new GridLayout(0, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        tbd = new JButton("WIP");
        btnLogout = new JButton("Logout");

        painel.add(tbd);
        painel.add(btnLogout);
        setContentPane(painel);
    }

    public JButton getTbd() {
        return tbd;
    }
    public JButton getBtnLogout() {
        return btnLogout;
    }
}
