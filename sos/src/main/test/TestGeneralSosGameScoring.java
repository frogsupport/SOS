import com.sos.GeneralSosGame;
import com.sos.ISosGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGeneralSosGameScoring {

    @Test
    public void testGameWinningMove() {
        // Given
        final int SIZE = 4;
        final int RED_EXPECTED_SCORE = 2;
        final int BLUE_EXPECTED_SCORE = 1;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When
        // Blue goes first
        sosGame.makeMove(0,0, ISosGame.Shape.S);
        sosGame.makeMove(1, 1, ISosGame.Shape.O);
        sosGame.makeMove(0, 2, ISosGame.Shape.S);
        // Red scores
        sosGame.makeMove(0, 1, ISosGame.Shape.O);
        sosGame.makeMove(0,3, ISosGame.Shape.S);
        // Blue scores
        sosGame.makeMove(2, 0, ISosGame.Shape.S);
        sosGame.makeMove(1, 0, ISosGame.Shape.S);

        // Fill the rest of the board except for the last row
        for (int i = 0; i < (sosGame.getBoardSize() - 1); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, ISosGame.Shape.O);
            }
        }

        // Red wins on the last move on the last row
        sosGame.makeMove((SIZE - 1), 0, ISosGame.Shape.O);
        sosGame.makeMove((SIZE - 1), 1, ISosGame.Shape.S);
        sosGame.makeMove((SIZE - 1), 3, ISosGame.Shape.S);
        sosGame.makeMove((SIZE - 1), 2, ISosGame.Shape.O);

        // Then
        assertEquals(RED_EXPECTED_SCORE, sosGame.getRedPlayerScore());
        assertEquals(BLUE_EXPECTED_SCORE, sosGame.getBluePlayerScore());
        assertEquals(ISosGame.GameStatus.RED_WON, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testScoreCenterAreaWithO() {
        // Given
        final int SIZE = 20;
        final int BLUE_EXPECTED_SCORE = 0;
        final int RED_EXPECTED_SCORE = 2;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When (Blue goes first)
        sosGame.makeMove(SIZE / 2, SIZE / 2, ISosGame.Shape.S);
        sosGame.makeMove(SIZE / 2 + 1, (SIZE / 2) + 1, ISosGame.Shape.S);
        sosGame.makeMove(SIZE / 2, (SIZE / 2) + 2, ISosGame.Shape.S);
        // Red scores
        sosGame.makeMove(SIZE / 2, (SIZE / 2) + 1, ISosGame.Shape.O);
        sosGame.makeMove(SIZE / 2 + 2, SIZE / 2, ISosGame.Shape.S);
        sosGame.makeMove(SIZE / 2 + 1, (SIZE / 2) + 1, ISosGame.Shape.S);
        sosGame.makeMove(SIZE / 2 + 2, (SIZE / 2) + 2, ISosGame.Shape.S);
        // Blue scores
        sosGame.makeMove(SIZE / 2 + 2, (SIZE / 2) + 1, ISosGame.Shape.O);

        // Then
        assertEquals(BLUE_EXPECTED_SCORE, sosGame.getBluePlayerScore());
        assertEquals(RED_EXPECTED_SCORE, sosGame.getRedPlayerScore());
        assertEquals(ISosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testScoreCornersWithS() {
        // Given
        final int SIZE = 11;
        final int BLUE_EXPECTED_SCORE = 3;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When (Blue goes first)
        sosGame.makeMove(0, SIZE - 1, ISosGame.Shape.S);
        sosGame.makeMove(0, SIZE - 2, ISosGame.Shape.O);
        // Blue Scores in top right corner
        sosGame.makeMove(0, SIZE - 3, ISosGame.Shape.S);
        sosGame.makeMove(SIZE - 1, SIZE - 1, ISosGame.Shape.S);
        sosGame.makeMove(SIZE - 1, SIZE - 2, ISosGame.Shape.O);
        // Blue scores in bottom right corner
        sosGame.makeMove(SIZE - 1, SIZE - 3, ISosGame.Shape.S);
        sosGame.makeMove(SIZE - 1, 0, ISosGame.Shape.S);
        sosGame.makeMove(SIZE - 2, 0, ISosGame.Shape.O);
        // Blue scores a third time
        sosGame.makeMove(SIZE - 3, 0, ISosGame.Shape.S);

        // Then
        assertEquals(BLUE_EXPECTED_SCORE, sosGame.getBluePlayerScore());
        // Make sure an S is in each corner
        assertEquals(ISosGame.Shape.S, sosGame.getCell(0, SIZE - 1));
        assertEquals(ISosGame.Shape.S, sosGame.getCell(SIZE - 1, SIZE - 1));
        assertEquals(ISosGame.Shape.S, sosGame.getCell(SIZE - 1, 0));
        assertEquals(ISosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testIsDrawBothScore() {
        // Given
        final int SIZE = 4;
        final int RED_EXPECTED_SCORE = 1;
        final int BLUE_EXPECTED_SCORE = 1;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When
        // Blue goes first
        sosGame.makeMove(0,0, ISosGame.Shape.S);
        sosGame.makeMove(1, 1, ISosGame.Shape.O);
        sosGame.makeMove(0, 2, ISosGame.Shape.S);
        // Red scores
        sosGame.makeMove(0, 1, ISosGame.Shape.O);
        sosGame.makeMove(0,3, ISosGame.Shape.S);
        // Blue scores
        sosGame.makeMove(2, 0, ISosGame.Shape.S);
        sosGame.makeMove(1, 0, ISosGame.Shape.S);

        // Fill the rest of the board
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, ISosGame.Shape.O);
            }
        }

        // Then
        assertEquals(ISosGame.GameStatus.DRAW, sosGame.getCurrentGameStatus());
        assertEquals(RED_EXPECTED_SCORE, sosGame.getRedPlayerScore());
        assertEquals(BLUE_EXPECTED_SCORE, sosGame.getBluePlayerScore());
    }

    @Test
    public void testBlueWon() {
        // Given
        final int SIZE = 4;
        final int BLUE_EXPECTED_SCORE = 2;
        final int RED_EXPECTED_SCORE = 0;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When
        sosGame.makeMove(0,0, ISosGame.Shape.S);
        sosGame.makeMove(0, 1, ISosGame.Shape.O);
        // Blue Score, still blues turn
        sosGame.makeMove(0, 2, ISosGame.Shape.S);
        sosGame.makeMove(0, 3, ISosGame.Shape.O);

        sosGame.makeMove(1,0, ISosGame.Shape.S);
        sosGame.makeMove(1, 3, ISosGame.Shape.O);
        sosGame.makeMove(1, 2, ISosGame.Shape.S);
        // Blue scores again
        sosGame.makeMove(1, 1, ISosGame.Shape.O);

        // Fill the rest of the board
        for (int i = 2; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, ISosGame.Shape.O);
            }
        }

        // Then
        assertEquals(ISosGame.GameStatus.BLUE_WON, sosGame.getCurrentGameStatus());
        assertEquals(BLUE_EXPECTED_SCORE, sosGame.getBluePlayerScore());
        assertEquals(RED_EXPECTED_SCORE, sosGame.getRedPlayerScore());
    }

    @Test
    public void testRedWon() {
        // Given
        final int SIZE = 4;
        final int RED_EXPECTED_SCORE = 2;
        final int BLUE_EXPECTED_SCORE = 1;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When
        // Blue goes first
        sosGame.makeMove(0,0, ISosGame.Shape.S);
        sosGame.makeMove(1, 1, ISosGame.Shape.O);
        sosGame.makeMove(0, 2, ISosGame.Shape.S);
        // Red scores
        sosGame.makeMove(0, 1, ISosGame.Shape.O);
        // Still red's turn, red scores again
        sosGame.makeMove(2,0, ISosGame.Shape.S);

        // Fill the rest of the board
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, ISosGame.Shape.O);
            }
        }

        // Then
        assertEquals(ISosGame.GameStatus.RED_WON, sosGame.getCurrentGameStatus());
        assertEquals(RED_EXPECTED_SCORE, sosGame.getRedPlayerScore());
        assertEquals(BLUE_EXPECTED_SCORE, sosGame.getBluePlayerScore());
    }

    @Test
    public void testBlueScores() {
        // Given
        final int SIZE = 4;
        final int EXPECTED_SCORE = 1;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When
        // Blue goes first
        sosGame.makeMove(0,0, ISosGame.Shape.S);
        sosGame.makeMove(0, 1, ISosGame.Shape.O);
        sosGame.makeMove(0, 2, ISosGame.Shape.S);

        // Then it is still blue's turn, and blue scored
        assertEquals(EXPECTED_SCORE, sosGame.getBluePlayerScore());
        assertEquals(ISosGame.Turn.BLUE, sosGame.getTurn());
        assertEquals(ISosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testRedScores() {
        // Given
        final int SIZE = 4;
        final int EXPECTED_SCORE = 1;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When
        // Blue goes first
        sosGame.makeMove(0,0, ISosGame.Shape.S);
        sosGame.makeMove(1, 1, ISosGame.Shape.O);
        sosGame.makeMove(0, 2, ISosGame.Shape.S);
        // Red scores
        sosGame.makeMove(0, 1, ISosGame.Shape.O);

        // Then it is still red's turn, and red scored
        assertEquals(EXPECTED_SCORE, sosGame.getRedPlayerScore());
        assertEquals(ISosGame.Turn.RED, sosGame.getTurn());
        assertEquals(ISosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
    }
}
