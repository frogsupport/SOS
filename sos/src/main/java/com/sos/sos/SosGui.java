package com.sos.sos;

import com.sos.models.SosLineCoordinate;
import com.sos.models.SosMove;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.List;

// CS449 SOS Project Fall 2022
// Professor Leo Chen
// Student Kyle Schaudt
public class SosGui extends Application {
    private enum GameType {SIMPLE, GENERAL}
    private TextField boardSizeField = new TextField();
    private Cell[][] cells;
    private Label gameStatus = new Label("Blue's Turn");
    private Label bluePlayerScore = new Label("0");
    private Label redPlayerScore = new Label("0");
    private Label blueScoreLabel;
    private Label redScoreLabel;
    private SosGame sosGame;
    private GridPane centerPane;
    private SosGame.Shape bluePlayerShape = SosGame.Shape.S;
    private SosGame.Shape redPlayerShape = SosGame.Shape.S;
    private GameType selectedGameType = GameType.SIMPLE;
    private SosGame.PlayerType redPlayerType = SosGame.PlayerType.HUMAN;
    private SosGame.PlayerType bluePlayerType = SosGame.PlayerType.HUMAN;
    private final int TEXT_SIZE = 13;
    private final int TITLE_SIZE = 30;
    private final int SCORE_TEXT_SIZE = 20;

    // set up gui ====================================================================================================

