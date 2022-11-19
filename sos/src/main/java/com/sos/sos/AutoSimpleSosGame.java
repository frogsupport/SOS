package com.sos.sos;

import com.sos.models.SosMove;
import com.sos.providers.AutoSosMoveMaker;

public class AutoSimpleSosGame extends SimpleSosGame {
    public AutoSimpleSosGame(int boardsize, PlayerType bluePlayerType, PlayerType redPlayerType) {
        super(boardsize);
        setBluePlayerType(bluePlayerType);
        setRedPlayerType(redPlayerType);
    }

    // Returns true if a successful sos move is made
    @Override
    public boolean makeAutoMove() {
        // Check for an ongoing game
        if (getCurrentGameStatus() == GameStatus.PLAYING) {
            // Check if it's a computer's turn
            if ((getTurn() == SosGame.Turn.BLUE && getBluePlayerType() == PlayerType.COMPUTER)
                    || (getTurn() == SosGame.Turn.RED && getRedPlayerType() == SosGame.PlayerType.COMPUTER)) {
                SosMove sosMove = autoMove();

                // If a valid move is made, add it to the board, update the last auto move made
                if (makeMove(sosMove.Row, sosMove.Col, sosMove.Shape)) {
                    setLastAutoMove(sosMove);
                    return true;
                }
            }
        }

        return false;
    }

    // Returns the next best sos move based on the current board
    private SosMove autoMove() {
        AutoSosMoveMaker autoSosMoveMaker = new AutoSosMoveMaker(getGrid());
        return autoSosMoveMaker.makeMove();
    }

    /*@Override
    public SosMove makeAutoMove() {
        AutoSosMoveMaker autoSosMoveMaker = new AutoSosMoveMaker(grid);
        return autoSosMoveMaker.makeMove();
    }*/
}
