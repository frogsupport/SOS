package sos.models;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class BoardCell extends Pane {

    private int row, col;

    public BoardCell(int row, int col) {

        this.row = row;
        this.col = col;
        setStyle("-fx-border-color: white");
        this.setPrefSize(2000, 2000);
    }

    public void drawS() {
        // make draw fcn
    }

    public void drawO() {
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

}
