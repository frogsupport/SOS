package com.sos;

import java.util.ArrayList;
import java.util.List;

// This class handles all the logic for a simple and general SOS game's scoring logic, as well as adding the
// appropriate line coordinates to be drawn for winning SOS moves
public class SosBoardScorer {
    private List<SosLineCoordinate> lineCoordinates;
    private SosGame.Shape[][] grid;
    private int BOARDSIZE;

    public SosBoardScorer(SosGame.Shape[][] grid) {
        this.grid = grid;
        this.BOARDSIZE = grid.length;
        lineCoordinates = new ArrayList<>();
    }

    // For a general game, SOS's can continue to be scored until the board is filled.
    // This returns an int so that if we score multiple SOS's in one turn, they will all
    // be counted and added to the score.
    // This method can also be used for a simple sos game.
    public int hasScored(int row, int col, SosGame.Shape shape) {
        int score = 0;

        // Handles logic for a board size of 3
        if (BOARDSIZE == 3) {
            if (grid[row][0] == SosGame.Shape.S // 3-in-the-row
                    && grid[row][1] == SosGame.Shape.O && grid[row][2] == SosGame.Shape.S) {
                score++;
            }
            if (grid[0][col] == SosGame.Shape.S // 3-in-the-column
                    && grid[1][col] == SosGame.Shape.O && grid[2][col] == SosGame.Shape.S) {
                score++;
            }
            if (row == col // 3-in-the-diagonal
                    && grid[0][0] == SosGame.Shape.S && grid[1][1] == SosGame.Shape.O && grid[2][2] == SosGame.Shape.S) {
                score++;
            }
            if (row + col == 2 // 3-in-the-opposite-diagonal
                    && grid[0][2] == SosGame.Shape.S && grid[1][1] == SosGame.Shape.O && grid[2][0] == SosGame.Shape.S) {
                score++;
            }
        }
        // Handles logic for BOARDSIZE > 3 and shape == 'S'
        else if (shape == SosGame.Shape.S && BOARDSIZE > 3) {
            // CASE: At least 3 spaces into the board in any direction
            if ((row - 2) >= 0 && (col - 2) >= 0 && (row + 3) <= BOARDSIZE && (col + 3) <= BOARDSIZE) {
                // Check in every direction
                if (checkUpS(row, col))
                    score++;
                if (checkDownS(row, col))
                    score++;
                if (checkLeftS(row, col))
                    score++;
                if (checkRightS(row, col))
                    score++;
                if (checkUpperLeftDiagonalS(row, col))
                    score++;
                if (checkUpperRightDiagonalS(row, col))
                    score++;
                if (checkLowerLeftDiagonalS(row, col))
                    score++;
                if (checkLowerRightDiagonalS(row, col))
                    score++;
            }
            // CASE: Top left corner
            else if (row <= 1 && col <= 1) {
                // check down, right, and lower right diagonal
                if (checkDownS(row, col))
                    score++;
                if (checkRightS(row, col))
                    score++;
                if (checkLowerRightDiagonalS(row, col))
                    score++;
            }
            // CASE: Top right corner
            else if ((row - 2) <= -1 && (col + 2) >= BOARDSIZE) {
                // check left, down, and bottom left diagonal
                if (checkLeftS(row, col))
                    score++;
                if (checkDownS(row, col))
                    score++;
                if (checkLowerLeftDiagonalS(row, col))
                    score++;
            }
            // CASE: Bottom left corner
            else if ((row + 2) >= BOARDSIZE && (col - 2) <= -1) {
                // check right, up, and upper right diagonal
                if (checkRightS(row, col))
                    score++;
                if (checkUpS(row, col))
                    score++;
                if (checkUpperRightDiagonalS(row, col))
                    score++;
            }
            // CASE: Bottom right corner
            else if ((row + 2) >= BOARDSIZE && (col + 2) >= BOARDSIZE) {
                // check left, up, and upper left diagonal
                if (checkLeftS(row, col))
                    score++;
                if (checkUpS(row, col))
                    score++;
                if (checkUpperLeftDiagonalS(row, col))
                    score++;
            }
            // CASE: Bottom in between borders
            else if ((row + 2) >= BOARDSIZE && (col - 2) >= 0 && (col + 3) <= BOARDSIZE) {
                // Check left, right, up, top left diagonal, top right diagonal
                if (checkLeftS(row, col))
                    score++;
                if (checkRightS(row, col))
                    score++;
                if (checkUpS(row, col))
                    score++;
                if (checkUpperLeftDiagonalS(row, col))
                    score++;
                if (checkUpperRightDiagonalS(row, col))
                    score++;
            }
            // CASE: Top in between borders
            else if ((row - 1) <= 0 && (col - 2) >= 0 && (col + 3) <= BOARDSIZE)  {
                // Check left, right, down, lower left diagonal, lower right diagonal
                if (checkLeftS(row, col))
                    score++;
                if (checkRightS(row, col))
                    score++;
                if (checkDownS(row, col))
                    score++;
                if (checkLowerLeftDiagonalS(row, col))
                    score++;
                if (checkLowerRightDiagonalS(row, col))
                    score++;
            }
            // CASE: Left side in between top and bottom
            else if ((row - 2) >= 0 && (row + 3) <= BOARDSIZE && (col - 1) <= 0) {
                // Check up, down, right, upper right diagonal, lower right diagonal
                if (checkUpS(row, col))
                    score++;
                if (checkDownS(row, col))
                    score++;
                if (checkRightS(row, col))
                    score++;
                if (checkUpperRightDiagonalS(row, col))
                    score++;
                if (checkLowerRightDiagonalS(row, col))
                    score++;
            }
            // CASE: Right side in between top and bottom
            else if ((row - 2) >= 0 && (row + 3) <= BOARDSIZE && (col + 2) >= BOARDSIZE) {
                // Check up, down, left, upper left diagonal, lower left diagonal
                if (checkUpS(row, col))
                    score++;
                if (checkDownS(row, col))
                    score++;
                if (checkLeftS(row, col))
                    score++;
                if (checkUpperLeftDiagonalS(row, col))
                    score++;
                if (checkLowerLeftDiagonalS(row, col))
                    score++;
            }
        }
        // Handles logic for BOARDSIZE > 3 and shape == 'O'
        else if (shape == SosGame.Shape.O && BOARDSIZE > 3)
        {
            // CASE: At least 2 spaces into the board in any direction
            if ((row - 1) >= 0 && (col - 1) >= 0 && (col + 2) <= BOARDSIZE && (row + 2) <= BOARDSIZE) {
                if (checkRowO(row, col))
                    score++;
                if (checkColO(row, col))
                    score++;
                if (checkBackSlashDiagonalO(row, col))
                    score++;
                if (checkForwardSlashDiagonalO(row, col))
                    score++;
            }
            // CASE: On the top edge
            else if (row == 0 && (col - 1) >= 0 && (col + 2) <= BOARDSIZE) {
                // Check row
                if (checkRowO(row, col))
                    score++;
            }
            // CASE: On the bottom edge
            else if (row == (BOARDSIZE - 1) && (col - 1) >= 0 && (col + 2) <= BOARDSIZE) {
                if (checkRowO(row, col))
                    score++;
            }
            // CASE: On the left edge
            else if (col == 0 && (row - 1) >= 0 && (row + 2) <= BOARDSIZE) {
                if (checkColO(row, col))
                    score++;
            }
            // CASE: On the right edge
            else if (col == (BOARDSIZE - 1) && (row - 1) >= 0 && (row + 2) <= BOARDSIZE) {
                if (checkColO(row, col))
                    score++;
            }
        }
        return score;
    }

