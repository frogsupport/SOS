package com.sos;

import java.util.Random;

public class AutoMove {
    private SosGame.Shape[][] grid;
    private int BOARDSIZE;

    public AutoMove(SosGame.Shape[][] grid) {
        this.grid = grid;
        BOARDSIZE = grid.length;
    }

    protected SosMove makeMove() {
        boolean haveS;
        boolean haveO;

        // Attempt to make an SOS in a row
        for (int i = 0; i < BOARDSIZE; i++) {
            haveS = false;
            haveO = false;
            for (int j = 0; j < BOARDSIZE; j++) {
                // S
                if (grid[i][j] == SosGame.Shape.S && !haveS) {
                    haveS = true;
                }
                // SS
                else if (haveS && grid[i][j] == SosGame.Shape.S) {
                    haveS = false;
                }
                // SO
                else if (haveS && grid[i][j] == SosGame.Shape.O) {
                    haveO = true;
                }
                // SOO
                else if (haveS && haveO && grid[i][j] == SosGame.Shape.O) {
                    haveS = false;
                    haveO = false;
                }
                // SOS
                else if(haveS && haveO && grid[i][j] == SosGame.Shape.EMPTY) {
                    return new SosMove(i, j, SosGame.Shape.S);
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
                if (grid[i][j] == SosGame.Shape.S && !haveS) {
                    haveS = true;
                }
                // SS
                else if (haveS && grid[i][j] == SosGame.Shape.S) {
                    haveS = false;
                }
                // SO
                else if (haveS && grid[i][j] == SosGame.Shape.O) {
                    haveO = true;
                }
                // SOO
                else if (haveS && haveO && grid[i][j] == SosGame.Shape.O) {
                    haveS = false;
                    haveO = false;
                }
                // SOS
                else if(haveS && haveO && grid[i][j] == SosGame.Shape.EMPTY) {
                    return new SosMove(i, j, SosGame.Shape.S);
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
                if (grid[i][j] == SosGame.Shape.S && !haveS) {
                    haveS = true;
                }
                // SS
                else if (haveS && grid[i][j] == SosGame.Shape.S) {
                    haveS = false;
                }
                // SO
                else if (haveS && grid[i][j] == SosGame.Shape.EMPTY) {
                    return new SosMove(i, j, SosGame.Shape.O);
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
                if (grid[i][j] == SosGame.Shape.S && !haveS) {
                    haveS = true;
                }
                // SS
                else if (haveS && grid[i][j] == SosGame.Shape.S) {
                    haveS = false;
                }
                // SO
                else if (haveS && grid[i][j] == SosGame.Shape.EMPTY) {
                    return new SosMove(i, j, SosGame.Shape.O);
                }
                else {
                    haveS = false;
                }
            }
        }

        return makeRandomMove();
    }

    protected SosMove makeRandomMove() {
        Random random = new Random();
        int targetMove = random.nextInt(getNumberOfEmptyCells());
        int index=0;
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                if (grid[row][col] == SosGame.Shape.EMPTY) {
                    if (targetMove == index) {
                        int randomInt = random.nextInt(2);
                        SosGame.Shape randomShape = (randomInt == 0) ? SosGame.Shape.S : SosGame.Shape.O;
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
                if (grid[row][col] == SosGame.Shape.EMPTY) {
                    numberOfEmptyCells++;
                }
            }
        }
        return numberOfEmptyCells;
    }

}
