package com.sos;

// One coordinate is a row and a column and a direction to draw the line in that cell
public class SosLineCoordinate {
    // Each possible direction to draw a line
    enum LineDirection { VERTICAL, HORIZONTAL, BACKSLASH_DIAGONAL, FORWARDSLASH_DIAGONAL }
    public int row;
    public int col;
    public LineDirection lineDirection;

    SosLineCoordinate(int row, int  col, LineDirection lineDirection) {
        this.row = row;
        this.col = col;
        this.lineDirection = lineDirection;
    }
}
