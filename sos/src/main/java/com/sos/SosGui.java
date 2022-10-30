package com.sos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;

// CS449 SOS Project Fall 2022
// Professor Leo Chen
// Student Kyle Schaudt
public class SosGui extends Application {
    public enum GameType {SIMPLE, GENERAL}
    private TextField boardSizeField = new TextField();
    private Cell[][] cells;
    private Label gameStatus = new Label("Blue's Turn");
    private Label bluePlayerScore = new Label("0");
    private Label redPlayerScore = new Label("0");
    private ISosGame sosGame;
    private GridPane centerPane;
    private ISosGame.Shape bluePlayerShape = ISosGame.Shape.S;
    private ISosGame.Shape redPlayerShape = ISosGame.Shape.S;
    private GameType selectedGameType = GameType.SIMPLE;

    // TODO: Figure out good way to connect SOS's with lines
    // private Vector<Triplet<Integer, Integer, ISosGame.LineDirection>> blueLineCoordinates;
    // private Vector<Triplet<Integer, Integer, ISosGame.LineDirection>> redLineCoordinates;

    // set up gui ===============================================================================

    @Override
    public void start(Stage primaryStage) {
        if (sosGame == null) {
            sosGame = new SimpleSosGame(3);
        }
        newGame(sosGame);

        // game title label
        Label gameTitle = new Label("SOS");
        // insets: top right bottom left
        gameTitle.setPadding(new Insets(5, 30, 5, 5));
        gameTitle.setFont(new Font(20));

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
        boardSizeField.setPromptText("ex. 6");

        // top pane
        HBox topPane = new HBox();
        topPane.setPadding(new Insets(10));
        topPane.setSpacing(10);
        topPane.setAlignment(Pos.CENTER);
        topPane.getChildren().addAll(gameTitle, generalGameRb, simpleGameRb, boardSizeLabel, boardSizeField);

        // left pane
        Label leftSideLabel = new Label("Blue Player");
        leftSideLabel.setFont(new Font(13));
        leftSideLabel.setPadding(new Insets(8));

        RadioMenuItem bluePlayerShapes = new RadioMenuItem();
        ToggleGroup bluePlayerShapeToggle = new ToggleGroup();
        bluePlayerShapes.setToggleGroup(bluePlayerShapeToggle);

        RadioButton blueSShapeRadioButton = new RadioButton("S");
        blueSShapeRadioButton.setPadding(new Insets(3));
        blueSShapeRadioButton.setToggleGroup(bluePlayerShapeToggle);
        blueSShapeRadioButton.setSelected(true);

        RadioButton blueOShapeRadioButton = new RadioButton("O");
        blueOShapeRadioButton.setPadding(new Insets(3));
        blueOShapeRadioButton.setToggleGroup(bluePlayerShapeToggle);

        Label blueScoreLabel = new Label("Score:");
        blueScoreLabel.setFont(new Font(13));
        blueScoreLabel.setPadding(new Insets(8));
        blueScoreLabel.setVisible(false);

        bluePlayerScore.setPadding(new Insets(5, 5, 5, 25));
        bluePlayerScore.setVisible(false);

        VBox leftSide = new VBox(leftSideLabel, blueSShapeRadioButton, blueOShapeRadioButton, blueScoreLabel, bluePlayerScore);
        leftSide.setPadding(new Insets(10));

        // right pane
        Label rightSideLabel = new Label("Red Player");
        rightSideLabel.setFont(new Font(13));
        rightSideLabel.setPadding(new Insets(8));

        RadioMenuItem redPlayerShapes = new RadioMenuItem();
        ToggleGroup redPlayerShapeToggle = new ToggleGroup();
        redPlayerShapes.setToggleGroup(redPlayerShapeToggle);

        RadioButton redSShapeRadioButton = new RadioButton("S");
        redSShapeRadioButton.setPadding(new Insets(3));
        redSShapeRadioButton.setToggleGroup(redPlayerShapeToggle);
        redSShapeRadioButton.setSelected(true);

        RadioButton redOShapeRadioButton = new RadioButton("O");
        redOShapeRadioButton.setPadding(new Insets(3));
        redOShapeRadioButton.setToggleGroup(redPlayerShapeToggle);

        Label redScoreLabel = new Label("Score:");
        redScoreLabel.setFont(new Font(13));
        redScoreLabel.setPadding(new Insets(8));
        redScoreLabel.setVisible(false);

        redPlayerScore.setPadding(new Insets(5, 5, 5, 25));
        redPlayerScore.setVisible(false);

        VBox rightSide = new VBox(rightSideLabel, redSShapeRadioButton, redOShapeRadioButton, redScoreLabel, redPlayerScore);
        rightSide.setPadding(new Insets(10));

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
        // start our new game
        newGameButton.setOnAction(actionEvent -> {
            int boardSize;
            try {
                boardSize = Integer.parseInt(getBoardSizeField().getText());
            }
            catch (NumberFormatException e) {
                boardSize = 3;
            }

            // Choose the game type, make score visible for general game or invisible for simple game
            if (selectedGameType == GameType.SIMPLE) {
                sosGame = new SimpleSosGame(boardSize);

                blueScoreLabel.setVisible(false);
                bluePlayerScore.setVisible(false);
                redScoreLabel.setVisible(false);
                redPlayerScore.setVisible(false);
            }
            else if (selectedGameType == GameType.GENERAL) {
                sosGame = new GeneralSosGame(boardSize);

                blueScoreLabel.setVisible(true);
                bluePlayerScore.setVisible(true);
                redScoreLabel.setVisible(true);
                redPlayerScore.setVisible(true);
            }

            // set the pane
            centerPane = newGame(sosGame);
            borderPane.setCenter(centerPane);
            resetScoreLabels();
            displayGameStatus();
        });

        blueSShapeRadioButton.setOnAction(actionEvent -> bluePlayerShape = ISosGame.Shape.S);
        blueOShapeRadioButton.setOnAction(actionEvent -> bluePlayerShape = ISosGame.Shape.O);

        redSShapeRadioButton.setOnAction(actionEvent -> redPlayerShape = ISosGame.Shape.S);
        redOShapeRadioButton.setOnAction(actionEvent -> redPlayerShape = ISosGame.Shape.O);

        simpleGameRb.setOnAction(actionEvent -> selectedGameType = GameType.SIMPLE);
        generalGameRb.setOnAction(actionEvent -> selectedGameType = GameType.GENERAL);
    }

