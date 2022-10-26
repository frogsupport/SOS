import com.sos.GeneralSosGame;
import com.sos.SimpleSosGame;
import com.sos.SosGame;
import com.sos.SosGui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSosGame {

    // Basic functionality is tested on the simple sos game class
    private SosGame sosGame = new SimpleSosGame(3);

    @BeforeEach
    public void setUp() throws Exception {
        sosGame = new SimpleSosGame(3);
    }

    @Test
    public void testChooseGameMode() {
        sosGame = new SimpleSosGame(3);
        assertTrue(sosGame instanceof SimpleSosGame);

        sosGame = new GeneralSosGame(3);
        assertTrue(sosGame instanceof GeneralSosGame);
    }

    @Test
    public void testChooseGameModeAndBoardSize() {
        final int BOARDSIZE = 9;

        sosGame = new SimpleSosGame(BOARDSIZE);
        assertTrue(sosGame instanceof SimpleSosGame);
        assertEquals(BOARDSIZE, sosGame.getBoardSize());

        sosGame = new GeneralSosGame(BOARDSIZE);
        assertTrue(sosGame instanceof GeneralSosGame);
        assertEquals(BOARDSIZE, sosGame.getBoardSize());
    }

    @Test
    public void testChooseBoardSize() {
        final int BOARDSIZE = 13;
        sosGame = new SimpleSosGame(BOARDSIZE);
        assertEquals(BOARDSIZE, sosGame.getBoardSize());
    }

    @Test
    public void testChooseInvalidBoardSize() {
        final int BOARDSIZE = -1;
        final int MINBOARDSIZE = 3;
        sosGame = new SimpleSosGame(BOARDSIZE);
        assertEquals(MINBOARDSIZE, sosGame.getBoardSize());
    }

    @Test
    public void testMakeValidMove() {
        int row, col;
        row = col = 1;
        sosGame.makeMove(row, col, SosGame.Cell.S);
        assertEquals(SosGame.Cell.S, sosGame.getCell(row, col));
    }

    @Test
    public void testMakeInvalidMove() {
        int row, col;
        row = col = -1;
        sosGame.makeMove(row, col, SosGame.Cell.O);
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                assertEquals(SosGame.Cell.EMPTY, sosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testMakeMoveInvalidRow() {
        int row, col;
        row = -1;
        col = 1;
        sosGame.makeMove(row, col, SosGame.Cell.S);
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                assertEquals(SosGame.Cell.EMPTY, sosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testMakeMoveInvalidCol() {
        int row, col;
        row = 1;
        col = -1;
        sosGame.makeMove(row, col, SosGame.Cell.S);
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                assertEquals(SosGame.Cell.EMPTY, sosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testMakeMoveOccupiedCell() {
        sosGame.makeMove(0, 1, SosGame.Cell.S);
        sosGame.makeMove(1, 0, SosGame.Cell.O);

        // make a move in the occupied (0, 1) cell
        sosGame.makeMove(0, 1, SosGame.Cell.O);

        assertEquals(sosGame.getCell(0, 1), SosGame.Cell.S);
    }

    @Test
    public void testEmptyBoard() {
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                assertEquals(SosGame.Cell.EMPTY, sosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testInvalidIndexes() {
        // invalid row
        assertEquals(null, sosGame.getCell(100, 0));
        // invalid column
        assertEquals(null, sosGame.getCell(0, 100));
        // both invalid
        assertEquals(null, sosGame.getCell(-1, -1));
    }
}
