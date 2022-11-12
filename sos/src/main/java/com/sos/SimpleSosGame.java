package com.sos;

public class SimpleSosGame extends SosGame {

    public SimpleSosGame(int boardSize) {
        super(boardSize);
    }

    // Player wins if they score a single SOS
    @Override
    protected void updateGameStatus(int row, int column, Shape shape) {
        // Create the object to handle the scoring logic
        SosBoardScorer sosBoardScorer = new SosBoardScorer(grid);
        if (sosBoardScorer.hasScored(row, column, shape) != 0) {
            lineCoordinates = sosBoardScorer.getLineCoordinates();

            if (turn == Turn.BLUE) {
                currentGameStatus = GameStatus.BLUE_WON;
            } else if (turn == Turn.RED) {
                currentGameStatus = GameStatus.RED_WON;
            }
        } else if (isBoardFilled()) {
            currentGameStatus = GameStatus.DRAW;
        }
        else {
            changeTurn();
        }
    }
}
