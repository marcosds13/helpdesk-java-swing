package controller;

import dao.TicketDAO;
import dao.UserDAO;
import model.Ticket;
import model.enums.InsertResult;

import java.util.regex.Pattern;

public class CreateTicketController {


    private final TicketDAO ticketDAO = new TicketDAO();
    private final UserDAO userDAO = new UserDAO();


    public InsertResult createTicket(String title, String description, int creatorId){
        if (title == null || title.trim().isEmpty() || creatorId == 0) {
            return InsertResult.INCOMPLETE_DATA;

        }

        // Title length validation
        if (title.length() < 5 || title.length() > 100) {
            return InsertResult.INVALID_TICKET_TITLE;
        }

        // Description length validation
        if (description == null || description.length() < 10 || description.length() > 1000) {
            return InsertResult.INVALID_TICKET_DESCRIPTION;
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9- ]+$");
        if (!pattern.matcher(title).matches()) {
            return InsertResult.INVALID_TICKET_TITLE;
        }

        Ticket t = new Ticket();
        t.setTitle(title);
        t.setDescription(description);
        t.setStatus_id(1);
        t.setCreated_by(creatorId);
        t.setAssigned_to(0);

        boolean result = ticketDAO.createTicket(t);
        if (result) {
            return InsertResult.SUCCESS;
        } else {
            return InsertResult.ERROR;
        }



    }


}
