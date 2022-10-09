package com.sos;

public class SosGame {
    public enum Cell {EMPTY, S, O, INVALID}
    public enum Turn {Blue, Red}
    private static int TOTALROWS;
    private static int TOTALCOLUMNS;

    private Cell[][] grid;
    private Turn turn;

    public SosGame(int boardSize) {
        TOTALROWS = boardSize;
        TOTALCOLUMNS = boardSize;
        grid = new Cell[TOTALROWS][TOTALCOLUMNS];
        initBoard();
    }

    public void initBoard() {
        for (int row = 0; row < TOTALROWS; row++) {
            for (int column = 0; column < TOTALCOLUMNS; column++) {
                grid[row][column] = Cell.EMPTY;
            }
        }
        turn = Turn.Blue;
    }

    public int getTotalRows() {
        return TOTALROWS;
    }

    public int getTotalColumns() {
        return TOTALCOLUMNS;
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < TOTALROWS && column >= 0 && column < TOTALCOLUMNS) {
            return grid[row][column];
        } else {
            return null;
        }
    }

    public Turn getTurn() {
        return turn;
    }

    public void makeMove(int row, int column) {
        if (row >= 0 && row < TOTALROWS && column >= 0 && column < TOTALCOLUMNS && grid[row][column] == Cell.EMPTY) {
            grid[row][column] = (turn == Turn.Blue) ? Cell.S : Cell.O;
            turn = (turn == Turn.Blue) ? Turn.Red : Turn.Blue;
        }
    }

}
