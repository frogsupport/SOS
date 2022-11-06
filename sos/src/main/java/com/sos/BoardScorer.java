package com.sos;

// This class handles all the logic for a simple and general SOS game's scoring logic
public class BoardScorer {

    private SosGame.Shape[][] grid;
    private int BOARDSIZE;

    public BoardScorer(SosGame.Shape[][] grid) {
        this.grid = grid;
        this.BOARDSIZE = grid.length;
    }

    // For a simple SosGame. A player wins if a single sos is generated, and so we return a bool true
    // here if a player scores
    public boolean hasWon(int row, int col, SosGame.Shape shape) {
        // Handles logic for a board size of 3
        if (BOARDSIZE == 3) {
            return (grid[row][0] == SosGame.Shape.S // 3-in-the-row
                    && grid[row][1] == SosGame.Shape.O && grid[row][2] == SosGame.Shape.S

                    || grid[0][col] == SosGame.Shape.S // 3-in-the-column
                    && grid[1][col] == SosGame.Shape.O && grid[2][col] == SosGame.Shape.S

                    || row == col // 3-in-the-diagonal
                    && grid[0][0] == SosGame.Shape.S && grid[1][1] == SosGame.Shape.O && grid[2][2] == SosGame.Shape.S

                    || row + col == 2 // 3-in-the-opposite-diagonal
                    && grid[0][2] == SosGame.Shape.S && grid[1][1] == SosGame.Shape.O && grid[2][0] == SosGame.Shape.S);
        }
        // Handles logic for BOARDSIZE > 3 and shape == 'S'
        else if (shape == SosGame.Shape.S) {
            // CASE: At least 3 spaces into the board in any direction
            if ((row - 2) >= 0 && (col - 2) >= 0 && (row + 3) <= BOARDSIZE && (col + 3) <= BOARDSIZE) {
                // Check in every direction S
                return (checkUpS(row, col)
                        || checkDownS(row, col)
                        || checkLeftS(row, col)
                        || checkRightS(row, col)
                        || checkUpperLeftDiagonalS(row, col)
                        || checkUpperRightDiagonalS(row, col)
                        || checkLowerLeftDiagonalS(row, col)
                        || checkLowerRightDiagonalS(row, col));
            }
            // CASE: Top left corner
            else if (row <= 1 && col <= 1) {
                // check down, right, and lower right diagonal
                return (checkDownS(row, col)
                        || checkRightS(row, col)
                        || checkLowerRightDiagonalS(row, col));
            }
            // CASE: Top right corner
            else if ((row - 2) <= -1 && (col + 2) >= BOARDSIZE) {
                // check left, down, and bottom left diagonal
                return (checkLeftS(row, col)
                        || checkDownS(row, col)
                        || checkLowerLeftDiagonalS(row, col));
            }
            // CASE: Bottom left corner
            else if ((row + 2) >= BOARDSIZE && (col - 2) <= -1) {
                // check right, up, and upper right diagonal
                return (checkRightS(row, col)
                        || checkUpS(row, col)
                        || checkUpperRightDiagonalS(row, col));
            }
            // CASE: Bottom right corner
            else if ((row + 2) >= BOARDSIZE && (col + 2) >= BOARDSIZE) {
                // check left, up, and upper left diagonal
                return (checkLeftS(row, col)
                        || checkUpS(row, col)
                        || checkUpperLeftDiagonalS(row, col));
            }
            // CASE: Bottom in between borders
            else if ((row + 2) >= BOARDSIZE && (col - 2) >= 0 && (col + 3) <= BOARDSIZE) {
                // Check left, right, up, top left diagonal, top right diagonal
                return (checkLeftS(row, col)
                        || checkRightS(row, col)
                        || checkUpS(row, col)
                        || checkUpperLeftDiagonalS(row, col)
                        || checkUpperRightDiagonalS(row, col));
            }
            // CASE: Top in between borders
            else if ((row - 1) <= 0 && (col - 2) >= 0 && (col + 3) <= BOARDSIZE)  {
                // Check left, right, down, lower left diagonal, lower right diagonal
                return (checkLeftS(row, col)
                        || checkRightS(row, col)
                        || checkDownS(row, col)
                        || checkLowerLeftDiagonalS(row, col)
                        || checkLowerRightDiagonalS(row, col));
            }
            // CASE: Left side in between top and bottom
            else if ((row - 2) >= 0 && (row + 3) <= BOARDSIZE && (col - 1) <= 0) {
                // Check up, down, right, upper right diagonal, lower right diagonal
                return (checkUpS(row, col)
                        || checkDownS(row, col)
                        || checkRightS(row, col)
                        || checkUpperRightDiagonalS(row, col)
                        || checkLowerRightDiagonalS(row, col));
            }
            // CASE: Right side in between top and bottom
            else if ((row - 2) >= 0 && (row + 3) <= BOARDSIZE && (col + 2) >= BOARDSIZE) {
                // Check up, down, left, upper left diagonal, lower left diagonal
                return (checkUpS(row, col)
                        || checkDownS(row, col)
                        || checkLeftS(row, col)
                        || checkUpperLeftDiagonalS(row, col)
                        || checkLowerLeftDiagonalS(row, col));
            }
        }
        // Handles logic for BOARDSIZE > 3 and shape == 'O'
        else if (shape == SosGame.Shape.O)
        {
            // CASE: At least 2 spaces into the board in any direction
            if ((row - 1) >= 0 && (col - 1) >= 0 && (col + 2) <= BOARDSIZE && (row + 2) <= BOARDSIZE) {
                return (checkRowO(row, col)
                        || checkColO(row, col)
                        || checkBackSlashDiagonalO(row, col)
                        || checkForwardSlashDiagonalO(row, col));
            }
            // CASE: On the top edge
            else if (row == 0 && (col - 1) >= 0 && (col + 2) <= BOARDSIZE) {
                // Check row
                return(checkRowO(row, col));
            }
            // CASE: On the bottom edge
            else if (row == (BOARDSIZE - 1) && (col - 1) >= 0 && (col + 2) <= BOARDSIZE) {
                return(checkRowO(row, col));
            }
            // CASE: On the left edge
            else if (col == 0 && (row - 1) >= 0 && (row + 2) <= BOARDSIZE) {
                return (checkColO(row, col));
            }
            // CASE: On the right edge
            else if (col == (BOARDSIZE - 1) && (row - 1) >= 0 && (row + 2) <= BOARDSIZE) {
                return (checkColO(row, col));
            }
        }

        return false;
    }

