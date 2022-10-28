package com.sos;

public class GeneralSosGame implements SosGame {
    private static int BOARDSIZE;
    private Cell[][] grid;
    private Turn turn;
    private GameStatus currentGameStatus;
    private int BluePlayerScore;
    private int RedPlayerScore;
    private int[] lineCoordinates;

    public GeneralSosGame(int boardSize) {
        // Board size must be at least 3
        if (boardSize < 3) {
            BOARDSIZE = 3;
        }
        else {
            BOARDSIZE = boardSize;
        }

        grid = new Cell[BOARDSIZE][BOARDSIZE];
        initBoard();
        currentGameStatus = GameStatus.PLAYING;
        BluePlayerScore = 0;
        RedPlayerScore = 0;
        lineCoordinates = new int[] {-1, -1, -1};
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
            updateGameStatus(row, column, shape);
            changeTurn();
        }
    }

    private void updateGameStatus(int row, int column, Cell shape) {
        if (hasScored(row, column, shape)) {
            if (turn == Turn.BLUE) {
                BluePlayerScore++;
            } else if (turn == Turn.RED) {
                RedPlayerScore++;
            }
        }
        if (isBoardFilled() && BluePlayerScore > RedPlayerScore) {
            currentGameStatus = GameStatus.BLUE_WON;
        } else if (isBoardFilled() && RedPlayerScore > BluePlayerScore) {
            currentGameStatus = GameStatus.RED_WON;
        } else if (isBoardFilled() && BluePlayerScore == RedPlayerScore) {
            currentGameStatus = GameStatus.DRAW;
        }
    }

    private boolean hasScored(int row, int col, Cell shape) {
        // Handles logic for a board size of 3
        if (BOARDSIZE == 3) {
            return (grid[row][0] == Cell.S // 3-in-the-row
                    && grid[row][1] == Cell.O && grid[row][2] == Cell.S

                    || grid[0][col] == Cell.S // 3-in-the-column
                    && grid[1][col] == Cell.O && grid[2][col] == Cell.S

                    || row == col // 3-in-the-diagonal
                    && grid[0][0] == Cell.S && grid[1][1] == Cell.O && grid[2][2] == Cell.S

                    || row + col == 2 // 3-in-the-opposite-diagonal
                    && grid[0][2] == Cell.S && grid[1][1] == Cell.O && grid[2][0] == Cell.S);
        }
        // Handles logic for BOARDSIZE > 3 and shape == 'S'
        else if (shape == Cell.S) {
            // CASE: At least 3 spaces into the board in any direction
            if ((row - 2) >= 0 && (col - 2) >= 0 && (row + 3) <= BOARDSIZE && (col + 3) <= BOARDSIZE) {
                // Check in every direction
                return (checkColS(row, col)
                        || checkRowS(row, col)
                        || checkBackSlashDiagonalS(row, col)
                        || checkForwardSlashDiagonal(row, col));
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
                return (checkRowS(row, col)
                        || checkUpS(row, col)
                        || checkUpperLeftDiagonalS(row, col)
                        || checkUpperRightDiagonalS(row, col));
            }
            // CASE: Top in between borders
            else if ((row - 1) <= 0 && (col - 2) >= 0 && (col + 3) <= BOARDSIZE)  {
                // Check left, right, down, lower left diagonal, lower right diagonal
                return (checkRowS(row, col)
                        || checkDownS(row, col)
                        || checkLowerLeftDiagonalS(row, col)
                        || checkLowerRightDiagonalS(row, col));
            }
            // CASE: Left side in between top and bottom
            else if ((row - 2) >= 0 && (row + 3) <= BOARDSIZE && (col - 1) <= 0) {
                // Check up, down, right, upper right diagonal, lower right diagonal
                return (checkColS(row, col)
                        || checkRightS(row, col)
                        || checkUpperRightDiagonalS(row, col)
                        || checkLowerRightDiagonalS(row, col));
            }
            // CASE: Right side in between top and bottom
            else if ((row - 2) >= 0 && (row + 3) <= BOARDSIZE && (col + 2) >= BOARDSIZE) {
                // Check up, down, left, upper left diagonal, lower left diagonal
                return (checkColS(row, col)
                        || checkLeftS(row, col)
                        || checkUpperLeftDiagonalS(row, col)
                        || checkLowerLeftDiagonalS(row, col));
            }
        }
        // Handles logic for BOARDSIZE > 3 and shape == 'O'
        else if (shape == Cell.O)
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

    private boolean checkBackSlashDiagonalO(int row, int col) {
        return (grid[row - 1][col - 1] == Cell.S && grid[row + 1][col + 1] == Cell.S);
    }

    private boolean checkForwardSlashDiagonalO(int row, int col) {
        return (grid[row + 1][col - 1] == Cell.S && grid[row - 1][col + 1] == Cell.S);
    }

    private boolean checkColO(int row, int col) {
        return (grid[row - 1][col] == Cell.S && grid[row + 1][col] == Cell.S);
    }

    private boolean checkRowO(int row, int col) {
        return (grid[row][col - 1] == Cell.S && grid[row][col + 1] == Cell.S);
    }

    private boolean checkLowerLeftDiagonalS(int row, int col) {
        return (grid[row + 2][col - 2] == Cell.S && grid[row + 1][col - 1] == Cell.O);
    }

    private boolean checkUpperRightDiagonalS(int row, int col) {
        return (grid[row - 2][col + 2] == Cell.S && grid[row - 1][col + 1] == Cell.O);
    }

    private boolean checkForwardSlashDiagonal(int row, int col) {
        return (checkLowerLeftDiagonalS(row, col) || checkUpperRightDiagonalS(row, col));
    }

    private boolean checkUpperLeftDiagonalS(int row, int col) {
        return (grid[row - 2][col - 2] == Cell.S && grid[row - 1][col - 1] == Cell.O);
    }

    private boolean checkLowerRightDiagonalS(int row, int col) {
        return (grid[row + 2][col + 2] == Cell.S && grid[row + 1][col + 1] == Cell.O);
    }

    private boolean checkBackSlashDiagonalS(int row, int col) {
        return (checkUpperLeftDiagonalS(row, col) || checkLowerRightDiagonalS(row, col));
    }

    private boolean checkUpS(int row, int col) {
        return (grid[row - 2][col] == Cell.S && grid[row - 1][col] == Cell.O);
    }

    private boolean checkDownS(int row, int col) {
        return (grid[row + 2][col] == Cell.S && grid[row + 1][col] == Cell.O);
    }

    private boolean checkColS(int row, int col) {
        return (checkUpS(row, col) || checkDownS(row, col));
    }

    private boolean checkLeftS(int row, int col) {
        return (grid[row][col - 2] == Cell.S && grid[row][col - 1] == Cell.O);
    }

    private boolean checkRightS(int row, int col) {
        return (grid[row][col + 2] == Cell.S && grid[row][col + 1] == Cell.O);
    }

    private boolean checkRowS(int row, int col) {
        return (checkLeftS(row, col) || checkRightS(row, col));
    }

    private boolean isBoardFilled() {
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                if (grid[row][col] == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
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

    public int getBluePlayerScore() {
        return BluePlayerScore;
    }

    public int getRedPlayerScore() {
        return RedPlayerScore;
    }
}
