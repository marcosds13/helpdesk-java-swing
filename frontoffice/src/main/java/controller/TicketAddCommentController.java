package controller;

import dao.CommentDAO;
import model.Comment;
import model.enums.InsertResult;

public class TicketAddCommentController {
    private final CommentDAO commentDAO;

    public TicketAddCommentController() {
        commentDAO = new CommentDAO();
    }
    public InsertResult createComment(int ticket_id, int user_id, String comment_text) {
        if (comment_text == null || comment_text.trim().isEmpty()) {
            return InsertResult.INCOMPLETE_DATA;
        }
        Comment c = new Comment(ticket_id,user_id,comment_text);
        if (commentDAO.createComment(c)) {
            return InsertResult.SUCCESS;
        }
        return InsertResult.ERROR;

    }
}