    // TODO: See if this method works for adding lines for every case
    private boolean checkBackSlashDiagonalO(int row, int col) {
        // return (grid[row - 1][col - 1] == SosGame.Shape.S && grid[row + 1][col + 1] == SosGame.Shape.S);
        if (grid[row - 1][col - 1] == SosGame.Shape.S && grid[row + 1][col + 1] == SosGame.Shape.S) {
            addBackslashDiagonalLineCoordinate(row - 1, col - 1);
            addBackslashDiagonalLineCoordinate(row, col);
            addBackslashDiagonalLineCoordinate(row + 1, col + 1);

            // lineCoordinates.add(new SosLineCoordinate(row - 1, col - 1, SosLineCoordinate.LineDirection.BACKSLASH_DIAGONAL));
            // lineCoordinates.add(new SosLineCoordinate(row, col, SosLineCoordinate.LineDirection.BACKSLASH_DIAGONAL));
            // lineCoordinates.add(new SosLineCoordinate(row + 1, col + 1, SosLineCoordinate.LineDirection.BACKSLASH_DIAGONAL));
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkForwardSlashDiagonalO(int row, int col) {
        // return (grid[row + 1][col - 1] == SosGame.Shape.S && grid[row - 1][col + 1] == SosGame.Shape.S);
        if (grid[row + 1][col - 1] == SosGame.Shape.S && grid[row - 1][col + 1] == SosGame.Shape.S) {
            addForwardslashDiagonalLineCoordinate(row + 1, col - 1);
            addForwardslashDiagonalLineCoordinate(row, col);
            addForwardslashDiagonalLineCoordinate(row - 1, col + 1);

            // lineCoordinates.add(new SosLineCoordinate(row + 1, col - 1, SosLineCoordinate.LineDirection.FORWARDSLASH_DIAGONAL));
            // lineCoordinates.add(new SosLineCoordinate(row, col, SosLineCoordinate.LineDirection.FORWARDSLASH_DIAGONAL));
            // lineCoordinates.add(new SosLineCoordinate(row - 1, col + 1, SosLineCoordinate.LineDirection.FORWARDSLASH_DIAGONAL));
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkColO(int row, int col) {
        // return (grid[row - 1][col] == SosGame.Shape.S && grid[row + 1][col] == SosGame.Shape.S);
        if (grid[row - 1][col] == SosGame.Shape.S && grid[row + 1][col] == SosGame.Shape.S) {
            addVerticalLineCoordinate(row - 1, col);
            addVerticalLineCoordinate(row, col);
            addVerticalLineCoordinate(row + 1, col);

            // lineCoordinates.add(new SosLineCoordinate(row - 1, col, SosLineCoordinate.LineDirection.VERTICAL));
            // lineCoordinates.add(new SosLineCoordinate(row, col, SosLineCoordinate.LineDirection.VERTICAL));
            // lineCoordinates.add(new SosLineCoordinate(row + 1, col, SosLineCoordinate.LineDirection.VERTICAL));
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkRowO(int row, int col) {
        // return (grid[row][col - 1] == SosGame.Shape.S && grid[row][col + 1] == SosGame.Shape.S);
        if (grid[row][col - 1] == SosGame.Shape.S && grid[row][col + 1] == SosGame.Shape.S) {
            addHorizontalLineCoordinate(row, col - 1);
            addHorizontalLineCoordinate(row, col);
            addHorizontalLineCoordinate(row, col + 1);

            // lineCoordinates.add(new SosLineCoordinate(row , col - 1, SosLineCoordinate.LineDirection.HORIZONTAL));
            // lineCoordinates.add(new SosLineCoordinate(row, col, SosLineCoordinate.LineDirection.HORIZONTAL));
            // lineCoordinates.add(new SosLineCoordinate(row, col + 1, SosLineCoordinate.LineDirection.HORIZONTAL));
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkLowerLeftDiagonalS(int row, int col) {
        // return (grid[row + 2][col - 2] == SosGame.Shape.S && grid[row + 1][col - 1] == SosGame.Shape.O);
        if (grid[row + 2][col - 2] == SosGame.Shape.S && grid[row + 1][col - 1] == SosGame.Shape.O) {
            addForwardslashDiagonalLineCoordinate(row + 2, col - 2);
            addForwardslashDiagonalLineCoordinate(row + 1, col - 1);
            addForwardslashDiagonalLineCoordinate(row, col);

            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkUpperRightDiagonalS(int row, int col) {
        // return (grid[row - 2][col + 2] == SosGame.Shape.S && grid[row - 1][col + 1] == SosGame.Shape.O);
        if (grid[row - 2][col + 2] == SosGame.Shape.S && grid[row - 1][col + 1] == SosGame.Shape.O) {
            addForwardslashDiagonalLineCoordinate(row - 2, col + 2);
            addForwardslashDiagonalLineCoordinate(row - 1, col + 1);
            addForwardslashDiagonalLineCoordinate(row, col);

            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkUpperLeftDiagonalS(int row, int col) {
        // return (grid[row - 2][col - 2] == SosGame.Shape.S && grid[row - 1][col - 1] == SosGame.Shape.O);
        if (grid[row - 2][col - 2] == SosGame.Shape.S && grid[row - 1][col - 1] == SosGame.Shape.O) {
            addBackslashDiagonalLineCoordinate(row - 2, col - 2);
            addBackslashDiagonalLineCoordinate(row - 1, col - 1);
            addBackslashDiagonalLineCoordinate(row, col);

            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkLowerRightDiagonalS(int row, int col) {
        // return (grid[row + 2][col + 2] == SosGame.Shape.S && grid[row + 1][col + 1] == SosGame.Shape.O);
        if (grid[row + 2][col + 2] == SosGame.Shape.S && grid[row + 1][col + 1] == SosGame.Shape.O) {
            addBackslashDiagonalLineCoordinate(row + 2, col + 2);
            addBackslashDiagonalLineCoordinate(row + 1, col + 1);
            addBackslashDiagonalLineCoordinate(row, col);

            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkUpS(int row, int col) {
        // return (grid[row - 2][col] == SosGame.Shape.S && grid[row - 1][col] == SosGame.Shape.O);
        if (grid[row - 2][col] == SosGame.Shape.S && grid[row - 1][col] == SosGame.Shape.O) {
            addVerticalLineCoordinate(row - 2, col);
            addVerticalLineCoordinate(row - 1, col);
            addVerticalLineCoordinate(row, col);

            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkDownS(int row, int col) {
        // return (grid[row + 2][col] == SosGame.Shape.S && grid[row + 1][col] == SosGame.Shape.O);
        if (grid[row + 2][col] == SosGame.Shape.S && grid[row + 1][col] == SosGame.Shape.O) {
            addVerticalLineCoordinate(row + 2, col);
            addVerticalLineCoordinate(row + 1, col);
            addVerticalLineCoordinate(row, col);

            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkLeftS(int row, int col) {
        // return (grid[row][col - 2] == SosGame.Shape.S && grid[row][col - 1] == SosGame.Shape.O);
        if (grid[row][col - 2] == SosGame.Shape.S && grid[row][col - 1] == SosGame.Shape.O) {
            addHorizontalLineCoordinate(row, col - 2);
            addHorizontalLineCoordinate(row, col - 1);
            addHorizontalLineCoordinate(row, col);

            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkRightS(int row, int col) {
        // return (grid[row][col + 2] == SosGame.Shape.S && grid[row][col + 1] == SosGame.Shape.O);
        if (grid[row][col + 2] == SosGame.Shape.S && grid[row][col + 1] == SosGame.Shape.O) {
            addHorizontalLineCoordinate(row, col + 2);
            addHorizontalLineCoordinate(row, col + 1);
            addHorizontalLineCoordinate(row, col);

            return true;
        }
        else {
            return false;
        }
    }

    private void addForwardslashDiagonalLineCoordinate(int row, int col) {
        lineCoordinates.add(new SosLineCoordinate(row, col, SosLineCoordinate.LineDirection.FORWARDSLASH_DIAGONAL));
    }

    private void addBackslashDiagonalLineCoordinate(int row, int col) {
        lineCoordinates.add(new SosLineCoordinate(row, col, SosLineCoordinate.LineDirection.BACKSLASH_DIAGONAL));
    }

    private void addHorizontalLineCoordinate(int row, int col) {
        lineCoordinates.add(new SosLineCoordinate(row, col, SosLineCoordinate.LineDirection.HORIZONTAL));
    }

    private void addVerticalLineCoordinate(int row, int col) {
        lineCoordinates.add(new SosLineCoordinate(row, col, SosLineCoordinate.LineDirection.VERTICAL));
    }

    public List<SosLineCoordinate> getLineCoordinates() {
        return lineCoordinates;
    }
}
