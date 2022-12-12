package com.sos.sos;

import com.sos.models.SosLineCoordinate;
import com.sos.models.SosMove;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Contains the base logic necessary to run an SOS game
public abstract class SosGame {
    public enum Shape {EMPTY, S, O}
    public enum Turn {BLUE, RED}
    public enum PlayerType {HUMAN, COMPUTER}
    public enum GameStatus {PLAYING, DRAW, BLUE_WON, RED_WON}
    private int BOARDSIZE;
    private Shape[][] grid;
    private List<SosLineCoordinate> lineCoordinates;
    private Turn turn;
    private GameStatus currentGameStatus;
    private int bluePlayerScore;
    private int redPlayerScore;
    private SosGame.PlayerType bluePlayerType;
    private SosGame.PlayerType redPlayerType;
    private SosMove lastAutoMove;
    private boolean isGameRecorded;
    private final String savedMovesFilePath = "lastPlayedGameSavedMoves.txt";

    protected abstract void updateGameStatus(int row, int col, Shape shape);

    public SosGame(int boardSize, boolean isGameRecorded) {
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
        lastAutoMove = null;
        this.isGameRecorded = isGameRecorded;

        // Delete the old saved moves file
        tryDeleteSavedMovesFile();
    }

    public boolean makeMove(int row, int column, Shape shape) {
        if (row >= 0 && row < BOARDSIZE && column >= 0 && column < BOARDSIZE && grid[row][column] == Shape.EMPTY && currentGameStatus == GameStatus.PLAYING) {
            grid[row][column] = shape;
            updateGameStatus(row, column, shape);
            return true;
        }

        return false;
    }

    protected void recordMove(int row, int col, Shape shape, Turn turn) {
        File savedMovesFile = new File(savedMovesFilePath);
        try {
            String moveToRecord = String.format("%s, %s, %s, %s \n", row, col, shape, turn);
            if (savedMovesFile.isFile()) {
                FileWriter fileWriter = new FileWriter(savedMovesFile, true);
                fileWriter.write(moveToRecord);
                fileWriter.close();
            }
            else {
                savedMovesFile.createNewFile();
                FileWriter fileWriter = new FileWriter(savedMovesFile);
                fileWriter.write(moveToRecord);
                fileWriter.close();
            }
        }
        catch (IOException exception) {
            System.out.println("An error occurred with the saved moves file");
        }
    }

    private void tryDeleteSavedMovesFile() {
        File savedMovesFile = new File(savedMovesFilePath);
        if (savedMovesFile.isFile()) {
            savedMovesFile.delete();
        }
    }

    private void initBoard() {
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

    public boolean makeAutoMove() {
        return false;
    }

    public void popLineCoordinate() {
        if (!lineCoordinates.isEmpty()) {
            lineCoordinates.remove(0);
        }
    }

    public boolean getIsGameRecorded() { return isGameRecorded; }

    public Shape[][] getGrid() { return grid; }

    public SosGame.PlayerType getBluePlayerType() { return bluePlayerType; }

    public void setBluePlayerType(PlayerType playerType) {
        bluePlayerType = playerType;
    }

    public SosGame.PlayerType getRedPlayerType() { return redPlayerType; }

    public void setRedPlayerType(PlayerType playerType) {
        redPlayerType = playerType;
    }

    protected void changeTurn() {
        turn = (turn == Turn.BLUE) ? Turn.RED : Turn.BLUE;
    }

    public GameStatus getCurrentGameStatus() {
        return currentGameStatus;
    }

    public void setCurrentGameStatus(GameStatus status) {
        currentGameStatus = status;
    }

    public int getBoardSize() {
        return BOARDSIZE;
    }

    public List<SosLineCoordinate> getLineCoordinates() { return lineCoordinates; }

    public void setLineCoordinates(List<SosLineCoordinate> coordinates) {
        lineCoordinates = coordinates;
    }

    public Turn getTurn() {
        return turn;
    }

    public int getBluePlayerScore() {
        return bluePlayerScore;
    }

    public void setBluePlayerScore(int score) {
        bluePlayerScore += score;
    }

    public int getRedPlayerScore() {
        return redPlayerScore;
    }

    public void setRedPlayerScore(int score) {
        redPlayerScore += score;
    }

    public SosMove getLastAutoMove() { return lastAutoMove; }

    public void setLastAutoMove(SosMove move) {
        lastAutoMove = move;
    }
}
