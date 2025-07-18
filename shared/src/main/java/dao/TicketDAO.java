package dao;

import model.Ticket;
import utils.ConnectionDB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TicketDAO {

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

    public boolean updateTicket() {
        return false;
    }

    public boolean deleteTicket() {
        return false;
    }




}
