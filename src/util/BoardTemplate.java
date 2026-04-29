package util;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Space;

public final class BoardTemplate {
    private static final int[][] SOLUTION = {
        {5, 3, 4, 6, 7, 8, 9, 1, 2},
        {6, 7, 2, 1, 9, 5, 3, 4, 8},
        {1, 9, 8, 3, 4, 2, 5, 6, 7},
        {8, 5, 9, 7, 6, 1, 4, 2, 3},
        {4, 2, 6, 8, 5, 3, 7, 9, 1},
        {7, 1, 3, 9, 2, 4, 8, 5, 6},
        {9, 6, 1, 5, 3, 7, 2, 8, 4},
        {2, 8, 7, 4, 1, 9, 6, 3, 5},
        {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    private static final boolean[][] FIXED = {
        {true, true, false, true, false, false, true, false, true},
        {false, true, false, false, true, false, false, true, false},
        {true, false, true, false, false, true, false, false, true},
        {false, true, false, true, false, false, true, false, false},
        {true, false, false, false, true, false, false, false, true},
        {false, false, true, false, false, true, false, true, false},
        {true, false, false, true, false, false, true, false, false},
        {false, true, false, false, true, false, false, true, false},
        {true, false, true, false, false, true, false, false, true}
    };

    private BoardTemplate() {}

    public static Board createDefaultBoard() {
        return new Board(createDefaultSpaces());
    }

    public static List<List<Space>> createDefaultSpaces() {
        final List<List<Space>> spaces = new ArrayList<>();
        for (int row = 0; row < SOLUTION.length; row++) {
            final List<Space> currentRow = new ArrayList<>();
            for (int col = 0; col < SOLUTION[row].length; col++) {
                currentRow.add(new Space(SOLUTION[row][col], FIXED[row][col]));
            }
            spaces.add(currentRow);
        }
        return spaces;
    }
}
