package com.example.reversi;

import java.util.ArrayList;

/**
 * Наследник класса игрок для живого игрока.
 */
public class Human extends Player {

    public Human(int mark) {
        super(mark);
    }

    /**
     * Игрок выбирает клетку через консоль.
     * @param turns - возможные ходы.
     * @param board - игровое поле.
     * @return Выбранная клетка.
     */
    @Override
    public CellInfo choose(ArrayList<CellInfo> turns, Board board) {
        return null;
    }

    /**
     * Игрок выбирает следующее действие.
     * @return Код выбранного действия.
     */
    @Override
    public int takeTurn() {
        return 0;
    }

    /**
     * Узнаём у игрока, уверен ли он в своём ходе.
     * @return true - верен, false - иначе.
     */
    @Override
    public boolean isSure() {
        return true;
    }
}
