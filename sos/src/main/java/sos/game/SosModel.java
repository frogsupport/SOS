package sos.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sos.models.Board;
import sos.models.BoardCell;

import java.io.IOException;

public class SosModel extends Application {

    static private Board board;

    private BoardCell[][] boardCells;

    private Label currentPlayerTurn = new Label("Sith's Turn");

    @Override
    public void start(Stage stage) throws IOException {

        // default board of size 3
        if (board == null) {
            board = new Board(3);
        }

        GridPane pane = new GridPane();
        boardCells = new BoardCell[3][3];
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                // note that pane.add goes(node, col, row) (weird order)
                pane.add(boardCells[i][j] = new BoardCell(i, j), j, i);
            }
        }
        drawBoard();

        FXMLLoader fxmlLoader = new FXMLLoader(SosModel.class.getResource("SosView.fxml"));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(currentPlayerTurn);

        Scene scene = new Scene(borderPane, 1200, 900);
        stage.setTitle("SOS: The Game");
        stage.setScene(scene);
        stage.show();
    }

    // draws the appropriate shapes on the board
    public void drawBoard() {
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                boardCells[i][j].getChildren().clear();
                if (board.getCell(i, j) == Board.CellType.S) {
                    boardCells[i][j].drawS();
                }
                else if (board.getCell(i, j) == Board.CellType.O) {
                    boardCells[i][j].drawO();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}