    // class functions ===================================================================================

    // starts a new game with the board size in the board size text field
    // triggered by clicking the new game button
    public GridPane newGame(ISosGame sosGame) {
        // center pane
        centerPane = new GridPane();
        cells = new Cell[sosGame.getBoardSize()][sosGame.getBoardSize()];
        for (int i = 0; i < sosGame.getBoardSize(); i++)
            for (int j = 0; j < sosGame.getBoardSize(); j++)
                centerPane.add(cells[i][j] = new Cell(i, j), j, i);

        // TODO: Figure out SOS lines
        // blueLineCoordinates = new Vector<Triplet<Integer, Integer, ISosGame.LineDirection>>();
        // redLineCoordinates = new Vector<Triplet<Integer, Integer, ISosGame.LineDirection>>();

        return centerPane;
    }

    // gets called once each time a successful move is made
    public void drawBoard(int row, int col, ISosGame.Turn currentTurn) {
        /*for (int row = 0; row < sosGame.getBoardSize(); row++)
            for (int column = 0; column < sosGame.getBoardSize(); column++) {
                if (sosGame.getCell(row, column) == ISosGame.Cell.S)
                    cells[row][column].drawS();
                else if (sosGame.getCell(row, column) == ISosGame.Cell.O)
                    cells[row][column].drawO();
            }*/
        if (sosGame.getCell(row, col) == ISosGame.Shape.S)
            cells[row][col].drawS(currentTurn);
        else if (sosGame.getCell(row, col) == ISosGame.Shape.O)
            cells[row][col].drawO(currentTurn);
    }

    private void displayGameStatus() {
        if (sosGame.getCurrentGameStatus() == ISosGame.GameStatus.PLAYING) {
            if (sosGame.getTurn() == SimpleSosGame.Turn.BLUE) {
                gameStatus.setText("Blue's Turn");
            } else {
                gameStatus.setText("Red's Turn");
            }
        } else if (sosGame.getCurrentGameStatus() == ISosGame.GameStatus.DRAW) {
            gameStatus.setText("It's a DRAW!!!");
        } else if (sosGame.getCurrentGameStatus() == ISosGame.GameStatus.BLUE_WON) {
            gameStatus.setText("Blue Player Won!!!");
        } else if (sosGame.getCurrentGameStatus() == ISosGame.GameStatus.RED_WON) {
            gameStatus.setText("Red Player Won!!!");
        }

        resetScoreLabels();
    }

    // If the logical score changes, updates the displayed scores
    private void resetScoreLabels() {
        if (!String.valueOf(sosGame.getBluePlayerScore()).equals(bluePlayerScore.toString())
                || !String.valueOf(sosGame.getRedPlayerScore()).equals(redPlayerScore.toString())) {
            bluePlayerScore.setText(String.valueOf(sosGame.getBluePlayerScore()));
            redPlayerScore.setText(String.valueOf(sosGame.getRedPlayerScore()));
        }

        // TODO: Lines
        /*if (!sosGame.getBlueLineCoordinates().isEmpty()) {
            blueLineCoordinates.addAll(sosGame.getBlueLineCoordinates());

            System.out.println(blueLineCoordinates.elementAt(0).getFirst().toString() + blueLineCoordinates.elementAt(0).getSecond().toString());

            drawLine(blueLineCoordinates, "blue");
            blueLineCoordinates.clear();
        }

        if (!sosGame.getRedLineCoordinates().isEmpty()) {
            redLineCoordinates.addAll(sosGame.getRedLineCoordinates());

            System.out.println(redLineCoordinates.elementAt(0).getFirst().toString() + redLineCoordinates.elementAt(0).getSecond().toString());

            drawLine(redLineCoordinates, "red");
            redLineCoordinates.clear();
        }*/
    }

