package ui.custom.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;

import model.GameStatusEnum;
import service.GameService;
import ui.custom.panel.BoardPanel;
import ui.custom.panel.ControlPanel;

public class MainScreen extends JPanel {
    private final GameService gameService;
    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    private final JLabel titleLabel;
    private final JLabel boardStateLabel;
    private final JLabel footerLabel;

    public MainScreen() {
        this.gameService = new GameService();
        setLayout(new BorderLayout(18, 18));
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setBackground(new Color(238, 242, 247));

        titleLabel = createTitleLabel();
        boardStateLabel = createBoardStateLabel();
        footerLabel = createFooterLabel();

        boardPanel = new BoardPanel();
        boardPanel.setPreferredSize(new Dimension(620, 620));
        boardPanel.setCellClickListener((row, col) -> {
            if (!ensureBoardStarted()) {
                return;
            }

            boardPanel.selectCell(row, col);
            boardPanel.requestFocusInWindow();

            final var space = gameService.getSpaces().get(row).get(col);
            if (space.isFixed()) {
                showMessage("Esa celda es fija y no se puede modificar.");
                refreshUi();
                return;
            }

            showMessage("Celda seleccionada: pulsa 1-9 para escribir o Backspace para borrar.");
            refreshUi();
        });

        controlPanel = new ControlPanel();
        controlPanel.setPreferredSize(new Dimension(330, 620));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
        add(footerLabel, BorderLayout.SOUTH);

        wireActions();
        refreshUi();
        bindDeleteKey();
        bindNumberKeys();
    }

    private JPanel buildHeader() {
        final JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        final JPanel textGroup = new JPanel(new GridLayout(2, 1));
        textGroup.setOpaque(false);
        textGroup.add(titleLabel);
        textGroup.add(boardStateLabel);

        header.add(textGroup, BorderLayout.WEST);
        return header;
    }

    private JPanel buildCenter() {
        final JPanel center = new JPanel(new BorderLayout(14, 14));
        center.setOpaque(false);

        final JPanel boardWrapper = new JPanel(new BorderLayout());
        boardWrapper.setOpaque(true);
        boardWrapper.setBackground(Color.WHITE);
        boardWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(198, 206, 221), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        boardWrapper.add(boardPanel, BorderLayout.CENTER);

        center.add(boardWrapper, BorderLayout.CENTER);
        return center;
    }

    private void wireActions() {
        controlPanel.getStartButton().addActionListener(event -> {
            gameService.startNewGame();
            boardPanel.clearSelection();
            refreshUi();
            showMessage("Nuevo juego iniciado.");
        });

        controlPanel.getRemoveButton().addActionListener(event -> handleRemoveNumber());
        controlPanel.getClearButton().addActionListener(event -> {
            if (!ensureBoardStarted()) {
                return;
            }
            gameService.clearBoard();
            boardPanel.clearSelection();
            refreshUi();
            showMessage("Tablero limpio, se conservaron los números fijos.");
        });
        controlPanel.getFinishButton().addActionListener(event -> handleFinishGame());
    }

    private void bindDeleteKey() {
        boardPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke("BACK_SPACE"), "deleteSelectedCell");