    // For a general game, SOS's can continue to be scored until the board is filled.
    // This returns an int so that if we score multiple SOS's in one turn, they will all
    // be counted and added to the score.
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

    private boolean checkBackSlashDiagonalO(int row, int col) {
        return (grid[row - 1][col - 1] == SosGame.Shape.S && grid[row + 1][col + 1] == SosGame.Shape.S);
    }

    private boolean checkForwardSlashDiagonalO(int row, int col) {
        return (grid[row + 1][col - 1] == SosGame.Shape.S && grid[row - 1][col + 1] == SosGame.Shape.S);
    }

    private boolean checkColO(int row, int col) {
        return (grid[row - 1][col] == SosGame.Shape.S && grid[row + 1][col] == SosGame.Shape.S);
    }

    private boolean checkRowO(int row, int col) {
        return (grid[row][col - 1] == SosGame.Shape.S && grid[row][col + 1] == SosGame.Shape.S);
    }

    private boolean checkLowerLeftDiagonalS(int row, int col) {
        return (grid[row + 2][col - 2] == SosGame.Shape.S && grid[row + 1][col - 1] == SosGame.Shape.O);
    }

    private boolean checkUpperRightDiagonalS(int row, int col) {
        return (grid[row - 2][col + 2] == SosGame.Shape.S && grid[row - 1][col + 1] == SosGame.Shape.O);
    }

    private boolean checkUpperLeftDiagonalS(int row, int col) {
        return (grid[row - 2][col - 2] == SosGame.Shape.S && grid[row - 1][col - 1] == SosGame.Shape.O);
    }

    private boolean checkLowerRightDiagonalS(int row, int col) {
        return (grid[row + 2][col + 2] == SosGame.Shape.S && grid[row + 1][col + 1] == SosGame.Shape.O);
    }

    private boolean checkUpS(int row, int col) {
        return (grid[row - 2][col] == SosGame.Shape.S && grid[row - 1][col] == SosGame.Shape.O);
    }

    private boolean checkDownS(int row, int col) {
        return (grid[row + 2][col] == SosGame.Shape.S && grid[row + 1][col] == SosGame.Shape.O);
    }

    private boolean checkLeftS(int row, int col) {
        return (grid[row][col - 2] == SosGame.Shape.S && grid[row][col - 1] == SosGame.Shape.O);
    }

    private boolean checkRightS(int row, int col) {
        return (grid[row][col + 2] == SosGame.Shape.S && grid[row][col + 1] == SosGame.Shape.O);
    }
}
