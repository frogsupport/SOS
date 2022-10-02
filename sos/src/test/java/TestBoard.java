import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sos.models.Board;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoard {

    private Board board = new Board(3);

    @BeforeEach
    public void setUp() throws Exception {
        board.initializeBoard();
    }

    @Test
    public void testMakeValidMove() {
        int row, col;
        row = col = 1;
        board.makeMove(row, col, Board.CellType.S);
        assertEquals(Board.CellType.S, board.getCell(row, col));
    }

    @Test
    public void testMakeMoveInvalidRow() {
        int row, col;
        row = -1;
        col = 1;
        board.makeMove(row, col, Board.CellType.S);
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                assertEquals(Board.CellType.EMPTY, board.getCell(i, j));
            }
        }
    }

    @Test
    public void testMakeMoveInvalidCol() {
        int row, col;
        row = 1;
        col = -1;
        board.makeMove(row, col, Board.CellType.S);
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                assertEquals(Board.CellType.EMPTY, board.getCell(i, j));
            }
        }
    }

    @Test
    public void testEmptyBoard() {
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                assertEquals(Board.CellType.EMPTY, board.getCell(i, j));
            }
        }
    }

    @Test
    public void testInvalidIndexes() {
        // invalid row
        assertEquals(Board.CellType.INVALID, board.getCell(100, 0));
        // invalid column
        assertEquals(Board.CellType.INVALID, board.getCell(0, 100));
        // both invalid
        assertEquals(Board.CellType.INVALID, board.getCell(-1, -1));
    }
}
