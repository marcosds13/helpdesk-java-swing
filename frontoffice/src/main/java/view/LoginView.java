package view;

import model.User;
import controller.LoginController;
import utils.ThemeManager;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtusername;
    private JPasswordField txtpassword;
    private JButton btnLogin;

    public LoginView(LoginController Controller) {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        InicializeComponents();
    }

    private void InicializeComponents() {

        JPanel title = new JPanel(new BorderLayout());
        JLabel titlelabel = new JLabel("Helpdesk Front-Office", SwingConstants.CENTER);
        titlelabel.setFont(new Font(titlelabel.getFont().getFontName(), Font.BOLD, 18));
        titlelabel.setForeground(Color.WHITE);

        // Botão para alternar o tema

        JButton btnToggleTheme = new JButton("🌙 Tema");
        if (ThemeManager.isDarkMode()) {
            btnToggleTheme.setText("☀️ Tema");
        } else {
            btnToggleTheme.setText("🌙 Tema");
        }
        btnToggleTheme.setFocusable(false);
        btnToggleTheme.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));


        // Adiciona título e botão ao painel do topo
        title.add(titlelabel, BorderLayout.CENTER);
        title.add(btnToggleTheme, BorderLayout.EAST);
        title.setBackground(Color.GRAY); // Apenas inicial, FlatLaf substitui depois

        //Painel Formulario
        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);

        center.add(painel);

        //Username text field
        JLabel lbluser = new JLabel("Username:");
        txtusername = new JTextField();

        //Password text field
        JLabel lblpassword = new JLabel("Password:");
        txtpassword = new JPasswordField();

        //Login Button
        btnLogin = new JButton("Login");

        painel.add(lbluser);
        painel.add(txtusername);
        painel.add(lblpassword);
        painel.add(txtpassword);
        painel.add(new JLabel());
        painel.add(btnLogin);

        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        btnToggleTheme.addActionListener(e -> {
            ThemeManager.toggleTheme(btnToggleTheme);

            if (ThemeManager.isDarkMode()) {
                btnToggleTheme.setText("☀️ Tema");
            } else {
                btnToggleTheme.setText("🌙 Tema");
            }
        });
    }


    public User getDataLogin() {
        return new User(txtusername.getText(), new String(txtpassword.getPassword()));
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }
}
