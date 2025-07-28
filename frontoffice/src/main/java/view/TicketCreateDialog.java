package view;

import controller.TicketCreateController;
import model.User;

import javax.swing.*;
import java.awt.*;
import model.enums.InsertResult;

public class TicketCreateDialog extends JDialog {

    private final User loggedUser;
    private JTextField txttitle;

    private JTextArea txtdescription;

    private JButton btncreate;
    private JButton btncancel;

    public TicketCreateDialog(JFrame owner, User loggedUser) {
        super(owner, "Create Ticket - " + loggedUser.getName(), true);
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

        JLabel user = new JLabel(loggedUser.getName());

        panel.add(new JLabel("Title:"));
        panel.add(txttitle);

        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(txtdescription));

        panel.add(new JLabel("Created by:"));
        panel.add(user);


        //Buttons bottom Small Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btncreate = new JButton("Create");
        btncancel = new JButton("Cancel");
        btncreate.addActionListener(e -> create());
        btncancel.addActionListener(e -> dispose());

        buttonPanel.add(btncreate);
        buttonPanel.add(btncancel);

        add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
    }

    private void create() {
        String title = txttitle.getText();
        String description = txtdescription.getText();
        int creator_id = loggedUser.getId();

        TicketCreateController controller = new TicketCreateController();

        InsertResult result = controller.createTicket(title, description, creator_id);

        switch (result) {
            case INCOMPLETE_DATA -> JOptionPane.showMessageDialog(this, "Please fill all fields.");
            case INVALID_TICKET_TITLE -> JOptionPane.showMessageDialog(this, "Ticket title Invalid, make sure you use only Numbers, Letters or - and has more than 5 characters and less than 100 characters.");
            case INVALID_TICKET_DESCRIPTION -> JOptionPane.showMessageDialog(this, "Ticket description must be between 10 and 1000 characters.");
            case INVALID_TICKET_CREATOR -> JOptionPane.showMessageDialog(this, "Invalid creator.");
            case SUCCESS -> {
                JOptionPane.showMessageDialog(this, "Ticket created successfully.");
                this.dispose();
            }
            default -> JOptionPane.showMessageDialog(this, "Unexpected error.");
        }


    }

}
