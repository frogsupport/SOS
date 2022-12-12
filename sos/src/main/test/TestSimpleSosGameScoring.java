import com.sos.sos.SimpleSosGame;
import com.sos.sos.SosGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimpleSosGameScoring {

    @Test
    public void testIsDraw() {
        // Given
        final int SIZE = 4;
        SimpleSosGame sosGame = new SimpleSosGame(SIZE, false);

        // When
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, SosGame.Shape.O);
            }
        }

        // Then
        assertEquals(SosGame.GameStatus.DRAW, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testRedWon() {
        // Given
        final int SIZE = 4;
        SimpleSosGame sosGame = new SimpleSosGame(SIZE, false);

        // When
        sosGame.makeMove(0, 0, SosGame.Shape.S);
        sosGame.makeMove(1, 1, SosGame.Shape.O);
        sosGame.makeMove(0, 2, SosGame.Shape.S);
        // Red wins
        sosGame.makeMove(0, 1, SosGame.Shape.O);

        // Then
        assertEquals(SosGame.GameStatus.RED_WON, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testBlueWon() {
        // Given
        final int SIZE = 4;
        SimpleSosGame sosGame = new SimpleSosGame(SIZE, false);

        // When
        sosGame.makeMove(0, 0, SosGame.Shape.S);
        sosGame.makeMove(0, 1, SosGame.Shape.O);
        // Blue wins
        sosGame.makeMove(0, 2, SosGame.Shape.S);

        // Then
        assertEquals(SosGame.GameStatus.BLUE_WON, sosGame.getCurrentGameStatus());
    }
}
