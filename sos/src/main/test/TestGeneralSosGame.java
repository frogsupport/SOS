import com.sos.sos.GeneralSosGame;
import com.sos.sos.SosGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Note that the functionality of a general sos game is extended to the point
// that this class warrants its own suite of testing
public class TestGeneralSosGame {
    private final int SIZE = 9;
    private GeneralSosGame sosGame = new GeneralSosGame(SIZE);

    @BeforeEach
    public void setUp() throws Exception {
        sosGame = new GeneralSosGame(SIZE);
    }

    @Test
    public void testMakeMoveOccupiedCell() {
        sosGame.makeMove(0, 0, SosGame.Shape.S);
        assertFalse(sosGame.makeMove(0, 0, SosGame.Shape.O));
        assertEquals(SosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testMakeMoveUnoccupiedCell() {
        assertEquals(SosGame.Turn.BLUE, sosGame.getTurn());
        assertTrue(sosGame.makeMove(0, 0, SosGame.Shape.O));
        assertEquals(SosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
        assertEquals(SosGame.Turn.RED, sosGame.getTurn());
    }

    @Test
    public void testIsBoardFilledFullBoard() {
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, SosGame.Shape.O);
            }
        }
        assertTrue(sosGame.isBoardFilled());
        assertEquals(SosGame.GameStatus.DRAW, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testIsBoardFilledNotFullBoard() {
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            // When the column index is decremented by one so the board isn't filled
            for (int j = 0; j < sosGame.getBoardSize() - 1; j++) {
                sosGame.makeMove(i, j, SosGame.Shape.O);
            }
        }
        assertFalse(sosGame.isBoardFilled());
        assertEquals(SosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
    }
}
