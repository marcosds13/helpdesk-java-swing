package view;

import controller.TicketDetailsController;
import model.Comment;
import model.Status;
import model.Ticket;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class TicketDetailsDialog extends JDialog {

    private TicketDetailsController controller;
    private final User loggedUser;
    private JTable commentsTable;
    private Ticket ticket;
    private String ticket_statusname;
    private User ticket_assignedto;
    private DefaultTableModel model;


    private JButton btnBack;
    private JButton btnWIP1;
    private JButton btnWIP2;
    private JButton btnAddComment;
    private JButton btnUpdateStatus;
    private JComboBox<String> statusCombo;


    public TicketDetailsDialog(JFrame owner, User loggedUser, int received_ticket) {
        super(owner, "Ticket Details", true);
        this.loggedUser = loggedUser;

        controller = new TicketDetailsController();
        ticket = controller.getTicketInfo(received_ticket);
        ticket_statusname = controller.getStatusByID(ticket.getStatus_id()).getName();
        ticket_assignedto = controller.getUserName(ticket.getAssigned_to());
        InicializeComponents();
        commentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        loadComments(ticket);
        autoResizeAndLockColumns(commentsTable);
        commentsTable.getTableHeader().setReorderingAllowed(false);
        pack();
        setLocationRelativeTo(owner);

    }

    private void InicializeComponents() {
        setLayout(new BorderLayout(10, 10)); // Main layout of dialog

        // ----- Ticket Info Panel (Top) -----
        JPanel ticketInfoPanel = new JPanel(new GridBagLayout());
        ticketInfoPanel.setBorder(BorderFactory.createTitledBorder("Ticket Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;

        // Example: replace these with real ticket values
        JLabel lblTicketId = new JLabel("<html><b>ID:</b> " + ticket.getId() + "</html>");
        JLabel lblTitle = new JLabel("<html><b>Title:</b> " + ticket.getTitle() + "</html>");
        JLabel lblStatus = new JLabel("<html><b>Status:</b> " + "</html>");

        List<Status> listaStatus = controller.listAllStatusTypes();
        String[] StatusNames = listaStatus.stream()
                .map(Status::getName)
                .toArray(String[]::new);
        statusCombo = new JComboBox<>(StatusNames);
        statusCombo.setSelectedItem(ticket_statusname);

        JLabel lblAssigned = new JLabel("<html><b>Assigned to:</b> </html>");
        JComboBox<User> assignedCombo = new JComboBox<>();
        List<User> users = controller.listAllAgents();
        assignedCombo.addItem(null);
        for (User user : users) {
            assignedCombo.addItem(user);
        }
        assignedCombo.setSelectedItem(ticket_assignedto.equals("") ? "None" : ticket_assignedto);
        assignedCombo.setEnabled(loggedUser.getRole_id() == 1);
        assignedCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) {
                    value = "None";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });


        statusCombo.setEnabled(loggedUser.getRole_id() == 1 || loggedUser.getRole_id() == 2);

        ticketInfoPanel.add(lblTicketId, gbc);

        gbc.gridx++;
        ticketInfoPanel.add(lblTitle, gbc);

        gbc.gridx++;
        ticketInfoPanel.add(lblAssigned, gbc);
        if (loggedUser.getRole_id() == 1) {
            gbc.gridx++;
            ticketInfoPanel.add(assignedCombo, gbc);
        }

        gbc.gridx++;
        ticketInfoPanel.add(lblStatus, gbc);

        gbc.gridx++;
        ticketInfoPanel.add(statusCombo, gbc);

        add(ticketInfoPanel, BorderLayout.NORTH);

        // ----- Description Panel (Center) -----
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBorder(BorderFactory.createTitledBorder("Description"));

        // JTextArea allows wrapping and scrolling for large text
        JTextArea txtDescription = new JTextArea(ticket.getDescription());
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setEditable(false);
        txtDescription.setBackground(UIManager.getColor("Label.background"));
        txtDescription.setFont(UIManager.getFont("Label.font"));

        JScrollPane scrollDesc = new JScrollPane(txtDescription,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollDesc.setPreferredSize(new Dimension(600, 100));
        scrollDesc.setMaximumSize(new Dimension(600, 100));
        descPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 120));

        descPanel.add(scrollDesc, BorderLayout.CENTER);
        add(descPanel, BorderLayout.CENTER);

        JPanel commentsPanel = new JPanel(new BorderLayout());
        commentsPanel.setBorder(BorderFactory.createTitledBorder("Comments"));

        String[] columnNames = {"Date", "Owner", "Comment"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        commentsTable = new JTable(model);
        commentsTable.getColumnModel().getColumn(2).setCellRenderer(new MultiLineCellRenderer());

        JScrollPane scrollComments = new JScrollPane(commentsTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        commentsPanel.add(scrollComments, BorderLayout.CENTER);

        commentsPanel.setPreferredSize(new Dimension(1000, 650));

//        Paging controls - In the future thinking of adding a paging system
//        JPanel paginationPanel = new JPanel();
//        btnWIP1 = new JButton("WIP1");
//        btnWIP2 = new JButton("WIP2");
//        paginationPanel.add(btnWIP1);
//        paginationPanel.add(btnWIP2);
//        commentsPanel.add(paginationPanel, BorderLayout.SOUTH);

        add(commentsPanel, BorderLayout.SOUTH); // We'll re-attach to correct position in a moment

        // 4. Buttons Panel (Bottom)
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAddComment = new JButton("Add Comment");
        btnAddComment.addActionListener(e -> openAddComment());
        btnUpdateStatus = new JButton("Update Status");
        btnUpdateStatus.addActionListener(e -> updateStatus());
        btnBack = new JButton("Back");
        buttonsPanel.add(btnAddComment);
        buttonsPanel.add(btnUpdateStatus);
        if (loggedUser.getRole_id() == 1) {
            JButton btnUpdateAssign = new JButton("Update Assignment");
            btnUpdateAssign.addActionListener(e -> updateAssignment((User) assignedCombo.getSelectedItem()));
            buttonsPanel.add(btnUpdateAssign);
        } else if (loggedUser.getRole_id() == 2) {
            JButton btnTakeOver = new JButton("Take Over");
            btnTakeOver.addActionListener(e -> takeOver());
            buttonsPanel.add(btnTakeOver);
        }
        buttonsPanel.add(btnBack);
        add(buttonsPanel, BorderLayout.PAGE_END);

        // Use a vertical box layout to stack Description and Comments panels
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(descPanel);
        centerPanel.add(commentsPanel);

        // Place both panels in the center (replacing old descPanel)
        add(centerPanel, BorderLayout.CENTER);



    }
    private void loadComments(Ticket ticket) {
        model.setRowCount(0);
        List<Comment> commentsList = controller.getComments(ticket.getId());
        for (Comment c : commentsList) {
            String singleLineComment = c.getMessage().replaceAll("\\s*\\n\\s*", " ");
            model.addRow(new Object[]{
                    c.getSent_at(),
                    controller.getUserName(c.getUser_id()).getName(),
                    singleLineComment

            });

        }
    }

    private void autoResizeAndLockColumns(JTable table) {
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            int maxWidth = 0;

            // Check the width of the column header
            TableCellRenderer headerRenderer = column.getHeaderRenderer();
            if (headerRenderer == null) {
                headerRenderer = table.getTableHeader().getDefaultRenderer();
            }
            Component comp = headerRenderer.getTableCellRendererComponent(
                    table, column.getHeaderValue(), false, false, 0, 0);
            maxWidth = comp.getPreferredSize().width;

            // Check the width of each cell in this column
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                comp = cellRenderer.getTableCellRendererComponent(
                        table, table.getValueAt(row, col), false, false, row, col);
                maxWidth = Math.max(maxWidth, comp.getPreferredSize().width);
            }

            // Add a little padding
            maxWidth += 10;

            // Apply maxWidth as preferred, min and max (for lock)
            column.setPreferredWidth(maxWidth);

            // Lock width for Date (col 0) and Owner (col 1), allow Comment (col 2) to resize
            if (col == 0 || col == 1) {
                column.setMinWidth(maxWidth);
                column.setMaxWidth(maxWidth);
                column.setResizable(false);
            } else {
                column.setResizable(true); // Only Comment can be resized
            }
        }
    }
    private void updateAssignment(User selectedUser) {
        Integer userIdToAssign = null;
        boolean updated = false;

        try {
            // This will throw NullPointerException if selectedUser is null
            userIdToAssign = selectedUser.getId();

            // If userIdToAssign is null, this may throw exception depending on controller code
            updated = controller.updateTicketAssignment(ticket.getId(), userIdToAssign);

        } catch (NullPointerException e) {
            // If selectedUser was null, retry with userIdToAssign = null
            updated = controller.updateTicketAssignment(ticket.getId(), null);
        }

        if (updated) {
            ticket_assignedto = selectedUser;
            JOptionPane.showMessageDialog(this,
                    "Assignment updated successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error updating assignment",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
//    private void updateAssignment(User selectedUser) {
//        // Safely check if assignment has changed
//        if ((selectedUser == null && ticket_assignedto == null) ||
//                (selectedUser != null && selectedUser.equals(ticket_assignedto))) {
//            JOptionPane.showMessageDialog(this,
//                    "Assignment hasn't changed",
//                    "No Changes",
//                    JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }
//
//        Integer userIdToAssign = (selectedUser == null) ? null : selectedUser.getId();
//        if (controller.updateTicketAssignment(ticket.getId(), userIdToAssign)) {
//            ticket_assignedto = selectedUser;
//            JOptionPane.showMessageDialog(this,
//                    "Assignment updated successfully",
//                    "Success",
//                    JOptionPane.INFORMATION_MESSAGE);
//        } else {
//            JOptionPane.showMessageDialog(this,
//                    "Error updating assignment",
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//    }

    private void takeOver() {
        if (controller.updateTicketAssignment(ticket.getId(), loggedUser.getId())) {
            ticket_assignedto = loggedUser;
            JOptionPane.showMessageDialog(this,
                    "Ticket assigned to you successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error taking over ticket",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatus() {
        String selectedStatus = (String) statusCombo.getSelectedItem();
        if (selectedStatus.equals(ticket_statusname)) {
            JOptionPane.showMessageDialog(this,
                    "Status hasn't changed",
                    "No Changes",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        List<Status> statuses = controller.listAllStatusTypes();
        int newStatusId = statuses.stream()
                .filter(s -> s.getName().equals(selectedStatus))
                .findFirst()
                .map(Status::getId)
                .orElse(-1);

        if (controller.updateTicketStatus(ticket.getId(), newStatusId)) {
            ticket_statusname = selectedStatus;
            JOptionPane.showMessageDialog(this,
                    "Status updated successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error updating status",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAddComment() {
        if ("CLOSED".equals(ticket_statusname)) {
            JOptionPane.showMessageDialog(this,
                    "Cannot add comments to closed tickets.",
                    "Ticket Closed",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        TicketAddCommentDialog dialog = new TicketAddCommentDialog(this, loggedUser, ticket.getId());
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                loadComments(ticket);
            }
        });
    }

}


