package com.example.reversi;

import java.util.ArrayList;

public abstract class Player {
    public final int mark;

    protected Player(int mark) {
        this.mark = mark;
    }

    public abstract CellInfo choose(ArrayList<CellInfo> turns, Board board);
    public abstract int takeTurn();
    public abstract boolean isSure();
}
