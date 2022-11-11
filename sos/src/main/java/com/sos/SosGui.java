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

    // set up gui ===============================================================================

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

            // Instantiate the correct game type
            if (selectedGameType == GameType.SIMPLE) {
                if (bluePlayerType == SosGame.PlayerType.COMPUTER || redPlayerType == SosGame.PlayerType.COMPUTER) {
                    sosGame = new AutoSimpleSosGame(boardSize, bluePlayerType, redPlayerType);
                }
                else {
                    sosGame = new SimpleSosGame(boardSize);
                }
                setScoreVisibility(false);
            }
            else if (selectedGameType == GameType.GENERAL) {
                // TODO: Currently do not support general game auto at all
                sosGame = new GeneralSosGame(boardSize);
                setScoreVisibility(true);
            }

            // set the pane
            centerPane = setBoard(sosGame);
            borderPane.setCenter(centerPane);
            resetScoreLabels();
            displayGameStatus();
            nextMove();
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

    // SosGui class functions ===================================================================================

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

    public void setScoreVisibility(boolean visibility) {
        blueScoreLabel.setVisible(visibility);
        bluePlayerScore.setVisible(visibility);
        redScoreLabel.setVisible(visibility);
        redPlayerScore.setVisible(visibility);
    }

    // Want a function that checks if it's the AI's turn. If it's a human player it returns, else it calls the
    // next turn for an AI player
    public void nextMove() {
        // Check for an ongoing game
        if (sosGame.getCurrentGameStatus() == SosGame.GameStatus.PLAYING) {
            // Check for AutoSimpleSosGame
            if (sosGame instanceof AutoSimpleSosGame) {
                // Check for Blue's turn
                if (sosGame.getTurn() == SosGame.Turn.BLUE) {
                    // Check if Blue player is a computer opponent or not
                    if (((AutoSimpleSosGame) sosGame).getBluePlayerType() == SosGame.PlayerType.COMPUTER) {
                        // Make blue's turn
                        SosGame.Turn currentTurn = SosGame.Turn.BLUE;
                        SosMove sosMove = ((AutoSimpleSosGame) sosGame).makeAutoMove();

                        // If a valid move is made, add it to the board
                        if (sosGame.makeMove(sosMove.Row, sosMove.Col, sosMove.Shape)) {
                            drawBoard(sosMove.Row, sosMove.Col, currentTurn);
                        }
                        displayGameStatus();
                        resetScoreLabels();
                        nextMove();
                    }
                }
                // Check for Red's turn
                else if (sosGame.getTurn() == SosGame.Turn.RED){
                    if (((AutoSimpleSosGame) sosGame).getRedPlayerType() == SosGame.PlayerType.COMPUTER) {
                        // Make Red's turn
                        SosGame.Turn currentTurn = SosGame.Turn.RED;
                        SosMove sosMove = ((AutoSimpleSosGame) sosGame).makeAutoMove();

                        // If a valid move is made, add it to the board
                        if (sosGame.makeMove(sosMove.Row, sosMove.Col, sosMove.Shape)) {
                            drawBoard(sosMove.Row, sosMove.Col, currentTurn);
                        }
                        displayGameStatus();
                        resetScoreLabels();
                        nextMove();
                    }
                }
            }
            // TODO: Do this for AutoGeneralSosGame
        }
    }

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
                displayGameStatus();
                resetScoreLabels();
                nextMove();
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

    // main method ====================================================================================

    public static void main(String[] args) {
        launch(args);
    }
}
