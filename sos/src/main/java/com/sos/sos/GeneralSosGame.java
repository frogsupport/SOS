package com.sos.sos;

import com.sos.providers.SosBoardScorer;

public class GeneralSosGame extends SosGame {

    public GeneralSosGame(int boardSize) {
        super(boardSize);
    }

    // Updates the score if an SOS is made
    // Changes the current game status if there is a winner or a draw
    // Changes the turn if no SOS is made
    @Override
    protected void updateGameStatus(int row, int column, Shape shape) {
        // The object that handles the scoring logic for the general SOS game
        SosBoardScorer sosBoardScorer = new SosBoardScorer(grid);
        int score = sosBoardScorer.hasScored(row, column, shape);
        // If an SOS is scored, keep the turn the same and add the score
        if (score > 0) {
            if (turn == Turn.BLUE) {
                bluePlayerScore += score;
            } else if (turn == Turn.RED) {
                redPlayerScore += score;
            }

            lineCoordinates = sosBoardScorer.getLineCoordinates();
        }
        // If no SOS is scored, then change the turn
        else {
            changeTurn();
        }

        // Check end of game status
        if (isBoardFilled() && bluePlayerScore > redPlayerScore) {
            currentGameStatus = GameStatus.BLUE_WON;
        } else if (isBoardFilled() && redPlayerScore > bluePlayerScore) {
            currentGameStatus = GameStatus.RED_WON;
        } else if (isBoardFilled() && bluePlayerScore == redPlayerScore) {
            currentGameStatus = GameStatus.DRAW;
        }
    }
}
