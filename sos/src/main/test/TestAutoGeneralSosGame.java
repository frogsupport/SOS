import com.sos.models.SosMove;
import com.sos.providers.AutoSosMoveMaker;
import com.sos.sos.AutoGeneralSosGame;
import com.sos.sos.SosGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAutoGeneralSosGame {

    @Test
    public void testGameFinishesWithComputerOpponents() {
        // Given
        final int SIZE = 4;
        SosGame.PlayerType bluePlayerType = SosGame.PlayerType.COMPUTER;
        SosGame.PlayerType redPlayerType = SosGame.PlayerType.COMPUTER;
        SosGame sosGame = new AutoGeneralSosGame(SIZE, false, bluePlayerType, redPlayerType);

        // When
        while(sosGame.getCurrentGameStatus() == SosGame.GameStatus.PLAYING) {
            sosGame.makeAutoMove();
        }

        // Then game should be over in some way
        assertNotSame(sosGame.getCurrentGameStatus(), SosGame.GameStatus.PLAYING);
    }

    @Test
    public void testComputerPlayerMakesAutoMoves() {
        // Given
        final int SIZE = 4;
        SosGame.PlayerType bluePlayerType = SosGame.PlayerType.COMPUTER;
        SosGame.PlayerType redPlayerType = SosGame.PlayerType.HUMAN;
        SosGame sosGame = new AutoGeneralSosGame(SIZE, false, bluePlayerType, redPlayerType);

        // When
        // Blue makes first move
        sosGame.makeAutoMove();

        // Make a move for red
        AutoSosMoveMaker autoSosMoveMaker = new AutoSosMoveMaker(sosGame.getGrid());
        SosMove move =  autoSosMoveMaker.makeMove();
        sosGame.makeMove(move.Row, move.Col, move.Shape);

        // Make another move for blue
        sosGame.makeAutoMove();

        // Then
        int movesMade = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (sosGame.getCell(i, j) != SosGame.Shape.EMPTY) {
                    movesMade++;
                }
            }
        }

        // Should have three moves on the board
        assertEquals(3, movesMade);
    }

    @Test
    public void testNewAutoSosGameWithComputerPlayers() {
        // Given
        final int SIZE = 4;
        SosGame.PlayerType bluePlayerType = SosGame.PlayerType.COMPUTER;
        SosGame.PlayerType redPlayerType = SosGame.PlayerType.COMPUTER;

        // When
        SosGame sosGame = new AutoGeneralSosGame(SIZE, false, bluePlayerType, redPlayerType);

        // Then
        assertEquals(SosGame.PlayerType.COMPUTER, sosGame.getBluePlayerType());
        assertEquals(SosGame.PlayerType.COMPUTER, sosGame.getRedPlayerType());
    }

    @Test
    public void testAutoBlueMakesFirstMove() {
        // Given
        final int SIZE = 4;
        SosGame.PlayerType bluePlayerType = SosGame.PlayerType.COMPUTER;
        SosGame.PlayerType redPlayerType = SosGame.PlayerType.HUMAN;
        SosGame sosGame = new AutoGeneralSosGame(SIZE, false, bluePlayerType, redPlayerType);

        // When
        // Blue makes first move
        sosGame.makeAutoMove();

        // Shouldn't work for human red player
        sosGame.makeAutoMove();

        // Then
        int movesMade = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (sosGame.getCell(i, j) != SosGame.Shape.EMPTY) {
                    movesMade++;
                }
            }
        }

        assertEquals(1, movesMade);
    }

    @Test
    public void testHumanBlueDoesntMakeFirstMoveAuto() {
        // Given
        final int SIZE = 4;
        SosGame.PlayerType bluePlayerType = SosGame.PlayerType.HUMAN;
        SosGame.PlayerType redPlayerType = SosGame.PlayerType.COMPUTER;
        SosGame sosGame = new AutoGeneralSosGame(SIZE, false, bluePlayerType, redPlayerType);

        // When
        sosGame.makeAutoMove();
        sosGame.makeAutoMove();

        // Then
        int movesMade = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (sosGame.getCell(i, j) != SosGame.Shape.EMPTY) {
                    movesMade++;
                }
            }
        }

        assertEquals(0, movesMade);
    }
}
