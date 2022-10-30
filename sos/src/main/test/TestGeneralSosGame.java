import com.sos.GeneralSosGame;
import com.sos.ISosGame;
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
        sosGame.makeMove(0, 0, ISosGame.Shape.S);
        assertFalse(sosGame.makeMove(0, 0, ISosGame.Shape.O));
    }

    @Test
    public void testMakeMoveUnoccupiedCell() {
        assertTrue(sosGame.makeMove(0, 0, ISosGame.Shape.O));
    }

    @Test
    public void testIsBoardFilledFullBoard() {
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, ISosGame.Shape.O);
            }
        }
        assertTrue(sosGame.isBoardFilled());
    }

    @Test
    public void testIsBoardFilledNotFullBoard() {
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            // When the column index is decremented by one so the board isn't filled
            for (int j = 0; j < sosGame.getBoardSize() - 1; j++) {
                sosGame.makeMove(i, j, ISosGame.Shape.O);
            }
        }
        assertFalse(sosGame.isBoardFilled());
    }
}
