package com.sos;

public class GeneralSosGame implements SosGame {
    private static int BOARDSIZE;
    private Cell[][] grid;
    private Turn turn;
    private GameStatus currentGameStatus;

    public GeneralSosGame(int boardSize) {
        // Board size must be at least 3
        if (boardSize < 3) {
            BOARDSIZE = 3;
        }
        else {
            BOARDSIZE = boardSize;
        }

        grid = new Cell[BOARDSIZE][BOARDSIZE];
        // this.gameType = gameType;
        initBoard();
        currentGameStatus = GameStatus.PLAYING;
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

            if (hasWon()) {
                if (turn == Turn.BLUE) {
                    currentGameStatus = GameStatus.BLUE_WON;
                } else if (turn == Turn.RED) {
                    currentGameStatus = GameStatus.RED_WON;
                }
            }
            else {
                changeTurn();
            }
        }
    }

    private boolean hasWon() {
        if (grid[0][0] == Cell.S && grid[0][1] == Cell.O && grid[0][2] == Cell.S) {
            return true;
        }
        else {
            return false;
        }
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < BOARDSIZE && column >= 0 && column < BOARDSIZE) {
            return grid[row][column];
        } else {
            return null;
        }
    }

    private void changeTurn() {
        turn = (turn == Turn.BLUE) ? Turn.RED : Turn.BLUE;
    }

    public GameStatus getCurrentGameStatus() {
        return currentGameStatus;
    }

    public int getBoardSize() {
        return BOARDSIZE;
    }

    public Turn getTurn() {
        return turn;
    }
}
