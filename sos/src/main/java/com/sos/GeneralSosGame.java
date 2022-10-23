package com.sos;

public class GeneralSosGame implements SosGame {
    private static int BOARDSIZE;
    private Cell[][] grid;
    private Turn turn;

    public GeneralSosGame(int boardSize) {
        BOARDSIZE = boardSize;
        grid = new Cell[BOARDSIZE][BOARDSIZE];
        // this.gameType = gameType;
        initBoard();
    }

    public void initBoard() {
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int column = 0; column < BOARDSIZE; column++) {
                grid[row][column] = Cell.EMPTY;
            }
        }
        turn = Turn.BLUE;
    }

    public void makeMove(int row, int column, Cell shape) {
        if (row >= 0 && row < BOARDSIZE && column >= 0 && column < BOARDSIZE && grid[row][column] == Cell.EMPTY) {
            grid[row][column] = shape;
            turn = (turn == Turn.BLUE) ? Turn.RED : Turn.BLUE;
        }
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < BOARDSIZE && column >= 0 && column < BOARDSIZE) {
            return grid[row][column];
        } else {
            return null;
        }
    }

    // returns the size of the board
    public int getBoardSize() {
        return BOARDSIZE;
    }

    public Turn getTurn() {
        return turn;
    }
}
