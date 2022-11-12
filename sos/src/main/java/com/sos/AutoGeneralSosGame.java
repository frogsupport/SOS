package com.sos;

public class AutoGeneralSosGame extends GeneralSosGame {
    public AutoGeneralSosGame(int boardsize, PlayerType bluePlayerType, PlayerType redPlayerType) {
        super(boardsize);
        this.bluePlayerType = bluePlayerType;
        this.redPlayerType = redPlayerType;
    }

    @Override
    public SosMove makeAutoMove() {
        AutoSosMoveMaker autoSosMoveMaker = new AutoSosMoveMaker(grid);
        return autoSosMoveMaker.makeMove();
    }
}
