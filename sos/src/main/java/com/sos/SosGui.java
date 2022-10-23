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
    public enum GameType {SIMPLE, GENERAL}
    private TextField boardSizeField = new TextField();
    private Cell[][] cells;
    private Label gameStatus = new Label("Blue's Turn");
    private SosGame sosGame;
    private GridPane centerPane;
    private SosGame.Cell bluePlayerShape = SosGame.Cell.S;
    private SosGame.Cell redPlayerShape = SosGame.Cell.S;
    private GameType selectedGameType = GameType.SIMPLE;

    // set up gui ===============================================================================

    @Override
    public void start(Stage primaryStage) {
        if (sosGame == null) {
            sosGame = new SimpleSosGame(3);
        }
        newGame(sosGame);

        // game title label
        Label gameTitle = new Label("SOS");

        // game type radio buttons
        RadioMenuItem radioMenuItem = new RadioMenuItem();
        ToggleGroup toggleGroup = new ToggleGroup();
        radioMenuItem.setToggleGroup(toggleGroup);

        RadioButton generalGameRb = new RadioButton("General Game");
        generalGameRb.setToggleGroup(toggleGroup);

        RadioButton simpleGameRb = new RadioButton("Simple Game");
        simpleGameRb.setToggleGroup(toggleGroup);
        simpleGameRb.setSelected(true);

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

        RadioMenuItem bluePlayerShapes = new RadioMenuItem();
        ToggleGroup bluePlayerShapeToggle = new ToggleGroup();
        bluePlayerShapes.setToggleGroup(bluePlayerShapeToggle);

        RadioButton blueSShapeRadioButton = new RadioButton("S");
        blueSShapeRadioButton.setToggleGroup(bluePlayerShapeToggle);
        blueSShapeRadioButton.setSelected(true);

        RadioButton blueOShapeRadioButton = new RadioButton("O");
        blueOShapeRadioButton.setToggleGroup(bluePlayerShapeToggle);

        VBox leftSide = new VBox(leftSideLabel, blueSShapeRadioButton, blueOShapeRadioButton);

        // right pane
        Label rightSideLabel = new Label("Red Player");
        rightSideLabel.setPadding(new Insets(8));

        RadioMenuItem redPlayerShapes = new RadioMenuItem();
        ToggleGroup redPlayerShapeToggle = new ToggleGroup();
        redPlayerShapes.setToggleGroup(redPlayerShapeToggle);

        RadioButton redSShapeRadioButton = new RadioButton("S");
        redSShapeRadioButton.setToggleGroup(redPlayerShapeToggle);
        redSShapeRadioButton.setSelected(true);

        RadioButton redOShapeRadioButton = new RadioButton("O");
        redOShapeRadioButton.setToggleGroup(redPlayerShapeToggle);

        VBox rightSide = new VBox(rightSideLabel, redSShapeRadioButton, redOShapeRadioButton);

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

        // event handlers ==================================================================================

        // new game button pressed event handler
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            // start our new game
            @Override
            public void handle(ActionEvent actionEvent) {
                int boardSize = Integer.parseInt(getBoardSizeField().getText());
                if (selectedGameType == GameType.SIMPLE) {
                    sosGame = new SimpleSosGame(boardSize);
                }
                else if (selectedGameType == GameType.GENERAL) {
                    sosGame = new GeneralSosGame(boardSize);
                }

                centerPane = newGame(sosGame);
                borderPane.setCenter(centerPane);
                displayGameStatus();
            }
        });

        blueSShapeRadioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bluePlayerShape = SimpleSosGame.Cell.S;
            }
        });

        blueOShapeRadioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bluePlayerShape = SimpleSosGame.Cell.O;
            }
        });

        redSShapeRadioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                redPlayerShape = SimpleSosGame.Cell.S;
            }
        });

        redOShapeRadioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                redPlayerShape = SimpleSosGame.Cell.O;
            }
        });

        simpleGameRb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedGameType = GameType.SIMPLE;
            }
        });

        generalGameRb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedGameType = GameType.GENERAL;
            }
        });
    }

    // functions ===================================================================================

    // starts a new game with the board size in the board size text field
    // triggered by clicking the new game button
    public GridPane newGame(SosGame sosGame) {
        // center pane
        centerPane = new GridPane();
        cells = new Cell[sosGame.getBoardSize()][sosGame.getBoardSize()];
        for (int i = 0; i < sosGame.getBoardSize(); i++)
            for (int j = 0; j < sosGame.getBoardSize(); j++)
                centerPane.add(cells[i][j] = new Cell(i, j), j, i);
        drawBoard();

        return centerPane;
    }

    // draws the board with what is in the logical grid
    public void drawBoard() {
        for (int row = 0; row < sosGame.getBoardSize(); row++)
            for (int column = 0; column < sosGame.getBoardSize(); column++) {
                cells[row][column].getChildren().clear();
                if (sosGame.getCell(row, column) == SimpleSosGame.Cell.S)
                    cells[row][column].drawS();
                else if (sosGame.getCell(row, column) == SimpleSosGame.Cell.O)
                    cells[row][column].drawO();
            }
    }

    private void displayGameStatus() {
        if (sosGame.getTurn() == SimpleSosGame.Turn.BLUE) {
            gameStatus.setText("Blue's Turn");
        } else {
            gameStatus.setText("Red's Turn");
        }
    }

    public TextField getBoardSizeField() { return boardSizeField; }

    // Cell class =============================================================================

    public class Cell extends Pane {

        private int row, column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        private void handleMouseClick() {
            SosGame.Cell shape = SosGame.Cell.EMPTY;

            if (sosGame.getTurn() == SosGame.Turn.BLUE) {
                shape = bluePlayerShape;
            }
            else {
                shape = redPlayerShape;
            }

            sosGame.makeMove(row, column, shape);
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

    // main method ====================================================================================

    public static void main(String[] args) {
        launch(args);
    }
}
