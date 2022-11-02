package com.sos;

public class GeneralSosGame implements ISosGame {
    private static int BOARDSIZE;
    private Shape[][] grid;
    private Turn turn;
    private GameStatus currentGameStatus;
    private int BluePlayerScore;
    private int RedPlayerScore;

    public GeneralSosGame(int boardSize) {
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
        BluePlayerScore = 0;
        RedPlayerScore = 0;
    }

    public void initBoard() {
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int column = 0; column < BOARDSIZE; column++) {
                grid[row][column] = Shape.EMPTY;
            }
        }
        turn = Turn.BLUE;
    }

    // Returns true if the cell is empty and a valid move is made
    // Returns false if the given index is invalid, or if the cell is occupied
    public boolean makeMove(int row, int column, Shape shape) {
        if (row >= 0 && row < BOARDSIZE && column >= 0 && column < BOARDSIZE && grid[row][column] == Shape.EMPTY) {
            grid[row][column] = shape;
            updateGameStatus(row, column, shape);
            // changeTurn();
            return true;
        }

        return false;
    }

    // Updates the score if an SOS is made
    // Changes the current game status if there is a winner or a draw
    // Changes the turn if no SOS is made
    private void updateGameStatus(int row, int column, Shape shape) {
        int score = hasScored(row, column, shape);
        // If an SOS is scored, keep the turn the same and add the score
        if (score > 0) {
            if (turn == Turn.BLUE) {
                BluePlayerScore += score;
            } else if (turn == Turn.RED) {
                RedPlayerScore += score;
            }
        }
        // If no SOS is scored, then change the turn
        else {
            changeTurn();
        }

        // Check end of game status
        if (isBoardFilled() && BluePlayerScore > RedPlayerScore) {
            currentGameStatus = GameStatus.BLUE_WON;
        } else if (isBoardFilled() && RedPlayerScore > BluePlayerScore) {
            currentGameStatus = GameStatus.RED_WON;
        } else if (isBoardFilled() && BluePlayerScore == RedPlayerScore) {
            currentGameStatus = GameStatus.DRAW;
        }
    }

    // For a general game, SOS's can continue to be scored until the board is filled.
    // This returns an int so that if we score multiple SOS's in one turn, they will all
    // be counted and added to the score.
    private int hasScored(int row, int col, Shape shape) {
        int score = 0;

        // Handles logic for a board size of 3
        if (BOARDSIZE == 3) {
            if (grid[row][0] == Shape.S // 3-in-the-row
                    && grid[row][1] == Shape.O && grid[row][2] == Shape.S) {
                score++;
            }
            if (grid[0][col] == Shape.S // 3-in-the-column
                    && grid[1][col] == Shape.O && grid[2][col] == Shape.S) {
                score++;
            }
            if (row == col // 3-in-the-diagonal
                    && grid[0][0] == Shape.S && grid[1][1] == Shape.O && grid[2][2] == Shape.S) {
                score++;
            }
            if (row + col == 2 // 3-in-the-opposite-diagonal
                    && grid[0][2] == Shape.S && grid[1][1] == Shape.O && grid[2][0] == Shape.S) {
                score++;
            }
        }
        // Handles logic for BOARDSIZE > 3 and shape == 'S'
        else if (shape == Shape.S && BOARDSIZE > 3) {
            // CASE: At least 3 spaces into the board in any direction
            if ((row - 2) >= 0 && (col - 2) >= 0 && (row + 3) <= BOARDSIZE && (col + 3) <= BOARDSIZE) {
                // Check in every direction
                if (checkColS(row, col))
                    score++;
                if (checkRowS(row, col))
                    score++;
                if (checkBackSlashDiagonalS(row, col))
                    score++;
                if (checkForwardSlashDiagonal(row, col))
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
                if (checkRowS(row, col))
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
                if (checkRowS(row, col))
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
                if (checkColS(row, col))
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
                if (checkColS(row, col))
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
        else if (shape == Shape.O && BOARDSIZE > 3)
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
        return (grid[row - 1][col - 1] == Shape.S && grid[row + 1][col + 1] == Shape.S);
    }

    private boolean checkForwardSlashDiagonalO(int row, int col) {
        return (grid[row + 1][col - 1] == Shape.S && grid[row - 1][col + 1] == Shape.S);
    }

    private boolean checkColO(int row, int col) {
        return (grid[row - 1][col] == Shape.S && grid[row + 1][col] == Shape.S);
    }

    private boolean checkRowO(int row, int col) {
        return (grid[row][col - 1] == Shape.S && grid[row][col + 1] == Shape.S);
    }

    private boolean checkLowerLeftDiagonalS(int row, int col) {
        return (grid[row + 2][col - 2] == Shape.S && grid[row + 1][col - 1] == Shape.O);
    }

    private boolean checkUpperRightDiagonalS(int row, int col) {
        return (grid[row - 2][col + 2] == Shape.S && grid[row - 1][col + 1] == Shape.O);
    }

    private boolean checkForwardSlashDiagonal(int row, int col) {
        return (checkLowerLeftDiagonalS(row, col) || checkUpperRightDiagonalS(row, col));
    }

    private boolean checkUpperLeftDiagonalS(int row, int col) {
        return (grid[row - 2][col - 2] == Shape.S && grid[row - 1][col - 1] == Shape.O);
    }

    private boolean checkLowerRightDiagonalS(int row, int col) {
        return (grid[row + 2][col + 2] == Shape.S && grid[row + 1][col + 1] == Shape.O);
    }

    private boolean checkBackSlashDiagonalS(int row, int col) {
        return (checkUpperLeftDiagonalS(row, col) || checkLowerRightDiagonalS(row, col));
    }

    private boolean checkUpS(int row, int col) {
        return (grid[row - 2][col] == Shape.S && grid[row - 1][col] == Shape.O);
    }

    private boolean checkDownS(int row, int col) {
        return (grid[row + 2][col] == Shape.S && grid[row + 1][col] == Shape.O);
    }

    private boolean checkColS(int row, int col) {
        return (checkUpS(row, col) || checkDownS(row, col));
    }

    private boolean checkLeftS(int row, int col) {
        return (grid[row][col - 2] == Shape.S && grid[row][col - 1] == Shape.O);
    }

    private boolean checkRightS(int row, int col) {
        return (grid[row][col + 2] == Shape.S && grid[row][col + 1] == Shape.O);
    }

    private boolean checkRowS(int row, int col) {
        return (checkLeftS(row, col) || checkRightS(row, col));
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

    public Shape getCell(int row, int column) {
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

    public int getBluePlayerScore() {
        return BluePlayerScore;
    }

    public int getRedPlayerScore() {
        return RedPlayerScore;
    }
}
