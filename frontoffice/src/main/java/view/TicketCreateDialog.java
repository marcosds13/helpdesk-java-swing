package view;

import model.User;

import javax.swing.*;
import java.awt.*;

public class TicketCreateDialog extends JDialog {

    private final User loggedUser;
    private JTextField txttitle;

    private JTextArea txtdescription;

    private JButton btncreate;
    private JButton btncancel;

    public TicketCreateDialog(JFrame owner, User loggedUser) {
        super(owner, "Create Ticket", true);
        this.loggedUser = loggedUser;
        setSize(800,300);
        setLocationRelativeTo(owner);
        InicializeComponents();

    }

    private void InicializeComponents() {

        JPanel panel = new JPanel (new GridLayout(0,2));

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txttitle = new JTextField();
        txtdescription = new JTextArea();

        JLabel teste = new JLabel(loggedUser.getName());

        panel.add(new JLabel("Title:"));
        panel.add(txttitle);

        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(txtdescription));

        panel.add(new JLabel("Created by:"));
        panel.add(teste);


        //Buttons bottom Small Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btncreate = new JButton("Create");
        btncancel = new JButton("Cancel");

        buttonPanel.add(btncreate);
        buttonPanel.add(btncancel);

        add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
    }



}
