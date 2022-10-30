package com.sos;

// This interface has the commands that the GUI requires
public interface ISosGame {
    enum Shape {EMPTY, S, O}
    enum Turn {BLUE, RED}
    enum GameStatus {PLAYING, DRAW, BLUE_WON, RED_WON}
    enum LineDirection { DOWN, RIGHT, UPPER_DIAGONAL_RIGHT, LOWER_DIAGONAL_RIGHT }

    boolean makeMove(int row, int column, Shape shape);

    Shape getCell(int row, int column);

    // returns the size of the board
    int getBoardSize();

    GameStatus getCurrentGameStatus();

    ISosGame.Turn getTurn();

    int getBluePlayerScore();
    int getRedPlayerScore();

    // TODO: Figure out how to connect SOS's with lines
    // Vector<Triplet<Integer, Integer, LineDirection>> getBlueLineCoordinates();
    // Vector<Triplet<Integer, Integer, LineDirection>> getRedLineCoordinates();
}
