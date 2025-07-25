package utils;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ThemeManager {

    private static boolean darkMode = false;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = ThemeManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
                String darkModeValue = properties.getProperty("theme.darkMode");
                if ("true".equalsIgnoreCase(darkModeValue)) {
                    darkMode = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // darkMode remains false unless explicitly set to true above
    }

    public static void startTheme() {
        try {
            if (darkMode) {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            } else {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toggleTheme(Component rootComponent) {
        try {
            if (darkMode) {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } else {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            }
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(rootComponent));
            darkMode = !darkMode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDarkMode() {
        return darkMode;
    }
}
