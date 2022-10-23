package com.sos;

public interface SosGame {
    public enum Cell {EMPTY, S, O}
    public enum Turn {BLUE, RED}

    public void initBoard();

    public void makeMove(int row, int column, SimpleSosGame.Cell shape);

    // TODO: hasWon function

    public SimpleSosGame.Cell getCell(int row, int column);

    // returns the size of the board
    public int getBoardSize();

    public SimpleSosGame.Turn getTurn();
}
