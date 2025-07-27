package controller;

import dao.CommentDAO;
import dao.TicketDAO;
import dao.UserDAO;
import dao.StatusDAO;
import model.Comment;
import model.Ticket;
import model.User;
import model.enums.InsertResult;
import model.Status;

import java.util.List;

public class TicketDetailsController {

    private final TicketDAO ticketDAO;
    private final UserDAO userDAO;
    private final CommentDAO commentDAO;
    private final StatusDAO statusDAO;

    public TicketDetailsController() {
        ticketDAO = new TicketDAO();
        userDAO = new UserDAO();
        commentDAO = new CommentDAO();
        statusDAO = new StatusDAO();
    }

    public Ticket getTicketInfo(int ticket_id) {
        return ticketDAO.getById(ticket_id);
    }

    public List<Comment> getComments(int ticket_id) {
        return commentDAO.getCommentsByTicketId(ticket_id);
    }

    public User getUserName(int user_id) {
        if (user_id == 0) {
            return new User();
        }
        return userDAO.getByID(user_id);
    }

    public boolean updateTicketStatus(int ticket_id, int status_id) {
        Ticket t = ticketDAO.getById(ticket_id);
        t.setStatus_id(status_id);
        return ticketDAO.updateTicket(t);
    }

    public List<Status> listAllStatusTypes() {
        return statusDAO.ListAll();
    }

    public Status getStatusByID(int id) {
        return statusDAO.getById(id);
    }

}
