package com.sos;

import java.util.Random;

public class AutoSimpleSosGame extends SimpleSosGame {
    private PlayerType BluePlayerType;
    private PlayerType RedPlayerType;

    AutoSimpleSosGame(int boardsize, PlayerType bluePlayerType, PlayerType redPlayerType) {
        super(boardsize);

        BluePlayerType = bluePlayerType;
        RedPlayerType = redPlayerType;
    }

    public SosMove makeAutoMove() {
        if (isEmpty()) {
            return makeRandomMove();
        }
        return makeMove();
    }

    private SosMove makeMove() {
        boolean haveS;
        boolean haveO;

        // Attempt to make an SOS in a row
        for (int i = 0; i < BOARDSIZE; i++) {
            haveS = false;
            haveO = false;
            for (int j = 0; j < BOARDSIZE; j++) {
                // S
                if (grid[i][j] == Shape.S && !haveS) {
                    haveS = true;
                }
                // SS
                else if (haveS && grid[i][j] == Shape.S) {
                    haveS = false;
                }
                // SO
                else if (haveS && grid[i][j] == Shape.O) {
                    haveO = true;
                }
                // SOO
                else if (haveS && haveO && grid[i][j] == Shape.O) {
                    haveS = false;
                    haveO = false;
                }
                // SOS
                else if(haveS && haveO && grid[i][j] == Shape.EMPTY) {
                    return new SosMove(i, j, Shape.S);
                }
                else {
                    haveS = false;
                    haveO = false;
                }
            }
        }

        // Attempt to make an SOS in a column
        for (int j = 0; j < BOARDSIZE; j++) {
            haveS = false;
            haveO = false;
            for (int i = 0; i < BOARDSIZE; i++) {
                // S
                if (grid[i][j] == Shape.S && !haveS) {
                    haveS = true;
                }
                // SS
                else if (haveS && grid[i][j] == Shape.S) {
                    haveS = false;
                }
                // SO
                else if (haveS && grid[i][j] == Shape.O) {
                    haveO = true;
                }
                // SOO
                else if (haveS && haveO && grid[i][j] == Shape.O) {
                    haveS = false;
                    haveO = false;
                }
                // SOS
                else if(haveS && haveO && grid[i][j] == Shape.EMPTY) {
                    return new SosMove(i, j, Shape.S);
                }
                else {
                    haveS = false;
                    haveO = false;
                }
            }
        }

        // If no SOS, then try to make an SO in a row
        for (int i = 0; i < BOARDSIZE; i++) {
            haveS = false;
            for (int j = 0; j < BOARDSIZE; j++) {
                // S
                if (grid[i][j] == Shape.S && !haveS) {
                    haveS = true;
                }
                // SS
                else if (haveS && grid[i][j] == Shape.S) {
                    haveS = false;
                }
                // SO
                else if (haveS && grid[i][j] == Shape.EMPTY) {
                    return new SosMove(i, j, Shape.O);
                }
                else {
                    haveS = false;
                }
            }
        }

        // If no SOS, then try to make an SO in a column
        for (int j = 0; j < BOARDSIZE; j++) {
            haveS = false;
            for (int i = 0; i < BOARDSIZE; i++) {
                // S
                if (grid[i][j] == Shape.S && !haveS) {
                    haveS = true;
                }
                // SS
                else if (haveS && grid[i][j] == Shape.S) {
                    haveS = false;
                }
                // SO
                else if (haveS && grid[i][j] == Shape.EMPTY) {
                    return new SosMove(i, j, Shape.O);
                }
                else {
                    haveS = false;
                }
            }
        }

        return makeRandomMove();
    }

    private SosMove makeRandomMove() {
        Random random = new Random();
        int targetMove = random.nextInt(getNumberOfEmptyCells());
        int index=0;
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                if (grid[row][col] == Shape.EMPTY) {
                    if (targetMove == index) {
                        int randomInt = random.nextInt(2);
                        Shape randomShape = (randomInt == 0) ? Shape.S : Shape.O;
                        return new SosMove(row, col, randomShape);
                    } else
                        index++;
                }
            }
        }
        return makeRandomMove();
    }

    private int getNumberOfEmptyCells() {
        int numberOfEmptyCells = 0;
        for (int row = 0; row < BOARDSIZE; ++row) {
            for (int col = 0; col < BOARDSIZE; ++col) {
                if (grid[row][col] == Shape.EMPTY) {
                    numberOfEmptyCells++;
                }
            }
        }
        return numberOfEmptyCells;
    }

    public PlayerType getBluePlayerType() { return BluePlayerType; }

    public PlayerType getRedPlayerType() { return RedPlayerType; }
}
