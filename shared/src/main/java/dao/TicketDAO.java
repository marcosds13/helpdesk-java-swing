package dao;

import com.mysql.cj.xdevapi.PreparableStatement;
import model.Ticket;
import utils.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {


    /**
     * Retrieves all Ticket entries from the database.
     * Each ticket object in the returned list contains information such as
     * id, title, description, status, creator, assignee, and creation timestamp.
     *
     * @return a list of Ticket objects representing all the tickets in the database.
     *         The list will be empty if no tickets are found or if an error occurs during retrieval.
     */
    public List<Ticket> listAll(){
        List<Ticket> lista = new ArrayList<>();
        String sql = "SELECT * FROM tickets";
        try (
            Connection con = ConnectionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("id"));
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                t.setStatus_id(rs.getInt("status_id"));
                t.setCreated_by(rs.getInt("created_by"));
                t.setAssigned_to(rs.getInt("assigned_to"));
                t.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                lista.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Retrieves a list of Ticket objects created by a specific user.
     * Each retrieved ticket contains details such as id, title, description,
     * status, creator, assignee, and creation timestamp.
     *
     * @param createdBy the unique identifier of the user who created the tickets
     * @return a list of Ticket objects created by the specified user.
     *         The list will be empty if no tickets are found or if an error occurs during retrieval.
     */
    public List<Ticket> listByCreator(int createdBy) {
        List<Ticket> lista = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE created_by = ?";
        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setInt(1, createdBy);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("id"));
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                t.setStatus_id(rs.getInt("status_id"));
                t.setCreated_by(rs.getInt("created_by"));
                t.setAssigned_to(rs.getInt("assigned_to"));
                t.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                lista.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    /**
     * Creates a new ticket record in the database.
     *
     * @param t the Ticket object containing details to be inserted into the database
     * @return true if the ticket was successfully created, false otherwise
     */
    public boolean createTicket(Ticket t) {
        String sql = "INSERT INTO tickets (title, description, status_id, created_by) VALUES (?, ?, ?, ?)";
        try (
            Connection con = ConnectionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1,t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setInt(3,t.getStatus_id());
            ps.setInt(4,t.getCreated_by());
            ps.executeUpdate();
            return true;


        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the details of an existing ticket in the database.
     *
     * @param t the Ticket object containing the updated information
     *          including its id, title, description, created_by, and status_id
     * @return true if the ticket was successfully updated, false otherwise
     */
    public boolean updateTicket(Ticket t) {
        String sql = "UPDATE tickets SET title = ?, description = ?, status_id = ?, created_by = ?, assigned_to = ? WHERE id = ?";
        try (
            Connection con = ConnectionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setString(1,t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setInt(3,t.getStatus_id());
            ps.setInt(4,t.getCreated_by());

            // --- Handle null for assigned_to ---
            if (t.getAssigned_to() == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, t.getAssigned_to());
            }
            ps.setInt(6,t.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the status of a ticket in the database.
     *
     * @param ticket_id the unique identifier of the ticket to be updated
     * @param status_id the new status ID to set for the ticket
     * @return true if the update operation was successful, false otherwise
     */
    public boolean updateStatus(int ticket_id, int status_id) {
        String sql = "UPDATE tickets SET status_id = ? WHERE id = ?";

        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setInt(1,status_id);
            ps.setInt(2,ticket_id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a ticket from the database with the specified ID.
     *
     * @param id the unique identifier of the ticket to be deleted
     * @return true if the ticket was successfully deleted, false otherwise
     */
    public boolean deleteTicket(int id) {
        String sql = "DELETE FROM tickets WHERE id = ?";
        try (
            Connection con = ConnectionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setInt(1,id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Ticket getById(int id) {
        Ticket ticket = null;
        String sql = "SELECT * from tickets WHERE id = ?";
        try (
            Connection con = ConnectionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setTitle(rs.getString("title"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus_id(rs.getInt("status_id"));
                ticket.setCreated_by(rs.getInt("created_by"));
                ticket.setAssigned_to(rs.getInt("assigned_to"));
                ticket.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ticket;
    }



}
