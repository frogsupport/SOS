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
        AutoSosMove autoSosMove = new AutoSosMove(grid);

        if (isEmpty()) {
            return autoSosMove.makeRandomMove();
        }
        return autoSosMove.makeMove();
    }
}
