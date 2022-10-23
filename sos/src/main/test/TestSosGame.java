import com.sos.SimpleSosGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSosGame {

    private SimpleSosGame simpleSosGame = new SimpleSosGame(3);

    @BeforeEach
    public void setUp() throws Exception {
        simpleSosGame.initBoard();
    }

    @Test
    public void testChooseGameMode() {

    }

    @Test
    public void testChooseBoardSize() {
        final int BOARDSIZE = 13;
        simpleSosGame = new SimpleSosGame(BOARDSIZE);
        assertEquals(BOARDSIZE, simpleSosGame.getBoardSize());
    }

    @Test
    public void testMakeValidMove() {
        int row, col;
        row = col = 1;
        simpleSosGame.makeMove(row, col, SimpleSosGame.Cell.S);
        assertEquals(SimpleSosGame.Cell.S, simpleSosGame.getCell(row, col));
    }

    @Test
    public void testMakeMoveInvalidRow() {
        int row, col;
        row = -1;
        col = 1;
        simpleSosGame.makeMove(row, col, SimpleSosGame.Cell.S);
        for (int i = 0; i < simpleSosGame.getBoardSize(); i++) {
            for (int j = 0; j < simpleSosGame.getBoardSize(); j++) {
                assertEquals(SimpleSosGame.Cell.EMPTY, simpleSosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testMakeMoveInvalidCol() {
        int row, col;
        row = 1;
        col = -1;
        simpleSosGame.makeMove(row, col, SimpleSosGame.Cell.S);
        for (int i = 0; i < simpleSosGame.getBoardSize(); i++) {
            for (int j = 0; j < simpleSosGame.getBoardSize(); j++) {
                assertEquals(SimpleSosGame.Cell.EMPTY, simpleSosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testEmptyBoard() {
        for (int i = 0; i < simpleSosGame.getBoardSize(); i++) {
            for (int j = 0; j < simpleSosGame.getBoardSize(); j++) {
                assertEquals(SimpleSosGame.Cell.EMPTY, simpleSosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testInvalidIndexes() {
        // invalid row
        assertEquals(null, simpleSosGame.getCell(100, 0));
        // invalid column
        assertEquals(null, simpleSosGame.getCell(0, 100));
        // both invalid
        assertEquals(null, simpleSosGame.getCell(-1, -1));
    }
}
