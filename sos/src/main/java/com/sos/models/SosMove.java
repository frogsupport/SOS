package com.sos.models;

import com.sos.sos.SosGame;

// The components needed to make an Sos Game move
public class SosMove {
    public int Row;
    public int Col;
    public SosGame.Shape Shape;

    public SosMove(int row, int col, SosGame.Shape shape) {
        Row = row;
        Col = col;
        this.Shape = shape;
    }
}
