import com.sos.SimpleSosGame;
import com.sos.ISosGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSimpleSosGameScoring {

    @Test
    public void testIsDraw() {
        // Given
        final int SIZE = 4;
        SimpleSosGame sosGame = new SimpleSosGame(SIZE);

        // When
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, ISosGame.Shape.O);
            }
        }

        // Then
        assertEquals(ISosGame.GameStatus.DRAW, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testRedWon() {
        // Given
        final int SIZE = 4;
        SimpleSosGame sosGame = new SimpleSosGame(SIZE);

        // When
        sosGame.makeMove(0, 0, ISosGame.Shape.S);
        sosGame.makeMove(1, 1, ISosGame.Shape.O);
        sosGame.makeMove(0, 2, ISosGame.Shape.S);
        // Red wins
        sosGame.makeMove(0, 1, ISosGame.Shape.O);

        // Then
        assertEquals(ISosGame.GameStatus.RED_WON, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testBlueWon() {
        // Given
        final int SIZE = 4;
        SimpleSosGame sosGame = new SimpleSosGame(SIZE);

        // When
        sosGame.makeMove(0, 0, ISosGame.Shape.S);
        sosGame.makeMove(0, 1, ISosGame.Shape.O);
        // Blue wins
        sosGame.makeMove(0, 2, ISosGame.Shape.S);

        // Then
        assertEquals(ISosGame.GameStatus.BLUE_WON, sosGame.getCurrentGameStatus());
    }
}