        boardPanel.getActionMap().put("deleteSelectedCell", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                handleRemoveNumber();
            }
        });
    }

    private void bindNumberKeys() {
        final var inputMap = boardPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final var actionMap = boardPanel.getActionMap();

        for (int i = 1; i <= 9; i++) {
            final String actionKey = "place" + i;
            final KeyStroke ksTyped = KeyStroke.getKeyStroke((char) ('0' + i));
            final KeyStroke ksNumpad = KeyStroke.getKeyStroke("pressed NUMPAD" + i);
            inputMap.put(ksTyped, actionKey);
            inputMap.put(ksNumpad, actionKey);

            final int value = i;
            actionMap.put(actionKey, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!ensureBoardStarted()) {
                        return;
                    }
                    if (!boardPanel.hasSelection()) {
                        showMessage("Selecciona una celda primero.");
                        return;
                    }
                    final int r = boardPanel.getSelectedRow();
                    final int c = boardPanel.getSelectedCol();
                    final var space = gameService.getSpaces().get(r).get(c);
                    if (space.isFixed()) {
                        showMessage("No se puede modificar una celda fija.");
                        return;
                    }
                    if (gameService.placeNumber(r, c, value)) {
                        refreshUi();
                        showMessage("Número " + value + " colocado.");
                        return;
                    }
                    showMessage("No se pudo colocar el número.");
                }
            });
        }
    }

    private void handleRemoveNumber() {
        if (!ensureBoardStarted()) {
            return;
        }

        if (!boardPanel.hasSelection()) {
            showMessage("Primero selecciona una celda del tablero.");
            return;
        }

        if (gameService.removeNumber(boardPanel.getSelectedRow(), boardPanel.getSelectedCol())) {
            refreshUi();
            showMessage("Número removido correctamente.");
            return;
        }

        showMessage("No se pudo remover el número. La celda es fija o ya estaba vacía.");
    }

    private void handleFinishGame() {
        if (!ensureBoardStarted()) {
            return;
        }

        if (gameService.canFinish()) {
            refreshUi();
            JOptionPane.showMessageDialog(this, "Parabéns, você terminou o jogo corretamente!", "Sudoku", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Todavía no puedes finalizar. Completa el tablero con su solución correcta.", "Sudoku", JOptionPane.WARNING_MESSAGE);
    }

    private boolean ensureBoardStarted() {
        if (!gameService.hasBoard()) {
            JOptionPane.showMessageDialog(this, "Primero inicia un nuevo juego.", "Sudoku", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    private Integer readField(final ui.custom.input.NumberText field, final String label) {
        final String text = field.getText() == null ? "" : field.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa el campo " + label + ".", "Sudoku", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "El campo " + label + " no es válido.", "Sudoku", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private void refreshUi() {
        if (gameService.hasBoard()) {
            boardPanel.render(gameService.getSpaces());
            if (boardPanel.hasSelection()) {
                boardPanel.selectCell(boardPanel.getSelectedRow(), boardPanel.getSelectedCol());
            }
            boardStateLabel.setText(buildStatusText());
            footerLabel.setText(buildFooterText());
        } else {
            boardPanel.removeAll();
            boardPanel.repaint();
            boardStateLabel.setText("Estado: juego no iniciado");
            footerLabel.setText("Pulsa 'Iniciar / reiniciar' para cargar el tablero base.");
        }
    }

    private String buildStatusText() {
        final GameStatusEnum status = gameService.getStatus();
        return "Estado: " + switch (status) {
            case NON_STARTED -> "no iniciado";
            case INCOMPLETE -> "incompleto";
            case COMPLETE -> "completo";
        } + " | Errores: " + (gameService.hasErrors() ? "sí" : "no");
    }

    private String buildFooterText() {
        if (!gameService.hasBoard()) {
            return "";
        }
        return gameService.canFinish()
                ? "El tablero está resuelto y listo para finalizar."
                : "Haz clic sobre una celda vacía para escribir el número directamente.";
    }

    private void showMessage(final String message) {
        controlPanel.getStatusLabel().setText(message);
    }

    private Integer parseBoardValue(final String input) {
        try {
            final int value = Integer.parseInt(input.trim());
            return value >= 1 && value <= 9 ? value : null;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private JLabel createTitleLabel() {
        final JLabel label = new JLabel("Sudoku Studio");
        label.setFont(new Font("Segoe UI", Font.BOLD, 34));
        label.setForeground(new Color(22, 28, 45));
        return label;
    }

    private JLabel createBoardStateLabel() {
        final JLabel label = new JLabel("Estado: juego no iniciado");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setForeground(new Color(91, 102, 129));
        return label;
    }

    private JLabel createFooterLabel() {
        final JLabel label = new JLabel("Pulsa 'Iniciar / reiniciar' para cargar el tablero base.");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(91, 102, 129));
        label.setBorder(BorderFactory.createEmptyBorder(8, 2, 0, 2));
        return label;
    }
}
