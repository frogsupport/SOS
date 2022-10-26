package com.sos;

// This interface has the commands that the GUI requires
public interface SosGame {
    public enum Cell {EMPTY, S, O}
    public enum Turn {BLUE, RED}
    public enum GameStatus {PLAYING, DRAW, BLUE_WON, RED_WON}

    public void makeMove(int row, int column, SimpleSosGame.Cell shape);

    public SimpleSosGame.Cell getCell(int row, int column);

    // returns the size of the board
    public int getBoardSize();

    public GameStatus getCurrentGameStatus();

    public SimpleSosGame.Turn getTurn();
}
