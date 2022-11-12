package com.sos;

import java.util.ArrayList;

public class AutoGeneralSosGame extends GeneralSosGame {
    public AutoGeneralSosGame(int boardsize, PlayerType bluePlayerType, PlayerType redPlayerType) {
        super(boardsize);
        this.bluePlayerType = bluePlayerType;
        this.redPlayerType = redPlayerType;
        lineCoordinates = new ArrayList<>();
    }

    @Override
    public SosMove makeAutoMove() {
        AutoMove autoMove = new AutoMove(grid);

        if (isEmpty()) {
            return autoMove.makeRandomMove();
        }
        return autoMove.makeMove();
    }
}
