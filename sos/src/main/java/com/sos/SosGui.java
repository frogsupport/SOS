package com.sos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

public class SosGui extends Application {

    private Cell[][] cells;
    private Label gameStatus = new Label("X's Turn");
    static private SosGame sosGame;

    @Override
    public void start(Stage primaryStage) {
        if (sosGame == null) {
            sosGame = new SosGame();
        }

        // center pane
        GridPane centerPane = new GridPane();
        cells = new Cell[sosGame.getTotalRows()][sosGame.getTotalColumns()];
        for (int i = 0; i < sosGame.getTotalRows(); i++)
            for (int j = 0; j < sosGame.getTotalColumns(); j++)
                centerPane.add(cells[i][j] = new Cell(i, j), j, i);
        drawBoard();

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
        TextField boardSizeField = new TextField();
        boardSizeField.setPrefSize(50, 30);

        // top pane
        HBox topPane = new HBox();
        topPane.setPadding(new Insets(10));
        topPane.setSpacing(10);
        topPane.setAlignment(Pos.CENTER);
        topPane.getChildren().addAll(gameTitle, generalGameRb, simpleGameRb, boardSizeLabel, boardSizeField);

        // left pane
        Label leftSideLabel = new Label("Left Side");
        VBox leftSide = new VBox(leftSideLabel);

        // right pane
        Label rightSideLabel = new Label("Right Side");
        VBox rightSide = new VBox(rightSideLabel);

        // set up borderpane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topPane);
        borderPane.setCenter(centerPane);
        borderPane.setLeft(leftSide);
        borderPane.setRight(rightSide);
        borderPane.setBottom(gameStatus);
        borderPane.setPadding(new Insets(10));

        // set up scene
        Scene scene = new Scene(borderPane, 500, 500);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void drawBoard() {
        for (int row = 0; row < sosGame.getTotalRows(); row++)
            for (int column = 0; column < sosGame.getTotalColumns(); column++) {
                cells[row][column].getChildren().clear();
                if (sosGame.getCell(row, column) == SosGame.Cell.CROSS)
                    cells[row][column].drawCross();
                else if (sosGame.getCell(row, column) == SosGame.Cell.NOUGHT)
                    cells[row][column].drawNought();
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

        public void drawCross() {
            Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
            line1.endXProperty().bind(this.widthProperty().subtract(10));
            line1.endYProperty().bind(this.heightProperty().subtract(10));
            line1.setStyle("-fx-stroke: red;");
            line1.setStrokeWidth(5.0);
            Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
            line2.startYProperty().bind(this.heightProperty().subtract(10));
            line2.endXProperty().bind(this.widthProperty().subtract(10));
            line2.setStyle("-fx-stroke: red;");
            line2.setStrokeWidth(5.0);
            this.getChildren().addAll(line1, line2);
        }

        public void drawNought() {
            Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10,
                    this.getHeight() / 2 - 10);
            ellipse.centerXProperty().bind(this.widthProperty().divide(2));
            ellipse.centerYProperty().bind(this.heightProperty().divide(2));
            ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
            ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
            ellipse.setStroke(Color.RED);
            ellipse.setStrokeWidth(5.0);
            ellipse.setFill(Color.TRANSPARENT);

            getChildren().add(ellipse);
        }

        private void displayGameStatus() {
            if (sosGame.getTurn() == 'X') {
                gameStatus.setText("X's Turn");
            } else {
                gameStatus.setText("O's Turn");
            }
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
