package com.sos;

public class AutoSimpleSosGame extends SimpleSosGame {
    public AutoSimpleSosGame(int boardsize, PlayerType bluePlayerType, PlayerType redPlayerType) {
        super(boardsize);
        this.bluePlayerType = bluePlayerType;
        this.redPlayerType = redPlayerType;
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
