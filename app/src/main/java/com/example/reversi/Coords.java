package com.example.reversi;

/**
 * Класс для координат клетки.
 */
public class Coords {
    private int row, column;

    public Coords(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean incRow() {
        ++row;
        return row <= 7;
    }

    public boolean incColumn() {
        ++column;
        return column <= 7;
    }

    public boolean decRow() {
        --row;
        return row >= 0;
    }

    public boolean decColumn() {
        --column;
        return column >= 0;
    }

    public boolean edge() {
        return getRow() == 0 || getColumn() == 0 || getRow() == 7 || getColumn() == 7;
    }

    public boolean corner() {
        return (getColumn() == 0 || getColumn() == 7) && (getRow() == 0 || getRow() == 7);
    }
}
