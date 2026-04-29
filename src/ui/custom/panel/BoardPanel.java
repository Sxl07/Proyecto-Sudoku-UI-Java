package ui.custom.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Space;

public class BoardPanel extends JPanel {
    private static final Color BOARD_BACKGROUND = new Color(245, 247, 252);
    private static final Color FIXED_BACKGROUND = new Color(231, 239, 255);
    private static final Color VALUE_BACKGROUND = Color.WHITE;
    private static final Color ERROR_FOREGROUND = new Color(176, 41, 41);
    private static final Color FIXED_FOREGROUND = new Color(28, 66, 143);
    private static final Color SELECTED_BACKGROUND = new Color(217, 232, 255);
    private static final Color HEADER_BACKGROUND = new Color(228, 233, 242);
    private static final Color HEADER_FOREGROUND = new Color(85, 96, 122);
    private static final Color SELECTION_BORDER = new Color(41, 98, 255);

    private final JButton[][] cells = new JButton[9][9];
    private final Space[][] renderedSpaces = new Space[9][9];
    private CellClickListener cellClickListener;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public BoardPanel() {
        setLayout(new GridLayout(10, 10, 2, 2));
        setBackground(BOARD_BACKGROUND);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(198, 206, 221), 2),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));
    }

    public void setCellClickListener(final CellClickListener cellClickListener) {
        this.cellClickListener = cellClickListener;
    }

    public void render(final List<List<Space>> spaces) {
        removeAll();

        add(createCornerHeader());
        for (int col = 0; col < 9; col++) {
            add(createHeaderCell(String.valueOf(col + 1)));
        }

        for (int row = 0; row < spaces.size(); row++) {
            add(createHeaderCell(String.valueOf(row + 1)));
            for (int col = 0; col < spaces.get(row).size(); col++) {
                final Space space = spaces.get(row).get(col);
                renderedSpaces[row][col] = space;
                final JButton cell = createCell(space, row, col);
                cells[row][col] = cell;
                add(cell);
            }
        }

        refreshSelection();
        revalidate();
        repaint();
    }

    public void selectCell(final int row, final int col) {
        selectedRow = row;
        selectedCol = col;
        refreshSelection();
    }

    public boolean hasSelection() {
        return selectedRow >= 0 && selectedCol >= 0;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getSelectedCol() {
        return selectedCol;
    }

    public void clearSelection() {
        selectedRow = -1;
        selectedCol = -1;
        refreshSelection();
    }

    private JButton createCell(final Space space, final int row, final int col) {
        final JButton cell = new JButton(centerValue(space));
        cell.setOpaque(true);
        cell.setFocusPainted(false);
        cell.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cell.setBackground(space.isFixed() ? FIXED_BACKGROUND : VALUE_BACKGROUND);
        cell.setForeground(space.isFixed() ? FIXED_FOREGROUND : new Color(48, 54, 71));
        cell.setMargin(new java.awt.Insets(0, 0, 0, 0));

        if (!space.isFixed() && space.getActual() != null && !space.getActual().equals(space.getExpected())) {
            cell.setForeground(ERROR_FOREGROUND);
        }

        cell.addActionListener(event -> {
            if (cellClickListener != null) {
                cellClickListener.onCellClicked(row, col);
            }
        });

        applyBorders(cell, row, col);
        return cell;
    }

    private JButton createHeaderCell(final String text) {
        final JButton header = new JButton(text);
        header.setEnabled(false);
        header.setFocusPainted(false);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(HEADER_BACKGROUND);
        header.setForeground(HEADER_FOREGROUND);
        header.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(198, 206, 221)));
        return header;
    }

    private JButton createCornerHeader() {
        return createHeaderCell("");
    }

    private void applyBorders(final JButton cell, final int row, final int col) {
        final int top = row % 3 == 0 ? 2 : 1;
        final int left = col % 3 == 0 ? 2 : 1;
        final int bottom = row == 8 ? 2 : (row % 3 == 2 ? 2 : 1);
        final int right = col == 8 ? 2 : (col % 3 == 2 ? 2 : 1);

        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, new Color(180, 189, 206)));
    }

    private String centerValue(final Space space) {
        return space.getActual() == null ? "" : String.valueOf(space.getActual());
    }

    private void refreshSelection() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                final JButton cell = cells[row][col];
                if (cell == null) {
                    continue;
                }

                final Space space = renderedSpaces[row][col];
                if (space == null) {
                    continue;
                }

                final Color baseBackground = space.isFixed() ? FIXED_BACKGROUND : VALUE_BACKGROUND;
                final Color baseForeground;
                if (space.isFixed()) {
                    baseForeground = FIXED_FOREGROUND;
                } else if (space.getActual() != null && !space.getActual().equals(space.getExpected())) {
                    baseForeground = ERROR_FOREGROUND;
                } else {
                    baseForeground = new Color(48, 54, 71);
                }

                cell.setBackground(baseBackground);
                cell.setForeground(baseForeground);
                applyBorders(cell, row, col);

                if (row == selectedRow && col == selectedCol) {
                    cell.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(SELECTION_BORDER, 3),
                            BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 189, 206))));
                    cell.setBackground(SELECTED_BACKGROUND);
                }
            }
        }
    }

    @FunctionalInterface
    public interface CellClickListener {
        void onCellClicked(int row, int col);
    }
}
