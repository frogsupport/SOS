package com.sos;

// The base class used by SimpleSosGame and GeneralSosGame. Contains the shared functionality of the two classes.
public abstract class SosGame {
    public enum Shape {EMPTY, S, O}
    public enum Turn {BLUE, RED}
    public enum PlayerType {HUMAN, COMPUTER}
    public enum GameStatus {PLAYING, DRAW, BLUE_WON, RED_WON}
    protected int BOARDSIZE;
    protected Shape[][] grid;
    protected Turn turn;
    protected GameStatus currentGameStatus;
    protected int bluePlayerScore;
    protected int redPlayerScore;
    protected SosGame.PlayerType bluePlayerType;
    protected SosGame.PlayerType redPlayerType;

    public abstract boolean makeMove(int row, int column, Shape shape);

    public SosGame(int boardSize) {
        // Board size must be at least 3 for a valid sos game
        if (boardSize < 3) {
            BOARDSIZE = 3;
        }
        else {
            BOARDSIZE = boardSize;
        }

        grid = new Shape[BOARDSIZE][BOARDSIZE];
        initBoard();
        currentGameStatus = GameStatus.PLAYING;
        bluePlayerScore = 0;
        redPlayerScore = 0;
        bluePlayerType = PlayerType.HUMAN;
        redPlayerType = PlayerType.HUMAN;
    }

    public void initBoard() {
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int column = 0; column < BOARDSIZE; column++) {
                grid[row][column] = Shape.EMPTY;
            }
        }

        turn = Turn.BLUE;
    }

    public Shape getCell(int row, int column) {
        if (row >= 0 && row < BOARDSIZE && column >= 0 && column < BOARDSIZE) {
            return grid[row][column];
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                if (grid[row][col] != Shape.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isBoardFilled() {
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                if (grid[row][col] == Shape.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    public SosGame.PlayerType getBluePlayerType() { return bluePlayerType; }

    public SosGame.PlayerType getRedPlayerType() { return redPlayerType; }

    protected void changeTurn() {
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

    public int getBluePlayerScore() {
        return bluePlayerScore;
    }

    public int getRedPlayerScore() {
        return redPlayerScore;
    }
}
