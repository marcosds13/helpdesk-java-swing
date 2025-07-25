package view;

import controller.MenuController;
import controller.TicketDashboardController;
import model.Status;
import model.Ticket;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;


public class TicketDashboardView extends JFrame {
    private final User loggedUser;
    private TicketDashboardController controller;
    private DefaultTableModel model;
    private JTable tickets;
    private JButton btnBack;
    private JButton btnRefresh;
    private JButton btnCreateTicket;
    private JButton btnOpenTicketDetails;


    public TicketDashboardView(User loggedUser) {
        controller = new TicketDashboardController();
        this.loggedUser = loggedUser;


        setTitle("Ticket Menu"); // Sets the window's title bar text
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 900); // Size of the window
        setLocationRelativeTo(null); // Center on screen
        InicializeComponents();
        loadTickets(loggedUser);
    }

    private void InicializeComponents() {
        User user = loggedUser;
            // Step 1: Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Step 2: Title panel (NORTH)
        JLabel titleLabel = new JLabel("Ticket DashBoard - " + user.getName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Step 3: Content panel (CENTER) with ticket list and buttons
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0)); // horizontal gap

        // Ticket list (CENTER-LEFT)
        String[] colunas = {"ID", "Title", "Status", "Creator", "Assignee", "Created At"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tickets = new JTable(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tickets.setDefaultRenderer(Object.class, centerRenderer);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        tickets.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);


        tickets.setRowHeight(30);
        tickets.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(tickets);

        JPanel ticketPanel = new JPanel(new BorderLayout());
        ticketPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel (RIGHT)
        Color userbase = UIManager.getColor("Panel.background");
        Color customBg = FlatDarculaLaf.isLafDark()
                ? userbase.brighter()
                : userbase.darker();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        JLabel userLabel = new JLabel("<html>👨‍ User: " + user.getName() +"<br> Access: " + controller.getRoleByID(user.getRole_id()) + "</html", SwingConstants.CENTER);
        userLabel.setBackground(customBg);
        userLabel.setOpaque(true);

        btnRefresh = new JButton("Refresh Tickets");
        btnRefresh.addActionListener(e -> loadTickets(loggedUser));
        btnCreateTicket = new JButton("Create Ticket");
        btnCreateTicket.addActionListener(e -> openCreateTicket());
        btnOpenTicketDetails = new JButton("Open Ticket Details");
        btnOpenTicketDetails.addActionListener(e -> JOptionPane.showMessageDialog(this, "Not Yet Implemented!"));
        btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            TicketDashboardView.this.dispose();
            new MenuController(loggedUser);
        });


        buttonPanel.add(userLabel);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createVerticalStrut(30));

        for (JButton btn : new JButton[]{btnRefresh, btnCreateTicket, btnOpenTicketDetails, btnBack}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, btn.getPreferredSize().height));
            buttonPanel.add(btn);
            buttonPanel.add(Box.createVerticalStrut(10));
        }


        // Add ticket and button panels to contentPanel
        contentPanel.add(ticketPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.EAST);

        // Add contentPanel to mainPanel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add mainPanel to JFrame
        setContentPane(mainPanel);
    }

    private void loadTickets(User loggedUser) {
        model.setRowCount(0);
        int userRole = loggedUser.getRole_id();
        int userID = loggedUser.getId();
        List<Ticket> ticketslist = controller.listAllTickets();
        for (Ticket t : ticketslist) {

            if (userRole == 3 && t.getCreated_by() != userID) {
                continue;
            }

            model.addRow(new Object[]{
                    t.getId(),
                    t.getTitle(),
                    controller.getStatusByID(t.getStatus_id()).getName(),
                    controller.getUserByID(t.getCreated_by()).getName(),
                    t.getAssigned_to() != 0 ? controller.getUserByID(t.getAssigned_to()).getName(): "To be assigned",
                    t.getCreated_at()

            });


        }
    }

    private void openCreateTicket() {
        new TicketCreateDialog(this, loggedUser).setVisible(true);
        loadTickets(loggedUser);
    }
    public void display() {
        setVisible(true);
    }
}