package com.sos;

public class GeneralSosGame extends SosGame {

    public GeneralSosGame(int boardSize) {
        super(boardSize);
    }

    // Returns true if the cell is empty and a valid move is made
    // Returns false if the given index is invalid, or if the cell is occupied
    @Override
    public boolean makeMove(int row, int column, Shape shape) {
        if (row >= 0 && row < BOARDSIZE && column >= 0 && column < BOARDSIZE && grid[row][column] == Shape.EMPTY) {
            grid[row][column] = shape;
            updateGameStatus(row, column, shape);
            return true;
        }

        return false;
    }

    // Updates the score if an SOS is made
    // Changes the current game status if there is a winner or a draw
    // Changes the turn if no SOS is made
    private void updateGameStatus(int row, int column, Shape shape) {
        // The object that handles the scoring logic for the general SOS game
        BoardScorer boardScorer = new BoardScorer(grid);
        int score = boardScorer.hasScored(row, column, shape);
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
}
