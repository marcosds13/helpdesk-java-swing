package view;

import controller.MenuController;
import controller.TicketDashboardController;
import model.Status;
import model.Ticket;
import model.User;

import javax.swing.*;
import javax.swing.table.*;
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
    private JButton btnApplyFilter;
    private JButton btnClearFilter;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private JTextField txtFilter;


    public TicketDashboardView(User loggedUser) {
        controller = new TicketDashboardController();
        this.loggedUser = loggedUser;


        setTitle("Ticket Menu - " + loggedUser.getName()); // Sets the window's title bar text
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
        btnOpenTicketDetails.addActionListener(e -> openDetailsTicket());
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

        //FILTER Function
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtFilter = new JTextField(30);
        btnApplyFilter = new JButton("Filter");
        btnClearFilter = new JButton("Clear");
        filterPanel.add(new JLabel("Filter:"));
        filterPanel.add(txtFilter);
        filterPanel.add(btnApplyFilter);
        filterPanel.add(btnClearFilter);

        rowSorter = new TableRowSorter<>(model);
        tickets.setRowSorter(rowSorter);

        btnApplyFilter.addActionListener(e -> filterTable());
        btnClearFilter.addActionListener(e -> clearFilter());


        ticketPanel.add(filterPanel, BorderLayout.SOUTH); // <<<<< Correct place!
        // Add mainPanel to JFrame
        setContentPane(mainPanel);
    }

    /**
     * Loads tickets into a table model for display, filtered based on the role
     * and ID of the logged-in user. Initializes the table with ticket data,
     * including details such as ticket ID, title, status, creator, assigned user,
     * and creation date. Ensures that users with a specific role only see their
     * own tickets.
     *
     * @param loggedUser the currently logged-in user, whose role and ID will
     *                   determine the tickets visible in the table; must not be null
     */
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
        autoFitColumns(tickets);
    }

    /**
     * Adjusts the column widths of a given JTable to fit the content and header size,
     * while ensuring the widths remain within a specified minimum and maximum range.
     *
     * @param table the JTable whose column widths are to be adjusted; must not be null
     */
    private void autoFitColumns(JTable table) {
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = 50; // Minimum width
            int maxWidth = 300; // Max width to avoid crazy wide columns

            // Get width of column header
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(table, tableColumn.getHeaderValue(), false, false, 0, column);
            preferredWidth = Math.max(headerComp.getPreferredSize().width, preferredWidth);

            // Get max width of cell contents
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
            }

            preferredWidth += 10; // Add a little extra for padding
            preferredWidth = Math.min(preferredWidth, maxWidth);

            tableColumn.setPreferredWidth(preferredWidth);
        }
    }

    private void openCreateTicket() {
        new TicketCreateDialog(this, loggedUser).setVisible(true);
        loadTickets(loggedUser);
    }

    private void openDetailsTicket() {
        int row = tickets.getSelectedRow();
        if (row >= 0) {
            int ticketID = (int) tickets.getValueAt(row, 0);
            new TicketDetailsDialog(this, loggedUser, ticketID).setVisible(true);
            loadTickets(loggedUser);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a ticket.");
        }


    }
    public void display() {
        setVisible(true);
    }


    private void filterTable() {
        String text = txtFilter.getText();
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);

        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));

        }
    }

    private void clearFilter(){
        txtFilter.setText("");
        rowSorter.setRowFilter(null);
        loadTickets(loggedUser);
    }
}