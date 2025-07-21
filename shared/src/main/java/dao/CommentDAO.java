package dao;

import model.Comment;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    /**
     * Retrieves all Comment entries from the database.
     * @return a list of Comment objects representing all the comments in the database.
     */
    public List<Comment> ListAll() {
        List<Comment> lista = new ArrayList<>();
        String sql = "SELECT * FROM comments";
        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setTicket_id(rs.getInt("ticket_id"));
                c.setUser_id(rs.getInt("user_id"));
                c.setMessage(rs.getString("message"));
                c.setSent_at(rs.getTimestamp("sent_at").toLocalDateTime());
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Creates a new comment record in the database.
     * @param c the Comment object containing details to be inserted
     * @return true if the comment was successfully created, false otherwise
     */
    public boolean createComment(Comment c) {
        String sql = "INSERT INTO comments (ticket_id, user_id, message) VALUES (?, ?, ?)";
        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, c.getTicket_id());
            ps.setInt(2, c.getUser_id());
            ps.setString(3, c.getMessage());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the details of an existing comment.
     * @param c the Comment object with updated info
     * @return true if updated, false otherwise
     */
    public boolean updateComment(Comment c) {
        String sql = "UPDATE comments SET ticket_id = ?, user_id = ?, message = ? WHERE id = ?";
        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, c.getTicket_id());
            ps.setInt(2, c.getUser_id());
            ps.setString(3, c.getMessage());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a comment by its ID.
     * @param id the comment's ID
     * @return true if deleted, false otherwise
     */
    public boolean deleteComment(int id) {
        String sql = "DELETE FROM comments WHERE id = ?";
        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all comments for a specific ticket.
     * @param ticketId the ID of the ticket
     * @return a list of Comment objects for that ticket
     */
    public List<Comment> getCommentsByTicketId(int ticketId) {
        List<Comment> lista = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE ticket_id = ?";
        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, ticketId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setTicket_id(rs.getInt("ticket_id"));
                c.setUser_id(rs.getInt("user_id"));
                c.setMessage(rs.getString("message"));
                c.setSent_at(rs.getTimestamp("sent_at").toLocalDateTime());
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

}
