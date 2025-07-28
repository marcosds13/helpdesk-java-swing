package view;

import controller.TicketAddCommentController;
import model.User;
import model.enums.InsertResult;


import javax.swing.*;
import java.awt.*;

public class TicketAddCommentDialog extends JDialog {

    private TicketAddCommentController controller;
    private final User loggedUser;
    private int ticket_id;
    private JButton btnaddcomment;
    private JButton btncancel;
    private JTextArea txtcomment;
    private JLabel lbluser;
    private JLabel lblticket;
    private JLabel lblcomment;

    public TicketAddCommentDialog(TicketDetailsDialog owner, User loggeduser, int ticket_id) {
        super(owner, "Ticket Add Comment", true);
        this.loggedUser = loggeduser;
        this.ticket_id = ticket_id;
        controller = new TicketAddCommentController();
        InicializeComponents();
        pack();

        setLocationRelativeTo(owner);
        setVisible(true);


    }

    private void InicializeComponents() {
        setLayout(new BorderLayout(10, 10)); // 10 px gap for aesthetics

        // ----- Top Panel: Centered box for Ticket ID and User -----
        // Outer panel with FlowLayout centers the inner box
        JPanel outerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15)); // vertical gap
        // Inner panel with GridLayout stacks two labels vertically
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 0, 5)); // horizontal, vertical gaps

        JLabel lblTicket = new JLabel("Ticket ID: " + ticket_id);
        lblTicket.setHorizontalAlignment(JLabel.CENTER);

        JLabel lblUser = new JLabel("User Name: " + loggedUser.getName());
        lblUser.setHorizontalAlignment(JLabel.CENTER);

        topPanel.add(lblTicket);
        topPanel.add(lblUser);

        // Optionally, put a border to show it's a box (remove if you want no border)
        topPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        topPanel.setPreferredSize(new Dimension(200, 50)); // control the size of the box

        outerPanel.add(topPanel); // put the grid panel inside the flow panel

        add(outerPanel, BorderLayout.NORTH);

        // ----- Center: Text Area for comment -----
        txtcomment = new JTextArea(4, 20);
        JScrollPane scrollPane = new JScrollPane(txtcomment);
        add(scrollPane, BorderLayout.CENTER);

        // ----- Bottom Panel: Buttons -----
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnaddcomment = new JButton("Add Comment");
        btncancel = new JButton("Cancel");
        bottomPanel.add(btnaddcomment);
        bottomPanel.add(btncancel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        btncancel.addActionListener(e -> dispose()); // closes dialog
        btnaddcomment.addActionListener(e -> addComment());
        // btnaddcomment.addActionListener(e -> { /* save code here */ dispose(); });
    }

    private void addComment(){
        String comment_text = txtcomment.getText();
        InsertResult result = controller.createComment(ticket_id, loggedUser.getId(), comment_text);

        if (result == InsertResult.SUCCESS) {
            dispose();
        } else if (result == InsertResult.INCOMPLETE_DATA) {
            JOptionPane.showMessageDialog(this, "Please enter a comment.");
        } else {
            JOptionPane.showMessageDialog(this, "Error creating comment.");
        }
    }
    
}
