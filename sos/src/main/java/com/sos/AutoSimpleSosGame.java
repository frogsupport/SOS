package com.sos;

public class AutoSimpleSosGame extends SimpleSosGame {
    public AutoSimpleSosGame(int boardsize, PlayerType bluePlayerType, PlayerType redPlayerType) {
        super(boardsize);
        this.bluePlayerType = bluePlayerType;
        this.redPlayerType = redPlayerType;
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
