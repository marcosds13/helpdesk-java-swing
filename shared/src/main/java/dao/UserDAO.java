package dao;

import model.User;
import model.enums.InsertResult;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {


    /**
     * Retrieves a list of all users from the database.
     *
     * This method connects to the database, executes a query to fetch all records
     * from the "users" table, and populates a list of User objects with the retrieved data.
     *
     * @return a list of User objects containing all users in the database. If an error
     *         occurs, an empty list is returned.
     */
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

    /**
     * Deletes a user from the database based on the specified ID.
     *
     * This method establishes a connection to the database, executes
     * a DELETE operation to remove the user with the given ID from the
     * "users" table, and returns the result.
     *
     * @param id the unique identifier of the user to be deleted
     * @return true if the user was successfully deleted, false otherwise
     */
    public boolean delete(int id) {
        try (Connection con = ConnectionDB.getConnection()){
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public InsertResult insertResult(User u) {
        try (Connection con = ConnectionDB.getConnection()) {
            String sql = "INSERT INTO users (username, password, name, email, resetpw, role_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getName());
            ps.setString(4, u.getEmail());
            ps.setInt(5, 0);
            ps.setInt(6, u.getRole_id());
            ps.executeUpdate();
            return InsertResult.SUCCESS;

        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            return InsertResult.USERNAME_ALREADY_EXISTS;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return InsertResult.ERROR;
    }

    /**
     * Checks if an email address exists in the users table of the database.
     * This method connects to the database, executes a query to search for the
     * specified email address, and returns whether it is found or not.
     *
     * @param email the email address to check for existence in the database
     * @return true if the email address exists in the database, false otherwise
     */
    public boolean checkEmailExists(String email) {
        try (Connection con = ConnectionDB.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a user from the database based on the provided username.
     *
     * This method establishes a connection to the database, executes a query to find
     * a user with the specified username, and populates a User object with the retrieved data.
     *
     * @param username the username of the user to retrieve from the database
     * @return a User object representing the user with the specified username,
     *         or null if no user is found or an error occurs during the operation
     */
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

    /**
     * Populates a User object with data from the given ResultSet.
     * This method maps the columns from the ResultSet to the corresponding
     * fields in the User object.
     *
     * @param user the User object to be populated with data
     * @param rs the ResultSet containing the user data from the database
     * @throws SQLException if an SQL error occurs while accessing the ResultSet
     */
    private static void fillUserData(User user, ResultSet rs) throws SQLException {
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setResetpw(rs.getInt("resetpw"));
    }


}
