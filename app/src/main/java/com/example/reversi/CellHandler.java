package com.example.reversi;

import static androidx.core.content.ContextCompat.getDrawable;

import android.widget.ImageButton;

public class CellHandler {
    private final ImageButton cell;

    CellHandler(ImageButton cell) {
        this.cell = cell;
        cell.setOnClickListener(null);
    }

    void makePossible(int i, int j) {
        cell.setBackground(getDrawable(this.cell.getContext(), R.drawable.possible_turn));
        cell.setOnClickListener(view -> {
            GameActivity.game.board.reverse(GameActivity.game.board.estimate(new Coords(i, j), GameActivity.game.players[GameActivity.game.turn % 2].mark));
            GameActivity.game.turn++;
            GameActivity.game.startTurn();
        });
    }

    void draw(int mark) {
        cell.setOnClickListener(null);
        switch (mark) {
            case -1:
                cell.setBackground(getDrawable(this.cell.getContext(), R.drawable.black_checke));
                break;
            case 1:
                cell.setBackground(getDrawable(this.cell.getContext(), R.drawable.white_checke));
                break;
            default:
                cell.setBackground(getDrawable(this.cell.getContext(), R.drawable.plain_cell));
                break;
        }
    }
}
