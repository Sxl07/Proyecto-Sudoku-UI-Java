package ui.custom.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ControlPanel extends JPanel {
    private final JLabel statusLabel;
    private final JButton startButton;
    private final JButton removeButton;
    private final JButton clearButton;
    private final JButton finishButton;

    public ControlPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(248, 250, 253));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(198, 206, 221), 2),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(6, 0, 6, 0);

        final JLabel title = new JLabel("Controles", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(24, 32, 53));

        final JLabel subtitle = new JLabel("Gestiona el tablero desde aquí", SwingConstants.LEFT);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(104, 114, 140));

        statusLabel = createStatusLabel();
        startButton = createButton("Iniciar / reiniciar");
        removeButton = createButton("Remover número");
        clearButton = createButton("Limpiar tablero");
        finishButton = createButton("Finalizar juego");

        add(title, constraints);
        add(subtitle, constraints);
        add(startButton, constraints);
        add(removeButton, constraints);
        add(clearButton, constraints);
        add(finishButton, constraints);
        add(statusLabel, constraints);
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getFinishButton() {
        return finishButton;
    }

    private JLabel createStatusLabel() {
        final JLabel label = new JLabel("Listo para comenzar");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(104, 114, 140));
        return label;
    }

    private JButton createButton(final String text) {
        final JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(41, 98, 255));
        button.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        return button;
    }
}