    @Override
    public void start(Stage primaryStage) {
        if (sosGame == null) {
            sosGame = new SimpleSosGame(3);
        }
        setBoard(sosGame);

        // top pane ====================================
        // insets: top right bottom left
        // game title label
        Label gameTitle = new Label("SOS");
        gameTitle.setPadding(new Insets(5, 30, 5, 5));
        gameTitle.setFont(new Font(TITLE_SIZE));

        // game type radio buttons
        RadioMenuItem radioMenuItem = new RadioMenuItem();
        ToggleGroup toggleGroup = new ToggleGroup();
        radioMenuItem.setToggleGroup(toggleGroup);

        RadioButton generalGameRb = new RadioButton("General Game");
        generalGameRb.setFont(new Font(TEXT_SIZE));
        generalGameRb.setToggleGroup(toggleGroup);

        RadioButton simpleGameRb = new RadioButton("Simple Game");
        simpleGameRb.setFont(new Font(TEXT_SIZE));
        simpleGameRb.setToggleGroup(toggleGroup);
        simpleGameRb.setSelected(true);

        // board size text field
        Label boardSizeLabel = new Label("Board Size");
        boardSizeLabel.setFont(new Font(TEXT_SIZE));
        boardSizeField.setPrefSize(50, 30);
        boardSizeField.setPromptText("ex. 6");

        // set top pane
        HBox topPane = new HBox();
        topPane.setPadding(new Insets(10));
        topPane.setSpacing(10);
        topPane.setAlignment(Pos.CENTER);
        topPane.getChildren().addAll(gameTitle, generalGameRb, simpleGameRb, boardSizeLabel, boardSizeField);

        // left pane ====================================
        Label bluePlayerLabel = new Label("Blue Player");
        bluePlayerLabel.setFont(new Font(SCORE_TEXT_SIZE));
        bluePlayerLabel.setPadding(new Insets(8));

        // blue player human computer radio buttons
        RadioMenuItem bluePlayerTypes = new RadioMenuItem();
        ToggleGroup bluePlayerTypesToggle = new ToggleGroup();
        bluePlayerTypes.setToggleGroup(bluePlayerTypesToggle);

        RadioButton blueHumanPlayerButton = new RadioButton("Human");
        blueHumanPlayerButton.setFont(new Font(TEXT_SIZE));
        blueHumanPlayerButton.setPadding(new Insets(3, 8, 8, 3));
        blueHumanPlayerButton.setToggleGroup(bluePlayerTypesToggle);
        blueHumanPlayerButton.setSelected(true);

        RadioButton blueComputerPlayerButton = new RadioButton("Computer");
        blueComputerPlayerButton.setFont(new Font(TEXT_SIZE));
        blueComputerPlayerButton.setPadding(new Insets(3, 8, 8, 3));
        blueComputerPlayerButton.setToggleGroup(bluePlayerTypesToggle);

        // blue player shapes radio buttons
        RadioMenuItem bluePlayerShapes = new RadioMenuItem();
        ToggleGroup bluePlayerShapeToggle = new ToggleGroup();
        bluePlayerShapes.setToggleGroup(bluePlayerShapeToggle);

        RadioButton blueSShapeRadioButton = new RadioButton("S");
        blueSShapeRadioButton.setFont(new Font(TEXT_SIZE));
        blueSShapeRadioButton.setPadding(new Insets(3, 8, 8, 45));
        blueSShapeRadioButton.setToggleGroup(bluePlayerShapeToggle);
        blueSShapeRadioButton.setSelected(true);

        RadioButton blueOShapeRadioButton = new RadioButton("O");
        blueOShapeRadioButton.setFont(new Font(TEXT_SIZE));
        blueOShapeRadioButton.setPadding(new Insets(3, 8, 8, 45));
        blueOShapeRadioButton.setToggleGroup(bluePlayerShapeToggle);

        // blue player score label
        blueScoreLabel = new Label("Score:");
        blueScoreLabel.setFont(new Font(SCORE_TEXT_SIZE));
        blueScoreLabel.setPadding(new Insets(8));
        blueScoreLabel.setVisible(false);

        bluePlayerScore.setPadding(new Insets(5, 5, 5, 25));
        bluePlayerScore.setFont(new Font(SCORE_TEXT_SIZE));
        bluePlayerScore.setVisible(false);

        // set left side pane
        VBox leftSide = new VBox(bluePlayerLabel, blueHumanPlayerButton, blueSShapeRadioButton, blueOShapeRadioButton, blueComputerPlayerButton, blueScoreLabel, bluePlayerScore);
        leftSide.setPadding(new Insets(10));

        // right pane ===================================
        Label redPlayerLabel = new Label("Red Player");
        redPlayerLabel.setFont(new Font(SCORE_TEXT_SIZE));
        redPlayerLabel.setPadding(new Insets(8));

        // red player human computer radio buttons
        RadioMenuItem redPlayerTypes = new RadioMenuItem();
        ToggleGroup redPlayerTypesToggle = new ToggleGroup();
        redPlayerTypes.setToggleGroup(redPlayerTypesToggle);

        RadioButton redHumanPlayerButton = new RadioButton("Human");
        redHumanPlayerButton.setFont(new Font(TEXT_SIZE));
        redHumanPlayerButton.setPadding(new Insets(3, 8, 8, 3));
        redHumanPlayerButton.setToggleGroup(redPlayerTypesToggle);
        redHumanPlayerButton.setSelected(true);

        RadioButton redComputerPlayerButton = new RadioButton("Computer");
        redComputerPlayerButton.setFont(new Font(TEXT_SIZE));
        redComputerPlayerButton.setPadding(new Insets(3, 8, 8, 3));
        redComputerPlayerButton.setToggleGroup(redPlayerTypesToggle);

        // red player shapeS radio buttons
        RadioMenuItem redPlayerShapes = new RadioMenuItem();
        ToggleGroup redPlayerShapeToggle = new ToggleGroup();
        redPlayerShapes.setToggleGroup(redPlayerShapeToggle);

        RadioButton redSShapeRadioButton = new RadioButton("S");
        redSShapeRadioButton.setFont(new Font(TEXT_SIZE));
        redSShapeRadioButton.setPadding(new Insets(3, 8, 8, 45));
        redSShapeRadioButton.setToggleGroup(redPlayerShapeToggle);
        redSShapeRadioButton.setSelected(true);

        RadioButton redOShapeRadioButton = new RadioButton("O");
        redOShapeRadioButton.setFont(new Font(TEXT_SIZE));
        redOShapeRadioButton.setPadding(new Insets(3, 8, 8, 45));
        redOShapeRadioButton.setToggleGroup(redPlayerShapeToggle);

        // red score label
        redScoreLabel = new Label("Score:");
        redScoreLabel.setFont(new Font(SCORE_TEXT_SIZE));
        redScoreLabel.setPadding(new Insets(8));
        redScoreLabel.setVisible(false);

        redPlayerScore.setPadding(new Insets(5, 5, 5, 25));
        redPlayerScore.setFont(new Font(SCORE_TEXT_SIZE));
        redPlayerScore.setVisible(false);

        // set the right side pane
        VBox rightSide = new VBox(redPlayerLabel, redHumanPlayerButton, redSShapeRadioButton, redOShapeRadioButton, redComputerPlayerButton, redScoreLabel, redPlayerScore);
        rightSide.setPadding(new Insets(10));

        // bottom pane ===================================
        Button newGameButton = new Button("New Game");
        newGameButton.setFont(new Font(TEXT_SIZE));

        gameStatus.setFont(new Font(SCORE_TEXT_SIZE));

        // set the bottom pane
        HBox bottomPane = new HBox();
        bottomPane.setPadding(new Insets(10));
        bottomPane.setSpacing(30);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.getChildren().addAll(gameStatus, newGameButton);

        // set up the borderpane layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topPane);
        borderPane.setCenter(centerPane);
        borderPane.setLeft(leftSide);
        borderPane.setRight(rightSide);
        borderPane.setBottom(bottomPane);
        borderPane.setPadding(new Insets(10));

        // set up scene
        Scene scene = new Scene(borderPane, 800, 800);
        primaryStage.setTitle("SOS: The Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // event handlers ============================================================================================

        // new game button pressed event handler
        // start our new game
        newGameButton.setOnAction(actionEvent -> {
            int boardSize;
            try {
                boardSize = Integer.parseInt(getBoardSizeTextField().getText());
            }
            catch (NumberFormatException e) {
                boardSize = 3;
            }

            // Instantiate the correct game type
            if (selectedGameType == GameType.SIMPLE) {
                if (bluePlayerType == SosGame.PlayerType.COMPUTER || redPlayerType == SosGame.PlayerType.COMPUTER) {
                    sosGame = new AutoSimpleSosGame(boardSize, bluePlayerType, redPlayerType);
                }
                else {
                    sosGame = new SimpleSosGame(boardSize);
                }

                setScoreLabelVisibility(false);
            }
            else if (selectedGameType == GameType.GENERAL) {
                if (bluePlayerType == SosGame.PlayerType.COMPUTER || redPlayerType == SosGame.PlayerType.COMPUTER) {
                    sosGame = new AutoGeneralSosGame(boardSize, bluePlayerType, redPlayerType);
                }
                else {
                    sosGame = new GeneralSosGame(boardSize);
                }

                setScoreLabelVisibility(true);
            }

            // set the pane for the new game
            centerPane = setBoard(sosGame);
            borderPane.setCenter(centerPane);

            // need to manually request a layout or else it will not be initialized for an Auto game
            // this is so we can draw our lines using the layout bounds of the board
            borderPane.layout();

            handleEndOfTurn();
        });

        blueHumanPlayerButton.setOnAction(actionEvent -> bluePlayerType = SosGame.PlayerType.HUMAN);
        blueComputerPlayerButton.setOnAction(actionEvent -> bluePlayerType = SosGame.PlayerType.COMPUTER);

        redHumanPlayerButton.setOnAction(actionEvent -> redPlayerType = SosGame.PlayerType.HUMAN);
        redComputerPlayerButton.setOnAction(actionEvent -> redPlayerType = SosGame.PlayerType.COMPUTER);

        blueSShapeRadioButton.setOnAction(actionEvent -> bluePlayerShape = SosGame.Shape.S);
        blueOShapeRadioButton.setOnAction(actionEvent -> bluePlayerShape = SosGame.Shape.O);

        redSShapeRadioButton.setOnAction(actionEvent -> redPlayerShape = SosGame.Shape.S);
        redOShapeRadioButton.setOnAction(actionEvent -> redPlayerShape = SosGame.Shape.O);

        simpleGameRb.setOnAction(actionEvent -> selectedGameType = GameType.SIMPLE);
        generalGameRb.setOnAction(actionEvent -> selectedGameType = GameType.GENERAL);


    }

    // SosGui class functions ========================================================================================

    // starts a new game with the board size in the board size text field
    // triggered by clicking the new game button
    public GridPane setBoard(SosGame sosGame) {
        centerPane = new GridPane();
        cells = new Cell[sosGame.getBoardSize()][sosGame.getBoardSize()];
        for (int i = 0; i < sosGame.getBoardSize(); i++)
            for (int j = 0; j < sosGame.getBoardSize(); j++) {
                centerPane.add(cells[i][j] = new Cell(i, j), j, i);
            }

        return centerPane;
    }

    // gets called once each time a successful move is made
    public void drawBoard(int row, int col, SosGame.Turn currentTurn) {
        if (sosGame.getCell(row, col) == SosGame.Shape.S)
            cells[row][col].drawS(currentTurn);
        else if (sosGame.getCell(row, col) == SosGame.Shape.O)
            cells[row][col].drawO(currentTurn);
    }

    // Sets the message at the bottom of the game
    private void displayGameStatus() {
        if (sosGame.getCurrentGameStatus() == SosGame.GameStatus.PLAYING) {
            if (sosGame.getTurn() == SimpleSosGame.Turn.BLUE) {
                gameStatus.setText("Blue's Turn");
            } else {
                gameStatus.setText("Red's Turn");
            }
        } else if (sosGame.getCurrentGameStatus() == SosGame.GameStatus.DRAW) {
            gameStatus.setText("It's a DRAW!!!");
        } else if (sosGame.getCurrentGameStatus() == SosGame.GameStatus.BLUE_WON) {
            gameStatus.setText("Blue Player Won!!!");
        } else if (sosGame.getCurrentGameStatus() == SosGame.GameStatus.RED_WON) {
            gameStatus.setText("Red Player Won!!!");
        }
    }

    // If the logical score changes, updates the displayed scores
    private void resetScoreLabels() {
        if (!String.valueOf(sosGame.getBluePlayerScore()).equals(bluePlayerScore.toString())
                || !String.valueOf(sosGame.getRedPlayerScore()).equals(redPlayerScore.toString())) {
            bluePlayerScore.setText(String.valueOf(sosGame.getBluePlayerScore()));
            redPlayerScore.setText(String.valueOf(sosGame.getRedPlayerScore()));
        }
    }

    public void setScoreLabelVisibility(boolean visibility) {
        blueScoreLabel.setVisible(visibility);
        bluePlayerScore.setVisible(visibility);
        redScoreLabel.setVisible(visibility);
        redPlayerScore.setVisible(visibility);
    }

    // Handles the next auto move
    public void tryNextAutoMove() {
        // Check for an ongoing game
        if (sosGame.getCurrentGameStatus() == SosGame.GameStatus.PLAYING) {
            // Check if it's a computer's turn
            if ((sosGame.getTurn() == SosGame.Turn.BLUE && sosGame.getBluePlayerType() == SosGame.PlayerType.COMPUTER)
                    || (sosGame.getTurn() == SosGame.Turn.RED && sosGame.getRedPlayerType() == SosGame.PlayerType.COMPUTER)) {
                SosGame.Turn currentTurn = sosGame.getTurn();
                if (sosGame.makeAutoMove()) {
                    drawBoard(sosGame.getLastAutoMove().Row, sosGame.getLastAutoMove().Col, currentTurn);
                }

                handleEndOfTurn();
            }
        }
        /*if (sosGame.getCurrentGameStatus() == SosGame.GameStatus.PLAYING) {
            SosGame.Turn currentTurn = sosGame.getTurn();
            if (sosGame.makeAutoMove()) {
                drawBoard(sosGame.getLastAutoMove().Row, sosGame.getLastAutoMove().Col, currentTurn);
            }

            handleEndOfTurn();
        }*/
        /*// Check for an ongoing game
        if (sosGame.getCurrentGameStatus() == SosGame.GameStatus.PLAYING) {
            // Check if it's a computer's turn
            if ((sosGame.getTurn() == SosGame.Turn.BLUE && sosGame.getBluePlayerType() == SosGame.PlayerType.COMPUTER)
                    || (sosGame.getTurn() == SosGame.Turn.RED && sosGame.getRedPlayerType() == SosGame.PlayerType.COMPUTER)) {
                SosGame.Turn currentTurn = sosGame.getTurn();
                SosMove sosMove = sosGame.makeAutoMove();

                // If a valid move is made, add it to the board
                if (sosGame.makeMove(sosMove.Row, sosMove.Col, sosMove.Shape)) {
                    drawBoard(sosMove.Row, sosMove.Col, currentTurn);
                }

                handleEndOfTurn();
            }
        }*/
    }

    // Handles the processes that must be run at the end of every turn, be it human turn or computer.
    // Likewise, this process must happen at the end of pressing the new game button.
    private void handleEndOfTurn() {
        displayGameStatus();
        resetScoreLabels();
        drawLines();
        tryNextAutoMove();
    }

    // Loops through the array of lines to be drawn until empty
    public void drawLines() {
        if (sosGame.getLineCoordinates() != null && !sosGame.getLineCoordinates().isEmpty()) {

            List<SosLineCoordinate> lineCoordinates = sosGame.getLineCoordinates();
            String lineColor = (sosGame.getTurn() == SosGame.Turn.BLUE) ? "-fx-stroke: blue;" : "-fx-stroke: red;";
            SosLineCoordinate coordinate = lineCoordinates.get(0);

            if (coordinate.lineDirection == SosLineCoordinate.LineDirection.BACKSLASH_DIAGONAL) {
                Line line = new Line();
                line.setStartX(cells[coordinate.row][coordinate.col].getLayoutBounds().getMaxX());
                line.setStartY(cells[coordinate.row][coordinate.col].getLayoutBounds().getMaxY());
                line.setEndX(cells[coordinate.row][coordinate.col].getLayoutBounds().getMinX());
                line.setEndY(cells[coordinate.row][coordinate.col].getLayoutBounds().getMinY());
                line.setStyle(lineColor);
                line.setStrokeWidth(5.0);
                cells[coordinate.row][coordinate.col].getChildren().add(line);
                sosGame.popLineCoordinate();
                drawLines();
            }
            else if (coordinate.lineDirection == SosLineCoordinate.LineDirection.FORWARDSLASH_DIAGONAL) {
                Line line = new Line();
                line.setStartX(cells[coordinate.row][coordinate.col].getLayoutBounds().getMaxX());
                line.setStartY(cells[coordinate.row][coordinate.col].getLayoutBounds().getMinY());
                line.setEndX(cells[coordinate.row][coordinate.col].getLayoutBounds().getMinX());
                line.setEndY(cells[coordinate.row][coordinate.col].getLayoutBounds().getMaxY());
                line.setStyle(lineColor);
                line.setStrokeWidth(5.0);
                cells[coordinate.row][coordinate.col].getChildren().add(line);
                sosGame.popLineCoordinate();
                drawLines();
            }
            else if (coordinate.lineDirection == SosLineCoordinate.LineDirection.VERTICAL) {
                Line line = new Line();
                line.setStartX(cells[coordinate.row][coordinate.col].getLayoutBounds().getMaxX() / 2);
                line.setStartY(cells[coordinate.row][coordinate.col].getLayoutBounds().getMaxY());
                line.setEndX(cells[coordinate.row][coordinate.col].getLayoutBounds().getMaxX() / 2);
                line.setEndY(cells[coordinate.row][coordinate.col].getLayoutBounds().getMinY());
                line.setStyle(lineColor);
                line.setStrokeWidth(5.0);
                cells[coordinate.row][coordinate.col].getChildren().add(line);
                sosGame.popLineCoordinate();
                drawLines();
            }
            else if (coordinate.lineDirection == SosLineCoordinate.LineDirection.HORIZONTAL) {
                Line line = new Line();
                line.setStartX(cells[coordinate.row][coordinate.col].getLayoutBounds().getMaxX());
                line.setStartY(cells[coordinate.row][coordinate.col].getLayoutBounds().getMaxY() / 2);
                line.setEndX(cells[coordinate.row][coordinate.col].getLayoutBounds().getMinX());
                line.setEndY(cells[coordinate.row][coordinate.col].getLayoutBounds().getMaxY() / 2);
                line.setStyle(lineColor);
                line.setStrokeWidth(5.0);
                cells[coordinate.row][coordinate.col].getChildren().add(line);
                sosGame.popLineCoordinate();
                drawLines();
            }
            // Something went wrong, just empty the list to keep the game moving
            else {
                sosGame.popLineCoordinate();
            }
        }
    }

    public TextField getBoardSizeTextField() { return boardSizeField; }

    // Internal Cell class ===========================================================================================

    // The object that is each tile in the board
    private class Cell extends Pane {

        private int row, column;

        private Label text;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.text = new Label();
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        private void handleMouseClick() {
            // Ongoing game
            if (sosGame.getCurrentGameStatus() == SosGame.GameStatus.PLAYING) {
                SosGame.Shape shape;

                if (sosGame.getTurn() == SosGame.Turn.BLUE) {
                    shape = bluePlayerShape;
                }
                else {
                    shape = redPlayerShape;
                }

                SosGame.Turn currentTurn = (sosGame.getTurn() == SosGame.Turn.BLUE) ? SosGame.Turn.BLUE : SosGame.Turn.RED;
                // If a valid move is made, add it to the board
                if (sosGame.makeMove(row, column, shape)) {
                    drawBoard(row, column, currentTurn);
                }

                handleEndOfTurn();
            }
        }

        public void drawS(SosGame.Turn currentTurn) {
            text = new Label("S");
            String textColor = (currentTurn == SosGame.Turn.BLUE) ? "blue" : "red";
            setCellTextSize(text, textColor);
            AnchorPane anchorPane = new AnchorPane(text);
            getChildren().add(anchorPane);
        }

        public void drawO(SosGame.Turn currentTurn) {
            text = new Label("O");
            String textColor = (currentTurn == SosGame.Turn.BLUE) ? "blue" : "red";
            setCellTextSize(text, textColor);
            AnchorPane anchorPane = new AnchorPane(text);
            getChildren().add(anchorPane);
        }

        public void setCellTextSize(Label text, String textColor) {
            // As the board size gets bigger we want the text size and padding to get smaller
            // so we set up an inverse relationship among the two.
            double fontSize = (1.0 / sosGame.getBoardSize()) * 300;
            double padding = (1.0 / sosGame.getBoardSize()) * 150;
            text.setStyle(String.format("-fx-text-fill: %s", textColor));
            text.setFont(new Font("Arial", fontSize));
            text.setPadding(new Insets(padding));
        }
    }

    // main =================================
    public static void main(String[] args) {
        launch(args);
    }
}
