package com.sos;

public class SimpleSosGame extends SosGame {

    public SimpleSosGame(int boardSize) {
        super(boardSize);
    }

    @Override
    public boolean makeMove(int row, int column, Shape shape) {
        if (row >= 0 && row < BOARDSIZE && column >= 0 && column < BOARDSIZE && grid[row][column] == Shape.EMPTY) {
            grid[row][column] = shape;
            updateGameStatus(row, column, shape);
            changeTurn();
            return true;
        }

        return false;
    }

    private void updateGameStatus(int row, int column, Shape shape) {
        // Create the object to handle the scoring logic
        BoardScorer boardScorer = new BoardScorer(grid);
        if (boardScorer.hasScored(row, column, shape) != 0) {
            if (turn == Turn.BLUE) {
                currentGameStatus = GameStatus.BLUE_WON;
            } else if (turn == Turn.RED) {
                currentGameStatus = GameStatus.RED_WON;
            }
        } else if (isBoardFilled()) {
            currentGameStatus = GameStatus.DRAW;
        }
    }
}
