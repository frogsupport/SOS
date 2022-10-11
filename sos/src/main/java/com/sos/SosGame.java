package com.sos;

public class SosGame {
    public enum Cell {EMPTY, S, O}
    public enum Turn {BLUE, RED}
    public enum GameType {SIMPLE, GENERAL}
    private static int TOTALROWS;
    private Cell[][] grid;
    private Turn turn;
    private GameType gameType = GameType.SIMPLE;

    public SosGame(int boardSize, GameType gameType) {
        TOTALROWS = boardSize;
        grid = new Cell[TOTALROWS][TOTALROWS];
        this.gameType = gameType;
        initBoard();
    }

    public void initBoard() {
        for (int row = 0; row < TOTALROWS; row++) {
            for (int column = 0; column < TOTALROWS; column++) {
                grid[row][column] = Cell.EMPTY;
            }
        }
        turn = Turn.BLUE;
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < TOTALROWS && column >= 0 && column < TOTALROWS) {
            return grid[row][column];
        } else {
            return null;
        }
    }

    public void makeMove(int row, int column, Cell shape) {
        if (row >= 0 && row < TOTALROWS && column >= 0 && column < TOTALROWS && grid[row][column] == Cell.EMPTY) {
            grid[row][column] = shape;
            turn = (turn == Turn.BLUE) ? Turn.RED : Turn.BLUE;
        }
    }

    // returns the size of the board
    public int getTotalRows() {
        return TOTALROWS;
    }

    public GameType getGameType() {
        return gameType;
    }

    public Turn getTurn() {
        return turn;
    }
}
