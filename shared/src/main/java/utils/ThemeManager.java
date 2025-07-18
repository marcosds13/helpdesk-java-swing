package utils;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;

public class ThemeManager {

    private static boolean darkMode = false;

    public static void startTheme() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
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