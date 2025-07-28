package Main;

import controller.LoginController;
import utils.ConnectionDB;

import java.sql.Connection;

import static utils.ThemeManager.startTheme;

public class Main {

    public static void main(String[] args) {
        //App starts by checking if MySQL connection was successful.
        startTheme();
        try (Connection con = ConnectionDB.getConnection()) {
            if (con != null && !con.isClosed()) {
                System.out.println("✅ [INFO] Database connection established successfully!");
            } else {
                System.out.println("❌ [ERROR] Failed to establish database connection.");
            }
        } catch (Exception e) {
            System.out.println("❌ [ERROR] Exception while connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
        // Starts Login Controller to open Login View
        new LoginController();

    }
}
