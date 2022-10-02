package sos.models;

public class Board {
    public enum CellType {EMPTY, S, O, INVALID};
    private int boardSize;
    private CellType[][] grid;

    public Board(int size) {
        boardSize = size;
        grid = new CellType[boardSize][boardSize];
    }

    // Populates every row in the current board with EMPTY
    public void initializeBoard() {
        for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                grid[row][col] = CellType.EMPTY;
            }
        }
    }

    // move takes the row, column, and shape to be entered
    public void makeMove(int row, int col, CellType cellType) {
        if (getCell(row, col) == CellType.O || getCell(row, col) == CellType.S) {
            System.out.println("Cell is occupied");
        }
        else if (row >= boardSize || row < 0 || col >= boardSize || col < 0) {
            System.out.println("Invalid cell");
        }
        else {
            setCell(row, col, cellType);
        }
    }

    // private function that will change a board cell's contents
    private void setCell(int row, int col, CellType cellType) {
        grid[row][col] = cellType;
    }

    // Returns the value in the given cell
    public CellType getCell(int row, int col) {
        if (row < boardSize && row >= 0 && col < boardSize && col >= 0) {
            return grid[row][col];
        }
        else {
            return CellType.INVALID;
        }
    }

    public int getBoardSize() {
        return boardSize;
    }
}