    // TODO: Lines
    // Draw the line starting from the upper leftmost node. Draw lines until the vector of line coordinates is empty
    /*public void drawLine(Vector<Triplet<Integer, Integer, ISosGame.LineDirection>> lineCoordinates, String lineColor) {
        while (!lineCoordinates.isEmpty()) {
            int i = lineCoordinates.elementAt(0).getFirst();
            int j = lineCoordinates.elementAt(0).getSecond();
            ISosGame.LineDirection direction = lineCoordinates.elementAt(0).getThird();
            Cell node = cells[i][j];

            if (direction == ISosGame.LineDirection.RIGHT) {
                // startx, starty, endx, endy
                Line line = new Line(node.getBaselineOffset(), node.getHeight() / 2, node.getWidth() , node.getHeight() / 2);
                line.setStyle(String.format("-fx-stroke: %s;", lineColor));
                line.setStrokeWidth(6.0);
                cells[i][j].getChildren().add(line);
            } else if (direction == ISosGame.LineDirection.DOWN) {
                // startx, starty, endx, endy
                Line line = new Line(node.getWidth() / 2, node.getHeight() / 8, (node.getWidth() / 2), centerPane.getWidth() + (node.getHeight() * 0.25));
                line.setStyle(String.format("-fx-stroke: %s;", lineColor));
                line.setStrokeWidth(6.0);
                cells[i][j].getChildren().add(line);
            }
            // TODO: Figure out the diagonal lines
            lineCoordinates.remove(0);
        }
    }*/

    public TextField getBoardSizeField() { return boardSizeField; }

    // Internal Cell class =============================================================================

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
            if (sosGame.getCurrentGameStatus() == ISosGame.GameStatus.PLAYING) {
                ISosGame.Shape shape = ISosGame.Shape.EMPTY;

                if (sosGame.getTurn() == ISosGame.Turn.BLUE) {
                    shape = bluePlayerShape;
                }
                else {
                    shape = redPlayerShape;
                }

                ISosGame.Turn currentTurn = (sosGame.getTurn() == ISosGame.Turn.BLUE) ? ISosGame.Turn.BLUE : ISosGame.Turn.RED;
                // If a valid move is made, add it to the board
                if (sosGame.makeMove(row, column, shape)) {
                    drawBoard(row, column, currentTurn);
                }
                displayGameStatus();
            }
            // Game over
            else {
                gameStatus.setText("It's time to start a new game!!!");
            }
        }

        public void drawS(ISosGame.Turn currentTurn) {
            text = new Label("S");
            String textColor = (currentTurn == ISosGame.Turn.BLUE) ? "blue" : "red";
            setCellTextSize(text, textColor);
            AnchorPane anchorPane = new AnchorPane(text);
            getChildren().add(anchorPane);
        }

        public void drawO(ISosGame.Turn currentTurn) {
            text = new Label("O");
            String textColor = (currentTurn == ISosGame.Turn.BLUE) ? "blue" : "red";
            setCellTextSize(text, textColor);
            AnchorPane anchorPane = new AnchorPane(text);
            getChildren().add(anchorPane);
        }

        public void setCellTextSize(Label text, String textColor) {
            // insets: top right bottom left
            // String textColor = (sosGame.getTurn() == ISosGame.Turn.BLUE) ? "blue" : "red";
            text.setStyle(String.format("-fx-text-fill: %s", textColor));
            if (sosGame.getBoardSize() == 3) {
                text.setFont(new Font("Arial", 120));
                text.setPadding(new Insets(10, 10, 10, 30));
            } else if (sosGame.getBoardSize() < 5) {
                text.setFont(new Font("Arial", 100));
                text.setPadding(new Insets(10, 10, 10, 20));
            } else if (sosGame.getBoardSize() < 8) {
                text.setFont(new Font("Arial", 70));
                text.setPadding(new Insets(8, 8, 8, 12));
            } else if (sosGame.getBoardSize() < 10) {
                text.setFont(new Font("Arial", 50));
                text.setPadding(new Insets(10, 10, 10, 10));
            } else if (sosGame.getBoardSize() < 14) {
                text.setFont(new Font("Arial", 30));
                text.setPadding(new Insets(10, 10, 10, 10));
            } else if (sosGame.getBoardSize() < 19) {
                text.setFont(new Font("Arial", 20));
                text.setPadding(new Insets(8, 8, 8, 8));
            } else {
                text.setFont(new Font("Arial", 15));
                text.setPadding(new Insets(8, 8, 8, 8));
            }
        }

        public Label getLabel() {
            return text;
        }
    }

    // main method ====================================================================================

    public static void main(String[] args) {
        launch(args);
    }
}
