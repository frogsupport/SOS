package com.sos.sos;

import com.sos.providers.SosBoardScorer;

public class SimpleSosGame extends SosGame {

    public SimpleSosGame(int boardSize, boolean isGameRecorded) {
        super(boardSize, isGameRecorded);
    }

    // Player wins if they score a single SOS
    @Override
    protected void updateGameStatus(int row, int column, Shape shape) {
        // Log the move if we're recording
        if (getIsGameRecorded()) {
            recordMove(row, column, shape, getTurn());
        }

        // Create the object to handle the scoring logic
        SosBoardScorer sosBoardScorer = new SosBoardScorer(getGrid());
        if (sosBoardScorer.hasScored(row, column, shape) != 0) {

            setLineCoordinates(sosBoardScorer.getLineCoordinates());

            if (getTurn() == Turn.BLUE) {
                setCurrentGameStatus(GameStatus.BLUE_WON);
            } else if (getTurn() == Turn.RED) {
                setCurrentGameStatus(GameStatus.RED_WON);
            }
        } else if (isBoardFilled()) {
            setCurrentGameStatus(GameStatus.DRAW);
        }
        else {
            changeTurn();
        }
    }
}
