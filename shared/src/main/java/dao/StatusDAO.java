package dao;

import model.Status;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO {

    // 1. Get all status types
    public List<Status> ListAll() {
        List<Status> list = new ArrayList<>();
        String sql = "SELECT * FROM status_types";
        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Status s = new Status();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Get Status type by ID
    public Status getById(int id) {
        Status s = null;
        String sql = "SELECT * FROM status_types WHERE id = ?";
        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = new Status();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    // 3. Create new status type
    public boolean createStatusType(Status s) {
        String sql = "INSERT INTO status_types (name) VALUES (?)";
        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, s.getName());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Update a status type
    public boolean updateStatusType(Status s) {
        String sql = "UPDATE status_types SET name = ? WHERE id = ?";
        try (
                Connection con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, s.getName());
            ps.setInt(2, s.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Delete a status type
    public boolean deleteStatusType(int id) {
        String sql = "DELETE FROM status_types WHERE id = ?";
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
}
