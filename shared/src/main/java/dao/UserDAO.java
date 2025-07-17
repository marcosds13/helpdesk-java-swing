package dao;

import model.User;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {


    public static List<User> listAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionDB.getConnection()) {
            String sql = "SELECT * FROM users";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                fillUserData(u, rs);
                users.add(u);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUsername(String username) {
        User user = null;
        try (Connection con = ConnectionDB.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ?";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
                fillUserData(user, rs);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private static void fillUserData(User user, ResultSet rs) throws SQLException {
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setResetpw(rs.getInt("resetpw"));
    }


}
