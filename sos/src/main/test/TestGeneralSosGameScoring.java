import com.sos.GeneralSosGame;
import com.sos.SosGame;
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
        sosGame.makeMove(0,0, SosGame.Shape.S);
        sosGame.makeMove(1, 1, SosGame.Shape.O);
        sosGame.makeMove(0, 2, SosGame.Shape.S);
        // Red scores
        sosGame.makeMove(0, 1, SosGame.Shape.O);
        sosGame.makeMove(0,3, SosGame.Shape.S);
        // Blue scores
        sosGame.makeMove(2, 0, SosGame.Shape.S);
        sosGame.makeMove(1, 0, SosGame.Shape.S);

        // Fill the rest of the board except for the last row
        for (int i = 0; i < (sosGame.getBoardSize() - 1); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, SosGame.Shape.O);
            }
        }

        // Red wins on the last move on the last row
        sosGame.makeMove((SIZE - 1), 0, SosGame.Shape.O);
        sosGame.makeMove((SIZE - 1), 1, SosGame.Shape.S);
        sosGame.makeMove((SIZE - 1), 3, SosGame.Shape.S);
        sosGame.makeMove((SIZE - 1), 2, SosGame.Shape.O);

        // Then
        assertEquals(RED_EXPECTED_SCORE, sosGame.getRedPlayerScore());
        assertEquals(BLUE_EXPECTED_SCORE, sosGame.getBluePlayerScore());
        assertEquals(SosGame.GameStatus.RED_WON, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testScoreCenterAreaWithO() {
        // Given
        final int SIZE = 20;
        final int BLUE_EXPECTED_SCORE = 0;
        final int RED_EXPECTED_SCORE = 2;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When (Blue goes first)
        sosGame.makeMove(SIZE / 2, SIZE / 2, SosGame.Shape.S);
        sosGame.makeMove(SIZE / 2 + 1, (SIZE / 2) + 1, SosGame.Shape.S);
        sosGame.makeMove(SIZE / 2, (SIZE / 2) + 2, SosGame.Shape.S);
        // Red scores
        sosGame.makeMove(SIZE / 2, (SIZE / 2) + 1, SosGame.Shape.O);
        sosGame.makeMove(SIZE / 2 + 2, SIZE / 2, SosGame.Shape.S);
        sosGame.makeMove(SIZE / 2 + 1, (SIZE / 2) + 1, SosGame.Shape.S);
        sosGame.makeMove(SIZE / 2 + 2, (SIZE / 2) + 2, SosGame.Shape.S);
        // Blue scores
        sosGame.makeMove(SIZE / 2 + 2, (SIZE / 2) + 1, SosGame.Shape.O);

        // Then
        assertEquals(BLUE_EXPECTED_SCORE, sosGame.getBluePlayerScore());
        assertEquals(RED_EXPECTED_SCORE, sosGame.getRedPlayerScore());
        assertEquals(SosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testScoreCornersWithS() {
        // Given
        final int SIZE = 11;
        final int BLUE_EXPECTED_SCORE = 3;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When (Blue goes first)
        sosGame.makeMove(0, SIZE - 1, SosGame.Shape.S);
        sosGame.makeMove(0, SIZE - 2, SosGame.Shape.O);
        // Blue Scores in top right corner
        sosGame.makeMove(0, SIZE - 3, SosGame.Shape.S);
        sosGame.makeMove(SIZE - 1, SIZE - 1, SosGame.Shape.S);
        sosGame.makeMove(SIZE - 1, SIZE - 2, SosGame.Shape.O);
        // Blue scores in bottom right corner
        sosGame.makeMove(SIZE - 1, SIZE - 3, SosGame.Shape.S);
        sosGame.makeMove(SIZE - 1, 0, SosGame.Shape.S);
        sosGame.makeMove(SIZE - 2, 0, SosGame.Shape.O);
        // Blue scores a third time
        sosGame.makeMove(SIZE - 3, 0, SosGame.Shape.S);

        // Then
        assertEquals(BLUE_EXPECTED_SCORE, sosGame.getBluePlayerScore());
        // Make sure an S is in each corner
        assertEquals(SosGame.Shape.S, sosGame.getCell(0, SIZE - 1));
        assertEquals(SosGame.Shape.S, sosGame.getCell(SIZE - 1, SIZE - 1));
        assertEquals(SosGame.Shape.S, sosGame.getCell(SIZE - 1, 0));
        assertEquals(SosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
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
        sosGame.makeMove(0,0, SosGame.Shape.S);
        sosGame.makeMove(1, 1, SosGame.Shape.O);
        sosGame.makeMove(0, 2, SosGame.Shape.S);
        // Red scores
        sosGame.makeMove(0, 1, SosGame.Shape.O);
        sosGame.makeMove(0,3, SosGame.Shape.S);
        // Blue scores
        sosGame.makeMove(2, 0, SosGame.Shape.S);
        sosGame.makeMove(1, 0, SosGame.Shape.S);

        // Fill the rest of the board
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, SosGame.Shape.O);
            }
        }

        // Then
        assertEquals(SosGame.GameStatus.DRAW, sosGame.getCurrentGameStatus());
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
        sosGame.makeMove(0,0, SosGame.Shape.S);
        sosGame.makeMove(0, 1, SosGame.Shape.O);
        // Blue Score, still blues turn
        sosGame.makeMove(0, 2, SosGame.Shape.S);
        sosGame.makeMove(0, 3, SosGame.Shape.O);

        sosGame.makeMove(1,0, SosGame.Shape.S);
        sosGame.makeMove(1, 3, SosGame.Shape.O);
        sosGame.makeMove(1, 2, SosGame.Shape.S);
        // Blue scores again
        sosGame.makeMove(1, 1, SosGame.Shape.O);

        // Fill the rest of the board
        for (int i = 2; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, SosGame.Shape.O);
            }
        }

        // Then
        assertEquals(SosGame.GameStatus.BLUE_WON, sosGame.getCurrentGameStatus());
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
        sosGame.makeMove(0,0, SosGame.Shape.S);
        sosGame.makeMove(1, 1, SosGame.Shape.O);
        sosGame.makeMove(0, 2, SosGame.Shape.S);
        // Red scores
        sosGame.makeMove(0, 1, SosGame.Shape.O);
        // Still red's turn, red scores again
        sosGame.makeMove(2,0, SosGame.Shape.S);

        // Fill the rest of the board
        for (int i = 0; i < sosGame.getBoardSize(); i++) {
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                sosGame.makeMove(i, j, SosGame.Shape.O);
            }
        }

        // Then
        assertEquals(SosGame.GameStatus.RED_WON, sosGame.getCurrentGameStatus());
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
        sosGame.makeMove(0,0, SosGame.Shape.S);
        sosGame.makeMove(0, 1, SosGame.Shape.O);
        sosGame.makeMove(0, 2, SosGame.Shape.S);

        // Then it is still blue's turn, and blue scored
        assertEquals(EXPECTED_SCORE, sosGame.getBluePlayerScore());
        assertEquals(SosGame.Turn.BLUE, sosGame.getTurn());
        assertEquals(SosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
    }

    @Test
    public void testRedScores() {
        // Given
        final int SIZE = 4;
        final int EXPECTED_SCORE = 1;
        GeneralSosGame sosGame = new GeneralSosGame(SIZE);

        // When
        // Blue goes first
        sosGame.makeMove(0,0, SosGame.Shape.S);
        sosGame.makeMove(1, 1, SosGame.Shape.O);
        sosGame.makeMove(0, 2, SosGame.Shape.S);
        // Red scores
        sosGame.makeMove(0, 1, SosGame.Shape.O);

        // Then it is still red's turn, and red scored
        assertEquals(EXPECTED_SCORE, sosGame.getRedPlayerScore());
        assertEquals(SosGame.Turn.RED, sosGame.getTurn());
        assertEquals(SosGame.GameStatus.PLAYING, sosGame.getCurrentGameStatus());
    }
}
