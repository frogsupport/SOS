package com.sos;

import java.util.ArrayList;
import java.util.List;

// Contains the base logic necessary to run an SOS game
public abstract class SosGame {
    public enum Shape {EMPTY, S, O}
    public enum Turn {BLUE, RED}
    public enum PlayerType {HUMAN, COMPUTER}
    public enum GameStatus {PLAYING, DRAW, BLUE_WON, RED_WON}
    protected int BOARDSIZE;
    protected Shape[][] grid;
    protected List<SosLineCoordinate> lineCoordinates;
    protected Turn turn;
    protected GameStatus currentGameStatus;
    protected int bluePlayerScore;
    protected int redPlayerScore;
    protected SosGame.PlayerType bluePlayerType;
    protected SosGame.PlayerType redPlayerType;

    protected abstract void updateGameStatus(int row, int col, Shape shape);

    public SosGame(int boardSize) {
        // Board size must be at least 3 for a valid sos game
        if (boardSize < 3) {
            BOARDSIZE = 3;
        }
        // Computer vs computer game will crash with general game bigger than around this size
        // The board is way too hard to see at this size as well and the game becomes almost
        // unenjoyable. If you want to crash the program though then remove this precaution
        else if (boardSize > 50) {
            BOARDSIZE = 50;
        }
        else {
            BOARDSIZE = boardSize;
        }

        grid = new Shape[BOARDSIZE][BOARDSIZE];
        lineCoordinates = new ArrayList<>();
        initBoard();
        currentGameStatus = GameStatus.PLAYING;
        bluePlayerScore = 0;
        redPlayerScore = 0;
        bluePlayerType = PlayerType.HUMAN;
        redPlayerType = PlayerType.HUMAN;
    }

    public boolean makeMove(int row, int column, Shape shape) {
        if (row >= 0 && row < BOARDSIZE && column >= 0 && column < BOARDSIZE && grid[row][column] == Shape.EMPTY) {
            grid[row][column] = shape;
            updateGameStatus(row, column, shape);
            return true;
        }

        return false;
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

    public SosMove makeAutoMove() {
        return new SosMove(-1, -1, Shape.EMPTY);
    }

    public void popLineCoordinate() {
        if (!lineCoordinates.isEmpty()) {
            lineCoordinates.remove(0);
        }
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

    public List<SosLineCoordinate> getLineCoordinates() { return lineCoordinates; }

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
