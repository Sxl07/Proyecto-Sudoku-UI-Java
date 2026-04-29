package ui.custom.frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ui.custom.screen.MainScreen;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Sudoku Studio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setContentPane(new MainScreen());
        setSize(1120, 760);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
