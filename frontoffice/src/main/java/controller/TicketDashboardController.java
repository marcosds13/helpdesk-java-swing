package controller;


import dao.RoleDAO;
import dao.StatusDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Role;
import model.Status;
import model.Ticket;
import model.User;

import java.util.List;

public class TicketDashboardController {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final TicketDAO ticketDAO;
    private final StatusDAO statusDAO;


    public TicketDashboardController() {
        userDAO = new UserDAO();
        roleDAO = new RoleDAO();
        ticketDAO = new TicketDAO();
        statusDAO = new StatusDAO();

    }

    public List<User> listAllUsers() {
        return userDAO.listAll();
    }
    public List<Ticket> listAllTickets() {
        return ticketDAO.listAll();
    }

    public List<Ticket> listTicketsByCreator(int id) {
        return ticketDAO.listByCreator(id);
    }

    public Status getStatusByID(int id) {
        return statusDAO.getById(id);
    }

    public Role getRoleByID(int id) {
        return roleDAO.getByID(id);
    }

    public User getUserByID(int id){
        return userDAO.getByID(id);
    }

}
