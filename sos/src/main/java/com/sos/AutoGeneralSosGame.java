package com.sos;

public class AutoGeneralSosGame extends GeneralSosGame {
    public AutoGeneralSosGame(int boardsize, PlayerType bluePlayerType, PlayerType redPlayerType) {
        super(boardsize);
        this.bluePlayerType = bluePlayerType;
        this.redPlayerType = redPlayerType;
    }

    public SosMove makeAutoMove() {
        AutoMove autoMove = new AutoMove(grid);

        if (isEmpty()) {
            return autoMove.makeRandomMove();
        }
        return autoMove.makeMove();
    }
}
