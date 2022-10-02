package sos.models;

public class Board {
    public enum Cell {EMPTY, S, O};
    private int boardSize;
    private Cell[][] grid;

    public Board(int size) {
        boardSize = size;
        grid = new Cell[boardSize][boardSize];

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                grid[row][col] = Cell.EMPTY;
            }
        }
    }

    public Cell getCell(int row, int col) {
        if (row < boardSize && row >= 0 && col < boardSize && col >= 0) {
            return grid[row][col];
        }
        else {
            return null;
        }
    }
}
