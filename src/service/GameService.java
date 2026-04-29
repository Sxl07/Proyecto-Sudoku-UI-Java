package service;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.GameStatusEnum;
import model.Space;
import util.BoardTemplate;

public class GameService {
    private Board board;

    public void startNewGame() {
        board = BoardTemplate.createDefaultBoard();
    }

    public boolean hasBoard() {
        return board != null;
    }

    public Board getBoard() {
        return board;
    }

    public List<List<Space>> getSpaces() {
        ensureBoard();
        return board.getSpaces();
    }

    public boolean placeNumber(final int row, final int col, final int value) {
        ensureBoard();
        return board.changeValue(row, col, value);
    }

    public boolean removeNumber(final int row, final int col) {
        ensureBoard();
        return board.clearValue(row, col);
    }

    public void clearBoard() {
        ensureBoard();
        board.reset();
    }

    public GameStatusEnum getStatus() {
        ensureBoard();
        return board.getStatus();
    }

    public boolean hasErrors() {
        ensureBoard();
        return board.hasErrors();
    }

    public boolean canFinish() {
        ensureBoard();
        return board.gameIsFinished();
    }

    private void ensureBoard() {
        if (board == null) {
            throw new IllegalStateException("El juego todavía no fue iniciado.");
        }
    }
}
