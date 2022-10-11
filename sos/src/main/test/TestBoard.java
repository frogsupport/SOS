import com.sos.SosGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoard {

    private SosGame sosGame = new SosGame(3);

    @BeforeEach
    public void setUp() throws Exception {
        sosGame.initBoard();
    }

    @Test
    public void testChooseGameMode() {

    }

    @Test
    public void testChooseBoardSize() {
        final int BOARDSIZE = 13;
        sosGame = new SosGame(BOARDSIZE);
        assertEquals(BOARDSIZE, sosGame.getTotalRows());
    }

    @Test
    public void testMakeValidMove() {
        int row, col;
        row = col = 1;
        sosGame.makeMove(row, col, SosGame.Cell.S);
        assertEquals(SosGame.Cell.S, sosGame.getCell(row, col));
    }

    @Test
    public void testMakeMoveInvalidRow() {
        int row, col;
        row = -1;
        col = 1;
        sosGame.makeMove(row, col, SosGame.Cell.S);
        for (int i = 0; i < sosGame.getTotalRows(); i++) {
            for (int j = 0; j < sosGame.getTotalRows(); j++) {
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
        for (int i = 0; i < sosGame.getTotalRows(); i++) {
            for (int j = 0; j < sosGame.getTotalRows(); j++) {
                assertEquals(SosGame.Cell.EMPTY, sosGame.getCell(i, j));
            }
        }
    }

    @Test
    public void testEmptyBoard() {
        for (int i = 0; i < sosGame.getTotalRows(); i++) {
            for (int j = 0; j < sosGame.getTotalRows(); j++) {
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
