package dao;

import model.Role;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {

    public Role getByID(int id) {
        String sql = "SELECT id, name FROM roles WHERE id = ?";
        try (
            Connection con = ConnectionDB.getConnection();
            PreparedStatement ps = con != null ? con.prepareStatement(sql) : null
        ) {
            if (con == null || ps == null) {
                // Optionally, handle nulls
                return null;
            }

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Role r = new Role();
                    r.setId(rs.getInt("id"));
                    r.setName(rs.getString("name"));
                    return r;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}