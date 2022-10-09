package com.sos;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SosGui extends Application {

    private TextField boardSizeField = new TextField();
    private Cell[][] cells;
    private Label gameStatus = new Label("Blue's Turn");
    private SosGame sosGame;
    private GridPane centerPane;

    @Override
    public void start(Stage primaryStage) {
        if (sosGame == null) {
            sosGame = new SosGame(3);
        }
        newGame(sosGame);

        // game title label
        Label gameTitle = new Label("SOS");

        // radio buttons
        RadioMenuItem radioMenuItem = new RadioMenuItem("Game Type: ");
        ToggleGroup toggleGroup = new ToggleGroup();
        radioMenuItem.setToggleGroup(toggleGroup);

        RadioButton generalGameRb = new RadioButton("General Game");
        generalGameRb.setToggleGroup(toggleGroup);

        RadioButton simpleGameRb = new RadioButton("Simple Game");
        simpleGameRb.setToggleGroup(toggleGroup);

        // board size text field
        Label boardSizeLabel = new Label("Board Size");
        boardSizeField.setPrefSize(50, 30);
        boardSizeField.setPromptText("ex. 7");

        // top pane
        HBox topPane = new HBox();
        topPane.setPadding(new Insets(10));
        topPane.setSpacing(10);
        topPane.setAlignment(Pos.CENTER);
        topPane.getChildren().addAll(gameTitle, generalGameRb, simpleGameRb, boardSizeLabel, boardSizeField);

        // left pane
        Label leftSideLabel = new Label("Blue PLayer");
        leftSideLabel.setPadding(new Insets(8));
        VBox leftSide = new VBox(leftSideLabel);

        // right pane
        Label rightSideLabel = new Label("Red Player");
        rightSideLabel.setPadding(new Insets(8));
        VBox rightSide = new VBox(rightSideLabel);

        // bottom pane
        Button newGameButton = new Button("New Game");

        HBox bottomPane = new HBox();
        bottomPane.setPadding(new Insets(10));
        bottomPane.setSpacing(30);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.getChildren().addAll(gameStatus, newGameButton);

        // set up borderpane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topPane);
        borderPane.setCenter(centerPane);
        borderPane.setLeft(leftSide);
        borderPane.setRight(rightSide);
        borderPane.setBottom(bottomPane);
        borderPane.setPadding(new Insets(10));

        // set up scene
        Scene scene = new Scene(borderPane, 700, 700);
        primaryStage.setTitle("SOS: The Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // new game button pressed event handler
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int boardSize = Integer.parseInt(getBoardSizeField().getText());
                sosGame = new SosGame(boardSize);
                centerPane = newGame(sosGame);
                borderPane.setCenter(centerPane);
            }
        });
    }

    // starts a new game with the board size in the board size text field
    // triggered by clicking the new game button
    public GridPane newGame(SosGame sosGame) {
        // center pane
        centerPane = new GridPane();
        cells = new Cell[sosGame.getTotalRows()][sosGame.getTotalColumns()];
        for (int i = 0; i < sosGame.getTotalRows(); i++)
            for (int j = 0; j < sosGame.getTotalColumns(); j++)
                centerPane.add(cells[i][j] = new Cell(i, j), j, i);
        drawBoard();

        return centerPane;
    }

    // draws the board with what is in the logical grid
    public void drawBoard() {
        for (int row = 0; row < sosGame.getTotalRows(); row++)
            for (int column = 0; column < sosGame.getTotalColumns(); column++) {
                cells[row][column].getChildren().clear();
                if (sosGame.getCell(row, column) == SosGame.Cell.S)
                    cells[row][column].drawS();
                else if (sosGame.getCell(row, column) == SosGame.Cell.O)
                    cells[row][column].drawO();
            }
    }

    public TextField getBoardSizeField() { return boardSizeField; }

    private void displayGameStatus() {
        if (sosGame.getTurn() == SosGame.Turn.Blue) {
            gameStatus.setText("Blue's Turn");
        } else {
            gameStatus.setText("Red's Turn");
        }
    }

    public class Cell extends Pane {

        private int row, column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
            setStyle("-fx-border-color: white");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        private void handleMouseClick() {
            sosGame.makeMove(row, column);
            drawBoard();
            displayGameStatus();
        }

        // is a method called on a cell to add an S
        public void drawS() {
            Label text = new Label("S");
            text.setFont(new Font("Arial", 20));
            text.setPadding(new Insets(10, 10, 10, 15));

            AnchorPane anchorPane = new AnchorPane(text);

            getChildren().add(anchorPane);
        }

        // is a method called on a cell to add an O
        public void drawO() {
            Label text = new Label("O");
            text.setFont(new Font("Arial", 20));
            text.setPadding(new Insets(10, 10, 10, 15));

            AnchorPane anchorPane = new AnchorPane(text);

            getChildren().add(anchorPane);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
