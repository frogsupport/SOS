import com.sos.GeneralSosGame;
import com.sos.SimpleSosGame;
import com.sos.SosGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Note that these tests test the functionality of the interface, ISosGame, as well
// as the functionality of the SimpleSosGame class. This is because the scope of the interface
// and the scope of what is offered by a simple sos game are the same.
public class TestSosGame {
    private final int SIZE = 3;
    private SosGame sosGame = new SimpleSosGame(SIZE);

    @BeforeEach
    public void setUp() throws Exception {
        sosGame = new SimpleSosGame(SIZE);
    }

    @Test
    public void testTurnChanges() {
        // Blue starts
        assertEquals(SosGame.Turn.BLUE, sosGame.getTurn());
        sosGame.makeMove(0, 0, SosGame.Shape.S);
        assertEquals(SosGame.Turn.RED, sosGame.getTurn());
    }

    @Test
    public void testChooseGameMode() {
        sosGame = new SimpleSosGame(SIZE);
        assertTrue(sosGame instanceof SimpleSosGame);

        sosGame = new GeneralSosGame(SIZE);
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
        sosGame.makeMove(row, col, SosGame.Shape.S);
        assertEquals(SosGame.Shape.S, sosGame.getCell(row, col));
    }

    @Test
    public void testMakeInvalidMove() {
        int row, col;
        row = col = -1;
        sosGame.makeMove(row, col, SosGame.Shape.O);
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                assertEquals(SosGame.Shape.EMPTY, sosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testMakeMoveInvalidRow() {
        int row, col;
        row = -1;
        col = 1;
        sosGame.makeMove(row, col, SosGame.Shape.S);
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                assertEquals(SosGame.Shape.EMPTY, sosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testMakeMoveInvalidCol() {
        int row, col;
        row = 1;
        col = -1;
        sosGame.makeMove(row, col, SosGame.Shape.S);
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                assertEquals(SosGame.Shape.EMPTY, sosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testMakeMoveOccupiedCell() {
        sosGame.makeMove(0, 1, SosGame.Shape.S);
        sosGame.makeMove(1, 0, SosGame.Shape.O);

        // make a move in the occupied (0, 1) cell
        sosGame.makeMove(0, 1, SosGame.Shape.O);

        assertEquals(sosGame.getCell(0, 1), SosGame.Shape.S);
    }

    @Test
    public void testEmptyBoard() {
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                assertEquals(SosGame.Shape.EMPTY, sosGame.getCell(i, j));
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
